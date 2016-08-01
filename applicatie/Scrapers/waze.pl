############################
# Waze.pl scraped de route details met gegeven  van - tot geocoordinaten
############################

# maak gebruik van XS:Json om de json te parsen, we moeten over de verschillende route segments sommeren
use JSON;
use strict;

if(scalar @ARGV < 4) {
	print "Usage: waze.pl fromlat fromlng tolat tolng avoidPrimaries\n";
	exit(1);
}

# Argumenten 
my $fromLat = @ARGV[0]; #"51.05633";
my $fromLng = @ARGV[1]; #"3.69485";
my $toLat = @ARGV[2]; #"51.038768";
my $toLng = @ARGV[3]; #"3.736953";

# flag dat aangeeft of we de highways moeten avoiden of niet
my $avoidPrimaries = 0;
if(scalar @ARGV >= 5) {
	$avoidPrimaries = @ARGV[4];
}

# bouw url op om gegevens op te vragen
my $url = 'https://www.waze.com/row-RoutingManager/routingRequest?from=x%3A' . $fromLng . '+y%3A' . $fromLat .'&to=x%3A' . $toLng . '+y%3A' . $toLat . '&at=0&returnJSON=true&returnGeometries=true&returnInstructions=true&timeout=60000&nPaths=1&clientVersion=4.0.0&options=AVOID_TRAILS%3At%2CALLOW_UTURNS%3At';

# als we highways moeten avoiden is dit de parameter die moet meegegeven worden
if($avoidPrimaries) {
	$url = $url . '%2CAVOID_PRIMARIES%3At';
}

# vraag json op
my $json = `curl --insecure -s -o - "$url"`;

# parse de json met XS:Json naar een object model
my $response =  from_json($json);

# bepaal de resultaten array
my @results = @{ $response->{"response"}->{"results"} };

# hou sommen bij 
my $distanceSum = 0;
my $crossTimeSum = 0;
my $crossTimeWithoutRealTimeSum = 0;
# overloop alle route segmenten en tel de values op
for	my $r (@results) {
	$crossTimeSum += $r->{"crossTime"};
	$crossTimeWithoutRealTimeSum += $r->{"crossTimeWithoutRealTime"};
	$distanceSum += $r->{"length"};
}

# bepaal de vertraging
my $delay = $crossTimeSum - $crossTimeWithoutRealTimeSum;
if($delay < 0) {
	$delay = 0;
}

# print de header en de berekende gegevens
print "totalDistanceMeters;totalTimeSeconds;totalDelaySeconds\n";
print $distanceSum . ";" . $crossTimeSum . ";" . $delay . "\n";