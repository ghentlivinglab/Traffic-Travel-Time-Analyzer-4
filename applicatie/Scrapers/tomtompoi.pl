use strict;
use JSON;

if(scalar @ARGV < 4) {
	print "Usage: tomtompoi min_lat min_lng max_lat max_lng\n";
	exit(1);
}

my $fromLat = @ARGV[0]; #"51.05633";
my $fromLng = @ARGV[1]; #"3.69485";
my $toLat = @ARGV[2]; #"51.038768";
my $toLng = @ARGV[3]; #"3.736953";


my %iconMeaning = ( 	0 => "unknown", 1 => "accident", 2 => "fog", 3 => "dangerous_conditions", 4 => "rain", 5 => "ice", 6 => "jam", 7 => "lane_closed", 8 => "road_closed", 9 => "road_works",
						10 => "wind", 11 => "flooding", 12 => "detour", 13 => "cluster");
				
my %tyMeaning = ( 0 => "unknown", 1 => "minor", 2 => "moderate", 3 => "major", 4 => "undefined" );

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
	printPOI($apiKey);

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


sub printPOI {
	(my $apiKey) = @_;
	
	# TODO bounding box lat lng -> x,y
	my $viewportUrl = 'http://api.internal.tomtom.com/lbs/services/viewportDesc/3/6624851.19157%2C410625.297053%2C6640081.269456%2C423657.810376/13/6618707.565422%2C389624.223532%2C6646224.895605%2C444658.883897/9/false/json?key=8r734zursdrdrvcejfhedk8q';
	
	my $viewportJson = `curl --insecure -s -o - "$viewportUrl"`;
	my $viewportResponse = from_json($viewportJson);
	
	#print $viewportJson;
	my $trafficModelId =  $viewportResponse->{"viewpResp"}->{"trafficState"}->{'@trafficModelId'};
	
	#print "Traffic model id: " . $trafficModelId;
	#http://api.internal.tomtom.com/lbs/services/trafficIcons/3/s3/50.98022,3.62719,51.19782,3.83324/13/1456502762951/json?jsonp=jsonp1456502809288&key=dvbfcb88hkrje9ur2fs84uxn&projection=EPSG4326&language=en&style=s3&expandCluster=true
	my $url = 'http://api.internal.tomtom.com/lbs/services/trafficIcons/3/s3/' . $fromLat . ',' . $fromLng . ',' . $toLat . ',' . $toLng . '/13/' . $trafficModelId . '/json?&key=' . $apiKey . '&projection=EPSG4326&language=nl&style=s3&expandCluster=true';
	
	#print $url;
	
	my $json = `curl --insecure -s -o - "$url"`;
	
	my $response = from_json($json);
#	print $json;

	my @pois = @{ $response->{"tm"}->{"poi"} };
	
	print "id;lat;lng;type;traffictype;comments";
	print "\n";
	
	for my $p (@pois) {
	
		print $p->{"id"};
		print ";";
		print $p->{"p"}->{"y"};
		print ";";
		print $p->{"p"}->{"x"};
		print ";";
		print $iconMeaning{$p->{"ic"}};
		print ";";
		print $tyMeaning{$p->{"ty"}};
		print ";";
		print $p->{"d"};
		print "\n";
	}
}