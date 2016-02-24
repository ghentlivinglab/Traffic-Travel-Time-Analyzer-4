use JSON;
use strict;

if(scalar @ARGV < 4) {
	print "Usage: waze.pl fromlat fromlng tolat tolng\n";
	exit(1);
}

my $fromLat = @ARGV[0]; #"51.05633";
my $fromLng = @ARGV[1]; #"3.69485";
my $toLat = @ARGV[2]; #"51.038768";
my $toLng = @ARGV[3]; #"3.736953";


my $url = 'https://www.waze.com/row-RoutingManager/routingRequest?from=x%3A' . $fromLng . '+y%3A' . $fromLat .'&to=x%3A' . $toLng . '+y%3A' . $toLat . '&at=0&returnJSON=true&returnGeometries=true&returnInstructions=true&timeout=60000&nPaths=1&clientVersion=4.0.0&options=AVOID_TRAILS%3At%2CALLOW_UTURNS%3At';

#print $url;
#print "\n";

my $json = `curl --insecure -s -o - "$url"`;

my $response =  from_json($json);

my @results = @{ $response->{"response"}->{"results"} };

my $distanceSum = 0;
my $crossTimeSum = 0;
my $crossTimeWithoutRealTimeSum = 0;
for	my $r (@results) {
	$crossTimeSum += $r->{"crossTime"};
	$crossTimeWithoutRealTimeSum += $r->{"crossTimeWithoutRealTime"};
	$distanceSum += $r->{"length"};
}

my $delay = $crossTimeSum - $crossTimeWithoutRealTimeSum;
if($delay < 0) {
	$delay = 0;
}
print "totalDistanceMeters;totalTimeSeconds;totalDelaySeconds\n";
print $distanceSum . ";" . $crossTimeSum . ";" . $delay . "\n";
	
#print "Distance: $distanceSum\n";
#print "Cross time without real time: $crossTimeWithoutRealTimeSum\n";
#print "Cross time: $crossTimeSum\n";
