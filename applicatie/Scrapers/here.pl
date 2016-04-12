############################
# Hehre.pl scraped de route details met gegeven  van - tot geocoordinaten
############################
use strict;

# de naam van de cache file om de api gegevens tijdelijk te cachen
my $cacheName = "here.cache";

if(scalar @ARGV < 4) {
	print "Usage: here fromlat fromlng tolat tolng avoidHighways\n";
	exit(1);
}

# Argumenten
# geo coordinaten
my $fromLat = @ARGV[0]; #"51.05633";
my $fromLng = @ARGV[1]; #"3.69485";
my $toLat = @ARGV[2]; #"51.038768";
my $toLng = @ARGV[3]; #"3.736953";

# flag dat aangeeft of we de highways moeten avoiden of niet
my $avoidHighways = 0;
if(scalar @ARGV >= 5) {
	$avoidHighways = @ARGV[4];
}

# voer de main functie uit de de gegevens zal opvragen en printen
main();

sub main {

	# we moeten eerst op zoek gaan naar een geldige API Key die we kunnen gebruiken
	# de API Key cachen we in een file zodat dit niet steeds opnieuw moet opgevraagd worden
	my $appCode;
	my $appId;
	
	# check of de api gecached is
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
	
	# haal de route gegevens op
	printRouteData($appCode, $appId);

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
# Haalt de API Key gegevens op uit de website html
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

##############################
# Print de route data met behulp van de gegeven api key
##############################
sub printRouteData {
	(my $appCode, my $appId) = @_;
	
	# als we highways moeten avoiden is dit de extra parameter die moet meegegeven worden
	my $motorways = "";
	if($avoidHighways) {
		$motorways = "motorway:-2";
	}
	
	# bouw url op om de route jsonp response terug te krijgen
	my $url = 'https://route.api.here.com/routing/7.2/calculateroute.json?alternatives=0&app_code=' . $appCode .'&app_id=' . $appId . '&jsonAttributes=41&language=en_US&legattributes=all&linkattributes=none,sh,ds,rn,ro,nl,pt,ns,le,fl&maneuverattributes=all&metricSystem=metric&mode=fastest;car;traffic:enabled;' .$motorways . '&routeattributes=none,sh,wp,sm,bb,lg,no,li,tx,la&transportModeType=car&waypoint0=geo!' . $fromLat . ',' . $fromLng . '&waypoint1=geo!' . $toLat . ',' . $toLng . '';
	
	# vraag gegevens op met curl en store de json response
	my $response = `curl --insecure -s -o - "$url"`;
	
	# haal het stuk met de gegevens uit de response, dat is sneller dan over kilobytes aan text regexxen
	my $idx = index($response, "summary");
	my $part = substr($response,$idx, 500);
	
	# parameters die er uit moeten gehaald worden
	my $distance;
	my $trafficTime;
	my $baseTime;
	
	# Parse met regex de afstand uit de json
	if($part =~ /.*?\"?distance\"?\:\s*(.*?),.*?/g) {
		$distance = $1;
	}
	
	# Parse met regex de trafficTime uit de json
	if($part =~ /.*?\"?trafficTime\"?\:\s*(.*?),.*?/g) {
		$trafficTime = $1;
	}
	
	# Parse met regex de baseTime uit de json
	if($part =~ /.*?\"?baseTime\"?\:\s*(.*?),.*?/g) {
		$baseTime = $1;
	}
	
	# bereken de vertraging
	my $delay = $trafficTime - $baseTime;
	
	# print de header en gegevens
	print "totalDistanceMeters;totalTimeSeconds;totalDelaySeconds\n";
	print $distance . ";" . $trafficTime . ";" . $delay . "\n";
		
}