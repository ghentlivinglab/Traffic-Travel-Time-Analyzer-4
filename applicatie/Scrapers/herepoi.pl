use strict;
use JSON;

my $cachename = "here.cache";

if(scalar @ARGV < 4) {
	print "Usage: herepoi min_lat min_lng max_lat max_lng\n";
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
	}
	else {
		my $obj = getAppCodeAndId();
		$cache->{"appCode"} = $obj->{"appCode"};
		$cache->{"appId"} = $obj->{"appId"};
		
		saveCache($cache);
	}
	
	$appCode = $cache->{"appCode"};
	$appId = $cache->{"appId"};
		
		
	#print "\n";
	printPOI($appCode, $appId);

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


sub printPOI {
	(my $appCode, my $appId) = @_;
	
	my $url = 'https://traffic.cit.api.here.com/traffic/6.0/incidents.json?bbox=' . $toLat . '%2C' . $fromLng . '%3B' . $fromLat . '%2C' . $toLng . '&criticality=0,1,2&app_id=' . $appId .'&app_code=' . $appCode . '';
	
	#print $url;
	
	my $json = `curl --insecure -s -o - "$url"`;
	my $response = from_json($json);
	
	#print $response;
	my @items = @{ $response->{"TRAFFICITEMS"}->{"TRAFFICITEM"} };
	
	print "id;lat;lng;type;traffictype;comments";
	print "\n";
	
	for my $item (@items) {
		
		print $item->{"TRAFFICITEMID"};
		print ";";
		print $item->{"LOCATION"}->{"GEOLOC"}->{"ORIGIN"}->{"LATITUDE"};
		print ";";
		print $item->{"LOCATION"}->{"GEOLOC"}->{"ORIGIN"}->{"LONGITUDE"};
		print ";";
		print $item->{"TRAFFICITEMTYPEDESC"};
		print ";";
		print $item->{"CRITICALITY"}->{"DESCRIPTION"};
		print ";";
		if($item->{"COMMENTS"} || $item->{"COMMENTS"} eq "") {
			print $item->{"RDSTMCLOCATIONS"}->{"RDSTMC"}->[0]->{"ALERTC"}->{"DESCRIPTION"};
		}
		else {
			print $item->{"COMMENTS"};
		}
		
		print "\n";
	}
	
}