use strict;

if(scalar @ARGV < 4) {
	print "Usage: tomtom fromlat fromlng tolat tolng\n";
	exit(1);
}

my $fromLat = @ARGV[0]; #"51.05633";
my $fromLng = @ARGV[1]; #"3.69485";
my $toLat = @ARGV[2]; #"51.038768";
my $toLng = @ARGV[3]; #"3.736953";

main();

sub main {

	my $apiKey;
	
	my $cache = readCache();
	if(exists $cache->{"apikey"} && $cache->{"apikey"} ne "") {
		$apiKey = $cache->{"apikey"};
	}
	else {
		$apiKey = getAPIKey();
		$cache->{"apikey"} = $apiKey;
		saveAPIKey($apiKey);
	}
	
	#print $apiKey;
	#print "\n";
	printRouteData($apiKey);

}

sub readCache {
	my @args = @ARGV;
	@ARGV = ( "viamichelin.cache" );
	
	(my $dev,my $ino,my $mode,my $nlink,my $uid,my $gid,my $rdev,my $size,
   my $atime,my $mtime,my $ctime,my $blksize,my $blocks) =  stat("viamichelin.cache");
   
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
	
	open my $fh, ">viamichelin.cache";
	print $fh "apikey=$apikey";
	close $fh;
}


sub getAPIKey {
	my $url = "http://www.viamichelin.com/web/Routes";
	my $response = `curl -s -o - '$url'`;
	
	
	# eerst substringen naar de apikey: is STUKKEN sneller
	#print $response;
	my $idx = index($response, "/js?key=");
	my $part = substr($response,$idx, 200);
	
	#print $part;
	if($part =~ /.*=(.*)\$/gc) {
		return $1;
	}
	
	print "API KEY NOT FOUND";
	exit(1);
}


sub printRouteData {
	(my $apiKey) = @_;
	
	my $url = 'http://vmrest.viamichelin.com/apir/4/iti.json/eng/geom;header;roadsheet?steps=1:e:' . $fromLng . ':' . $fromLat . ';1:e:' . $toLng . ':' . $toLat . ';&distUnit=m&veht=0&itit=1&avoidClosedRoad=false&currency=EUR&fuelCost=1.36&fuelConsump=6.8:5.6:5.6&favMotorways=false&avoidBorders=false&avoidTolls=false&avoidCCZ=false&avoidORC=false&avoidPass=false&wCaravan=false&withSecurityAdv=true&fullMapOpt=300:300:true:true:true&stepMapOpt=300:300:true:true:true&multipleIti=true&traffic=AUT;BEL;CHE;DEU;DNK;ESP;FIN;FRA;GBR;ITA;LUX;NLD;NOR;POL;SWE&obfuscation=false&ie=UTF-8&charset=UTF-8&callback=JSE.HTTP.asyncRequests[32].HTTPResponseLoaded&authKey=' . $apiKey . '&lg=eng&nocache=' . time;
		
	#print $url;
	#print "$url\n";
	my $response = `curl --insecure -s --globoff -o - "$url"`;
	
	my $idx = index($response, "totalDist");
	my $part = substr($response,$idx, 1500);
	
	my $trafficTime;
	my $baseTime;
	my $distance;
	
	#print $response;
	#print $part;
	
	if($part =~ /.*?\"?trafficTime\"?\:\s*(.*?),.*?/) {
		$trafficTime = int($1);
	}
	
	if($part =~ /.*?\"?drivingTime\"?\:\s*(.*?),.*?/) {
		$baseTime = int($1);
	}
	
	if($part =~ /.*?\"?drivingDist\"?\:\s*(.*?),.*?/) {
		$distance = int($1);
	}
	
	my $delay = $trafficTime - $baseTime;
	if($delay < 0) {
		$delay = 0;
	}
	
	print "totalDistanceMeters;totalTimeSeconds;totalDelaySeconds\n";
	print $distance . ";" . $trafficTime . ";" . $delay . "\n";
}