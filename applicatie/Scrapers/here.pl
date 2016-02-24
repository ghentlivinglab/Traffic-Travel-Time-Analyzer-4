use strict;

my $cachename = "here.cache";

if(scalar @ARGV < 4) {
	print "Usage: here fromlat fromlng tolat tolng\n";
	exit(1);
}

my $fromLat = @ARGV[0]; #"51.05633";
my $fromLng = @ARGV[1]; #"3.69485";
my $toLat = @ARGV[2]; #"51.038768";
my $toLng = @ARGV[3]; #"3.736953";

main();

sub main {

	my $appCode;
	my $appId;
	
	my $cache = readCache();
	if(exists $cache->{"appCode"} && $cache->{"appCode"} ne "" && 
	   exists $cache->{"appId"} && $cache->{"appId"} ne "") {
		$appCode = $cache->{"appCode"};
		$appId = $cache->{"appId"};
	}
	else {
		
		my $obj = getAppCodeAndId();
		$cache->{"appCode"} = $obj->{"appCode"};
		$cache->{"appId"} = $obj->{"appId"};
		saveCache($cache);
	}
	
	
	#print "\n";
	printRouteData($appCode, $appId);

}

sub readCache {
	my @args = @ARGV;
	@ARGV = ( $cachename );
	
	(my $dev,my $ino,my $mode,my $nlink,my $uid,my $gid,my $rdev,my $size,
   my $atime,my $mtime,my $ctime,my $blksize,my $blocks) =  stat("here.cache");
   
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

sub saveCache {
	(my $cache) = @_;
	
	my %c = %{ $cache };
	open my $fh, ">" . $cachename;
	for my $k (keys %c) {
		print $fh $k . "=" . $c{$k} . "\n";
	}
	close $fh;
}




sub getAppCodeAndId {
	my $url = 'https://maps.here.com/directions/drive/N' . $fromLat . '-,-E' . $fromLng . ':' . $fromLat . ',' . $fromLng . '/N' . $toLat . '-,-E' . $toLng .':' . $toLat . ',' . $toLng .'?map=' . $toLat . ',' . $toLng . ',normal&avoid=carHOV';
	
	my $response = `curl --insecure -s -o - "$url"`;
	
	#print "parsing api key\n";
	# "appCode":"djPZyynKsbTjIUDOBcHZ2g","appId":"xWVIueSv6JL0aJ5xqTxb"
	
	# eerst substringen naar de apikey: is STUKKEN sneller
	#print $response;
	my $idx = index($response, "appCode");
	my $part = substr($response,$idx, 200);
	
	my $appCode;
	my $appId;
	#print $part;
	if($part =~ /.*?\"?appCode\"?\:\s*(\'|\")(.*?)(\1).*?/gc) {
		#print "Code: " . $2;
		$appCode = $2;
	}
	
	my $part2 = $part;
	if($part2 =~ /.*?\"?appId\"?\:\s*(\'|\")(.*?)(\1).*?/gc) {
		#print "Id: " . $2;
		$appId = $2;
	}
	
	if($appCode ne "" && $appId ne "") {
		my $obj = { "appCode" => $appCode, "appId" => $appId };
		return $obj;
	}
	print "API KEY NOT FOUND";
	exit(1);
}


sub printRouteData {
	(my $appCode, my $appId) = @_;
	
	my $url = 'https://route.api.here.com/routing/7.2/calculateroute.json?alternatives=0&app_code=' . $appCode .'&app_id=' . $appId . '&jsonAttributes=41&language=en_US&legattributes=all&linkattributes=none,sh,ds,rn,ro,nl,pt,ns,le,fl&maneuverattributes=all&metricSystem=metric&mode=fastest;car;traffic:enabled;&routeattributes=none,sh,wp,sm,bb,lg,no,li,tx,la&transportModeType=car&waypoint0=geo!' . $fromLat . ',' . $fromLng . '&waypoint1=geo!' . $toLat . ',' . $toLng . '';
	
	#print $url;
	
	my $response = `curl --insecure -s -o - "$url"`;
	
	my $idx = index($response, "summary");
	my $part = substr($response,$idx, 500);
	
	my $distance;
	my $trafficTime;
	my $baseTime;
	#print $part;
	if($part =~ /.*?\"?distance\"?\:\s*(.*?),.*?/g) {
		$distance = $1;
	}
	if($part =~ /.*?\"?trafficTime\"?\:\s*(.*?),.*?/g) {
		$trafficTime = $1;
	}
	if($part =~ /.*?\"?baseTime\"?\:\s*(.*?),.*?/g) {
		$baseTime = $1;
	}
	
	#print "Base time: $baseTime\n";
	#print "Traffic time: $trafficTime\n";
	
	my $delay = $trafficTime - $baseTime;
	
	print "totalDistanceMeters;totalTimeSeconds;totalDelaySeconds\n";
	print $distance . ";" . $trafficTime . ";" . $delay . "\n";
		
		
		#print "\n-----------------------\n";
	
	
}