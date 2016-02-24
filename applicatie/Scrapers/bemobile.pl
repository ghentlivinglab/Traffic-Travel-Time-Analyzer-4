use strict;

my $fromLat = @ARGV[0]; #"51.05633";
my $fromLng = @ARGV[1]; #"3.69485";
my $toLat = @ARGV[2]; #"51.038768";
my $toLng = @ARGV[3]; #"3.736953";


my $params = '{"FR":{"COORD":{"LA":' . $fromLat .',"LO":' . $fromLng . '},"TD":""},"M":["car"],"ST":"\/Date(' . time . '+0200)\/","EC":"","O":"","RC":"","TO":{"COORD":{"LA":' . $toLat . ',"LO":' . $toLng . '},"TD":""}}';


my $exec;
if($^O eq 'MSWin32') {
	$exec = 'echo ' . $params . ' | curl --insecure -s -o - --header "Content-Type: application/json" -d @- "http://touring.api.be-mobile.be/service/TravelTimes/Personal3"';
}
else {
	$exec = 'curl -s -o - --header "Content-Type: application/json" --data \'' . $params . '\' "http://touring.api.be-mobile.be/service/TravelTimes/Personal3"';
}

my $response = `$exec`;
#print $response;
my $distance;
my $baseTime;
my $delay;

if($response =~ /.*?\"?L\"?\:\s*(.*?),.*?/g) {
     $distance = int($1 / 1000);
}


while($response =~ /.*?\"?TA\"?\:\s*(.*?),.*?/g) {
     $baseTime = $1;
}

while($response =~ /.*?\"?TD\"?\:\s*(.*?),.*?/g) {
     $delay = $1;
}



#print $response;

#print "Distance: $distance\n";
#print "Base time: $baseTime\n";
#print "Delay: $delay\n";

print "totalDistanceMeters;totalTimeSeconds;totalDelaySeconds\n";
print $distance . ";" . $baseTime . ";" . $delay . "\n";




