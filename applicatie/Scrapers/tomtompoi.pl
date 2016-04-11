############################
# Tomtompoi.pl scraped  alle poi gegevens binnen de opgegeven bounding box
############################
use strict;
use JSON;
use utf8; 
binmode(STDOUT, ":utf8");

if(scalar @ARGV < 4) {
	print "Usage: tomtompoi min_lat min_lng max_lat max_lng\n";
	exit(1);
}

# Argumenten
# geo coordinaten voor bounding box
my $fromLat = @ARGV[0]; #"51.05633";
my $fromLng = @ARGV[1]; #"3.69485";
my $toLat = @ARGV[2]; #"51.038768";
my $toLng = @ARGV[3]; #"3.736953";

# cache file voor api key tijdelijk in te bewaren
my $cacheName = "tomtompoi.cache";

# mapping van de types naar een duidelijkere omschrijving
my %iconMeaning = ( 	0 => "unknown", 1 => "accident", 2 => "fog", 3 => "dangerous_conditions", 
						4 => "rain", 5 => "ice", 6 => "jam", 7 => "lane_closed", 8 => "road_closed", 9 => "road_works",
						10 => "wind", 11 => "flooding", 12 => "detour", 13 => "cluster");
		
# mapping van het type		
my %tyMeaning = ( 0 => "unknown", 1 => "minor", 2 => "moderate", 3 => "major", 4 => "undefined" );

# voer de main functie uit de de gegevens zal opvragen en printen
main();

sub main {

	# we moeten eerst op zoek gaan naar een geldige API Key die we kunnen gebruiken
	# de API Key cachen we in een file zodat dit niet steeds opnieuw moet opgevraagd worden
	my $apiKey;
	
	# check of de api gecached is
	my $cache = readCache();
	if(exists $cache->{"apikey"} && $cache->{"apikey"} ne "") {
		$apiKey = $cache->{"apikey"};
	}
	else {
		# // EDIT: de website is aangepast, de api key staat nu ergens anders
		my $bootstrapUrl = "https://mydrive.tomtom.com/onp-conf.js?v=1.04"; #getBootstrapScriptUrl();
		$apiKey = getAPIKey($bootstrapUrl);
		$cache->{"apikey"} = $apiKey;
		saveAPIKey($apiKey);
	}
	
	#print $apiKey;
	#print "\n";
	printPOI($apiKey);

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
sub saveAPIKey {
	(my $apikey) = @_;
	
	open my $fh, ">$cacheName";
	print $fh "apikey=$apikey";
	close $fh;
}


#################
#  Geeft de bootstrap js url terug waaruit de API key gehaald kan worden
# EDIT: niet meer van toepassing sinds wijziging website op 8/4/2016
##################
sub getBootstrapScriptUrl {
	my $url ='http://routes.tomtom.com/#/route/' . $fromLat . '°N, ' . $fromLng . '°E@' . $fromLat . ',' . $fromLng . '@-1/' . $toLat . '°N, ' . $toLng . '°E@' . $toLat . ',' . $toLng . '@-1/?leave=now&traffic=false&zoom=12';
	my $response = `curl --insecure -s -o - "$url"`;

	if($response =~ /.*"(.*Bootstrap\.js.*?)".*/gc) {
		#print "Bootstrapscript at $1\n";
		return "http://routes.tomtom.com" . $1;
	}
	return "";
}


########################
# Haalt de API Key gegevens op uit de javascript file
########################
sub getAPIKey {
	(my $bootstrapUrl) = @_;
	# vraag js inhoud op
	my $response = `curl --insecure -s -o - "$bootstrapUrl"`;
	
	# eerst substringen naar de apikey: is STUKKEN sneller
	my $idx = index($response, "LBS_TRAFFIC_INCIDENTS_API_KEY:");
	my $part = substr($response,$idx, 200);
	
	# parse de apikey uit de javascript file met regex
	if($part =~ /.*LBS_TRAFFIC_INCIDENTS_API_KEY\:\s*"(.*?)".*/gc) {
		return $1;
	}
	
	print "API KEY NOT FOUND";
	exit(1);
}

sub printPOI {
	(my $apiKey) = @_;
	
	#http://api.internal.tomtom.com/lbs/services/trafficIcons/3/s3/50.98022,3.62719,51.19782,3.83324/13/1456502762951/json?jsonp=jsonp1456502809288&key=dvbfcb88hkrje9ur2fs84uxn&projection=EPSG4326&language=en&style=s3&expandCluster=true
	my $url = 'http://api-internal.tomtom.com/lbs/services/trafficIcons/3/s3/' . $fromLat . ',' . $fromLng . ',' . $toLat . ',' . $toLng . '/13/-1/json?key=' . $apiKey . '&projection=EPSG4326&language=nl&style=s3&expandCluster=true&originalPosition=true';
	
	# haal json op
	my $json = `curl --insecure -s -o - "$url"`;
	
	# gebruik xs:json om de json naar een object model om te zetten
	my $response = from_json($json);

	my @pois = @{ $response->{"tm"}->{"poi"} };
	
	# print de header
	print "id;lat;lng;type;traffictype;comments";
	print "\n";
	
	for my $p (@pois) {
	
		# print de id van de poi
		print $p->{"id"};
		print ";";
		
		# print de lat en lng
		print $p->{"p"}->{"y"};
		print ";";
		print $p->{"p"}->{"x"};
		print ";";
		
		# print de overeenkomstige enum waarde voor het type van de poi
		if($iconMeaning{$p->{"ic"}} eq "jam") {
			print "3"; # traffic jam
		}
		elsif($iconMeaning{$p->{"ic"}} eq "accident") {
			print "8"; # accident
		}
		elsif($iconMeaning{$p->{"ic"}} eq "lane_closed") {
			print "4"; # lane closed
		}
		elsif($iconMeaning{$p->{"ic"}} eq "road_closed") {
			print "5"; # road closed
		}
		elsif($iconMeaning{$p->{"ic"}} eq "road_works") {
			print "1"; #construction
		}
		else {
			print "0"; # unknown
		}		
		print ";";
		
		# print het type (major/minor, ...)
		print $tyMeaning{$p->{"ty"}};
		print ";";
		
		# print de comments als er zijn, anders gebruik de omschrijving van het type als comments
		if(!$p->{"d"} || $p->{"d"} eq "") {
			print $iconMeaning{$p->{"ic"}};
		}
		else {
			print $p->{"d"};
		}
		print "\n";
	}
}