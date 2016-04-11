############################
# Herepoi.pl scraped  alle poi gegevens binnen de opgegeven bounding box
############################
use strict;
use JSON;
binmode(STDOUT, ":utf8");

# de naam van de cache met de api key in
my $cacheName = "here.cache";

if(scalar @ARGV < 4) {
	print "Usage: herepoi min_lat min_lng max_lat max_lng\n";
	exit(1);
}

# Argumenten
# geo coordinaten voor bounding box
my $fromLat = @ARGV[0]; #"51.05633";
my $fromLng = @ARGV[1]; #"3.69485";
my $toLat = @ARGV[2]; #"51.038768";
my $toLng = @ARGV[3]; #"3.736953";

# voer de main functie uit de de gegevens zal opvragen en printen
main();

sub main {

	# we moeten eerst op zoek gaan naar een geldige API Key die we kunnen gebruiken
	# de API Key cachen we in een file zodat dit niet steeds opnieuw moet opgevraagd worden
	my $appCode;
	my $appId;
	
	my $cache = readCache();
	if(exists $cache->{"appCode"} && $cache->{"appCode"} ne "" && 
	   exists $cache->{"appId"} && $cache->{"appId"} ne "") {
	}
	else {
		my $obj = getAppCodeAndId();
		$cache->{"appCode"} = $obj->{"appCode"};
		$cache->{"appId"} = $obj->{"appId"};
		
		saveCache($cache);
	}
	
	$appCode = $cache->{"appCode"};
	$appId = $cache->{"appId"};
		
	printPOI($appCode, $appId);

}

##################
# Leest de gecachede properties van de cache file
##################
sub readCache {
	my @args = @ARGV;
	@ARGV = ( $cacheName );
	
	# check de last modified time van de  cache file
	(my $dev,my $ino,my $mode,my $nlink,my $uid,my $gid,my $rdev,my $size,
   my $atime,my $mtime,my $ctime,my $blksize,my $blocks) =  stat($cacheName);
   
   # als cache ouder is dan een uur gebruik cache niet meer
   my $age = time - $mtime;
   if($age > 60 * 60) {
		@ARGV = @args;
		return {};
   }		
	
	# lees alle lijnen van de vorm key=value en steek ze in een hash
	my $obj = {};
	while(<>) {
		chomp;
		my @parts = split("=", $_);
		$obj->{$parts[0]} = $parts[1];
	}
	
	# restore de oorpsronkelijke args en return de hash
	@ARGV = @args;
	return $obj;
}

#################
# Saved de API key gegevens in de cache
#################
sub saveCache {
	(my $cache) = @_;
	
	my %c = %{ $cache };
	open my $fh, ">" . $cacheName;
	for my $k (keys %c) {
		print $fh $k . "=" . $c{$k} . "\n";
	}
	close $fh;
}



########################
# Zoekt bruikbare API key van de website
########################
sub getAppCodeAndId {
	# bouw url op
	my $url = 'https://maps.here.com/directions/drive/N' . $fromLat . '-,-E' . $fromLng . ':' . $fromLat . ',' . $fromLng . '/N' . $toLat . '-,-E' . $toLng .':' . $toLat . ',' . $toLng .'?map=' . $toLat . ',' . $toLng . ',normal&avoid=carHOV';
	
	# vraag html response op met curl
	my $response = `curl --insecure -s -o - "$url"`;
	
	# eerst substringen naar de apikey: is STUKKEN sneller
	my $idx = index($response, "appCode");
	my $part = substr($response,$idx, 200);
	
	my $appCode;
	my $appId;
	
	# parse de appcode uit de  response
	if($part =~ /.*?\"?appCode\"?\:\s*(\'|\")(.*?)(\1).*?/gc) {
		$appCode = $2;
	}
	
	my $part2 = $part;
	# parse de appid uit de  response
	if($part2 =~ /.*?\"?appId\"?\:\s*(\'|\")(.*?)(\1).*?/gc) {
		$appId = $2;
	}
	
	# als er een appcode en app id gevonden is geef ze terug
	if($appCode ne "" && $appId ne "") {
		my $obj = { "appCode" => $appCode, "appId" => $appId };
		return $obj;
	}
	print "API KEY NOT FOUND";
	exit(1);
}

########################
# Vraagt de POI gegevens op en print ze in een bruikbaar csv formaat
########################
sub printPOI {
	(my $appCode, my $appId) = @_;
	
	# bouw url op
	my $url = 'https://traffic.cit.api.here.com/traffic/6.1/incidents.json?bbox=' . $toLat . '%2C' . $fromLng . '%3B' . $fromLat . '%2C' . $toLng . '&criticality=0,1,2&app_id=' . $appId .'&app_code=' . $appCode . '';
	
	# voer uit met curl
	my $json = `curl --insecure -s -o - "$url"`;
	
	# parse de json naar objecten
	my $response = from_json($json);
	
	my @items = @{ $response->{"TRAFFIC_ITEMS"}->{"TRAFFIC_ITEM"} };
	
	# print header
	print "id;lat;lng;type;traffictype;comments";
	print "\n";
	
	for my $item (@items) {
		
		# print id van de poi
		print $item->{"TRAFFIC_ITEM_ID"};
		print ";";
		
		# print lat & lng van de poi
		print $item->{"LOCATION"}->{"GEOLOC"}->{"ORIGIN"}->{"LATITUDE"};
		print ";";
		print $item->{"LOCATION"}->{"GEOLOC"}->{"ORIGIN"}->{"LONGITUDE"};
		print ";";
		
		# print de overeenkomstige enum waarde voor het type
		if($item->{"TRAFFIC_ITEM_TYPE_DESC"} eq "LANE_RESTRICTION") {
			print "4"; # laneclosed
		}
		elsif($item->{"TRAFFIC_ITEM_TYPE_DESC"} eq "ROAD_CLOSURE") {
			print "5"; # routeclosed
		}
		elsif($item->{"TRAFFIC_ITEM_TYPE_DESC"} eq "CONGESTION") {
			print "3"; # traffic jam
		}		
		elsif($item->{"TRAFFIC_ITEM_TYPE_DESC"} eq "CONSTRUCTION") {
			print "1"; #construction
		}
		elsif($item->{"TRAFFIC_ITEM_TYPE_DESC"} eq "ACCIDENT") {
			print "8"; #accident
		}
		else {
			print "0"; # unknown
		}
		print ";";
		
		# print de criticality (major,minor, ...)
		print $item->{"CRITICALITY"}->{"DESCRIPTION"};
		print ";";
		
		# print de comments als er zijn ,aders gebruikt de omschrijving van de TMC
		if(!$item->{"COMMENTS"} || $item->{"COMMENTS"} eq "") {
			print $item->{"RDS-TMC_LOCATIONS"}->{"RDS-TMC"}->[0]->{"ALERTC"}->{"DESCRIPTION"};
		}
		else {
			print $item->{"COMMENTS"};
		}
		
		print "\n";
	}
	
}