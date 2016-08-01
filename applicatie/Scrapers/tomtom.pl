############################
# Tomtom.pl scraped de route details met gegeven  van - tot geocoordinaten
############################
use strict;

if(scalar @ARGV < 4) {
	print "Usage: tomtom fromlat fromlng tolat tolng avoidHighways\n";
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

my $cacheName = "tomtom.cache";

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
		# vraag eerst de website op waar in de javascript file  gereferenced wordt
		#my $bootstrapUrl = getBootstrapScriptUrl();
		# // EDIT: de website is aangepast, de api key staat nu ergens anders
		my $bootstrapUrl = 'https://mydrive.tomtom.com/onp-conf.js?v=1.04';
		# haal nu de api key uit de javascript file
		$apiKey = getAPIKey($bootstrapUrl);
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
##################
sub getBootstrapScriptUrl {
	my $url ='http://routes.tomtom.com/#/route/' . $fromLat . '°N, ' . $fromLng . '°E@' . $fromLat . ',' . $fromLng . '@-1/' . $toLat . '°N, ' . $toLng . '°E@' . $toLat . ',' . $toLng . '@-1/?leave=now&traffic=false&zoom=12';
	my $response = `curl --insecure -s -o - "$url"`;

	# parse de referentie naar de js file met regex
	if($response =~ /.*"(.*Bootstrap\.js.*?)".*/gc) {
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
	my $idx = index($response, "NKW_ROUTING_API_KEY:");
	my $part = substr($response,$idx, 200);
	
	# parse de apikey uit de javascript file met regex
	if($part =~ /.*NKW_ROUTING_API_KEY\:\s*"(.*?)".*/gc) {
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
	
	# als we highways moeten avoiden is dit de parameter die moet meegegeven worden
	my $routeType;
	if($avoidHighways) {
		$routeType = "shortest";
	}
	else {
		$routeType = "fastest";
	}
	
	# bouw url op
	my $url = 'https://api.tomtom.com/routing/1/calculateRoute/' . $fromLat . ',' . $fromLng . ':' . $toLat . ',' . $toLng . '/json?key=' . $apiKey . '&language=en&traffic=true&report=effectiveSettings&travelMode=car&routeType=' . $routeType;
	
	# vraag gegevens op
	my $response = `curl --insecure -s -o - "$url"`;
	
	# haal de volledige inhoud van de instructions json property uit de response met regex
	if($response =~ /"routes":\[{"summary":{(.*?)}/gc) {
		my $summary = $1;
		
		# split de instructions in stukken
		my @parts = split(",", $summary);
		
		# voor elk stuk haal de nodige gegevens eruit en steek ze in een hash
		my %obj;
		for my $p (@parts) {
			my @lineparts = split(':', $p);
			$obj{$lineparts[0]} = substr($p, length($lineparts[0]) + 1);
		}
		
		#Alle mogelijke parameters: print join("\n" , map { $_ . " -> " . $obj{$_} } keys %obj);
		
		#print de header en de route data
		print "totalDistanceMeters;totalTimeSeconds;totalDelaySeconds\n";
		print $obj{'"lengthInMeters"'} . ";" . $obj{'"travelTimeInSeconds"'} . ";" . $obj{'"trafficDelayInSeconds"'} . "\n";
		
	}
}