############################
# ViaMichelin.pl scraped de route details met gegeven  van - tot geocoordinaten
############################
use strict;

# Check of alle argumenten zijn opgegeven
if(scalar @ARGV < 4) {
	print "Usage: viamichelin fromlat fromlng tolat tolng\n";
	exit(1);
}

# Argumenten 
my $fromLat = @ARGV[0]; #"51.05633";
my $fromLng = @ARGV[1]; #"3.69485";
my $toLat = @ARGV[2]; #"51.038768";
my $toLng = @ARGV[3]; #"3.736953";

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
		$apiKey = getAPIKey();
		$cache->{"apikey"} = $apiKey;
		saveAPIKey($apiKey);
	}
	
	# haal de route gegevens op
	printRouteData($apiKey);
}

##################
# Leest de gecachede properties van de cache file
##################
sub readCache {
	my @args = @ARGV;
	@ARGV = ( "viamichelin.cache" );
	
	# check de last modified time van de  cache file
	(my $dev,my $ino,my $mode,my $nlink,my $uid,my $gid,my $rdev,my $size,
   my $atime,my $mtime,my $ctime,my $blksize,my $blocks) =  stat("viamichelin.cache");
   
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
# Saved de API key in de cache
#################
sub saveAPIKey {
	(my $apikey) = @_;
	
	open my $fh, ">viamichelin.cache";
	print $fh "apikey=$apikey";
	close $fh;
}

########################
# Haalt de API Key op uit de website html
########################
sub getAPIKey {
	my $url = "http://www.viamichelin.com/web/Routes";
	my $response = `curl -s -o - '$url'`;
	
	# eerst substringen naar de apikey: is STUKKEN sneller	
	my $idx = index($response, "/js?key=");
	my $part = substr($response,$idx, 200);
	
	if($part =~ /.*=(.*)\$/gc) {
		return $1;
	}
	
	print "API KEY NOT FOUND";
	exit(1);
}

##############################
# Print de route data met behulp van de gegeven api key
##############################
sub printRouteData {
	(my $apiKey) = @_;
	
	# bouw url op om de route jsonp response terug te krijgen
	my $url = 'http://vmrest.viamichelin.com/apir/4/iti.json/eng/geom;header;roadsheet?steps=1:e:' . $fromLng . ':' . $fromLat . ';1:e:' . $toLng . ':' . $toLat . ';&distUnit=m&veht=0&itit=1&avoidClosedRoad=false&currency=EUR&fuelCost=1.36&fuelConsump=6.8:5.6:5.6&favMotorways=false&avoidBorders=false&avoidTolls=false&avoidCCZ=false&avoidORC=false&avoidPass=false&wCaravan=false&withSecurityAdv=true&fullMapOpt=300:300:true:true:true&stepMapOpt=300:300:true:true:true&multipleIti=true&traffic=AUT;BEL;CHE;DEU;DNK;ESP;FIN;FRA;GBR;ITA;LUX;NLD;NOR;POL;SWE&obfuscation=false&ie=UTF-8&charset=UTF-8&callback=JSE.HTTP.asyncRequests[32].HTTPResponseLoaded&authKey=' . $apiKey . '&lg=eng&nocache=' . time;
		
	# vraag gegevens op met curl en store de jsonp response
	my $response = `curl --insecure -s --globoff -o - "$url"`;
	
	# subtstring het stuk dat we willen hebben
	my $idx = index($response, "totalDist");
	my $part = substr($response,$idx, 1500);

	# parameters die er uit moeten gehaald worden
	my $trafficTime;
	my $baseTime;
	my $distance;
	
	# Parse met regex de trafficTime:value json property
	if($part =~ /.*?\"?trafficTime\"?\:\s*(.*?),.*?/) {
		$trafficTime = int($1);
	}
	
	# Parse met regex de drivingTime:value json property
	if($part =~ /.*?\"?drivingTime\"?\:\s*(.*?),.*?/) {
		$baseTime = int($1);
	}
	
	# Parse met regex de drivingDist:value json property
	if($part =~ /.*?\"?drivingDist\"?\:\s*(.*?),.*?/) {
		$distance = int($1);
	}
	
	# bereken vertraging
	my $delay = $trafficTime - $baseTime;
	if($delay < 0) {
		$delay = 0;
	}
	
	# print header en data
	print "totalDistanceMeters;totalTimeSeconds;totalDelaySeconds\n";
	print $distance . ";" . $trafficTime . ";" . $delay . "\n";
}