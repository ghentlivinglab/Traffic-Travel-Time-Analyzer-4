use strict;

if(scalar @ARGV < 4) {
	print "Usage: tomtom fromlat fromlng tolat tolng avoidHighways\n";
	exit(1);
}

my $fromLat = @ARGV[0]; #"51.05633";
my $fromLng = @ARGV[1]; #"3.69485";
my $toLat = @ARGV[2]; #"51.038768";
my $toLng = @ARGV[3]; #"3.736953";
my $avoidHighways = 0;
if(scalar @ARGV >= 5) {
	$avoidHighways = @ARGV[4];
}
main();

sub main {

	my $apiKey;
	
	my $cache = readCache();
	if(exists $cache->{"apikey"} && $cache->{"apikey"} ne "") {
		$apiKey = $cache->{"apikey"};
	}
	else {
		my $bootstrapUrl = getBootstrapScriptUrl();
		$apiKey = getAPIKey($bootstrapUrl);
		$cache->{"apikey"} = $apiKey;
		saveAPIKey($apiKey);
	}
	
	#print $apiKey;
	#print "\n";
	printRouteData($apiKey);

}

sub readCache {
	my @args = @ARGV;
	@ARGV = ( "tomtom.cache" );
	
	(my $dev,my $ino,my $mode,my $nlink,my $uid,my $gid,my $rdev,my $size,
   my $atime,my $mtime,my $ctime,my $blksize,my $blocks) =  stat("tomtom.cache");
   
   # als cache ouder is dan een uur gebruik cache niet meer
   my $age = time - $mtime;
   if($age > 60 * 60) {
		@ARGV = @args;
		return {};
   }		
	
	my $obj = {};
	
	while(<>) {
		chomp;
		my @parts = split("=", $_);
		$obj->{$parts[0]} = $parts[1];
	}
	
	@ARGV = @args;
	return $obj;
}

sub saveAPIKey {
	(my $apikey) = @_;
	
	open my $fh, ">tomtom.cache";
	print $fh "apikey=$apikey";
	close $fh;
}



sub getBootstrapScriptUrl {
	my $url ='http://routes.tomtom.com/#/route/' . $fromLat . '°N, ' . $fromLng . '°E@' . $fromLat . ',' . $fromLng . '@-1/' . $toLat . '°N, ' . $toLng . '°E@' . $toLat . ',' . $toLng . '@-1/?leave=now&traffic=false&zoom=12';
	my $response = `curl --insecure -s -o - "$url"`;

	if($response =~ /.*"(.*Bootstrap\.js.*?)".*/gc) {
		#print "Bootstrapscript at $1\n";
		return "http://routes.tomtom.com" . $1;
	}
	return "";
}

sub getAPIKey {
	(my $bootstrapUrl) = @_;
	my $response = `curl --insecure -s -o - "$bootstrapUrl"`;
	
	#print "parsing api key\n";
	# apikey:'dvbfcb88hkrje9ur2fs84uxn'
	
	# eerst substringen naar de apikey: is STUKKEN sneller
	#print $response;
	my $idx = index($response, "apikey:");
	my $part = substr($response,$idx, 200);
	
	#print $part;
	if($part =~ /.*apikey\:\s*'(.*?)'.*/gc) {
		return $1;
	}
	
	print "API KEY NOT FOUND";
	exit(1);
}


sub printRouteData {
	(my $apiKey) = @_;
	
	my $routeType;
	if($avoidHighways) {
		$routeType = "Shortest";
	}
	else {
		$routeType = "Quickest";
	}
	my $url = 'http://api.internal.tomtom.com/lbs/services/route/3/' . $fromLat . ',' . $fromLng . ':' . $toLat . ',' . $toLng . '/' . $routeType .'/json?key=' . $apiKey . '&language=en&projection=EPSG4326&avoidTraffic=false&includeTraffic=true&day=today&time=now&iqRoutes=2&trafficModelId=1455899653197&map=basic';
	#my $url = 'https://api.tomtom.com/routing/1/calculateRoute/' .$fromLat . ',' . $fromLng . ':' . $toLat . ',' . $toLng . '/json?key=' . $apiKey . 
	
	
	#print $url;
	
	my $response = `curl --insecure -s -o - "$url"`;
	
	#print $response;
	if($response =~ /.*?"summary"\s*\:\s*\{(.*?)"instructions"/gc) {
		#print $1;
		
		my $summary = $1;
		
		my @parts = split(",", $summary);
		
		my %obj;
		for my $p (@parts) {
			my @lineparts = split(':', $p);
			$obj{$lineparts[0]} = substr($p, length($lineparts[0]) + 1);
		}
		
		#print join("\n" , map { $_ . " -> " . $obj{$_} } keys %obj);
		
		print "totalDistanceMeters;totalTimeSeconds;totalDelaySeconds\n";
		print $obj{'"totalDistanceMeters"'} . ";" . $obj{'"totalTimeSeconds"'} . ";" . $obj{'"totalDelaySeconds"'} . "\n";
		
		
		#print "\n-----------------------\n";
	}
	
}