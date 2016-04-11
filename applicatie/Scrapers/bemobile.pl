############################
# Bemobile.pl scraped de route details met gegeven  van - tot geocoordinaten
############################
use strict;

# Argumenten 
my $fromLat = @ARGV[0]; #"51.05633";
my $fromLng = @ARGV[1]; #"3.69485";
my $toLat = @ARGV[2]; #"51.038768";
my $toLng = @ARGV[3]; #"3.736953";

# JSON argumenten die moeten meegestuurd worden
my $params = '{"FR":{"COORD":{"LA":' . $fromLat .',"LO":' . $fromLng . '},"TD":""},"M":["car"],"ST":"\/Date(' . time . '+0200)\/","EC":"rs","O":"","RC":"os","TO":{"COORD":{"LA":' . $toLat . ',"LO":' . $toLng . '},"TD":""}}';

# Bouw curl command line op,. Er is een klein verschil tussen windows en linux
my $exec;
if($^O eq 'MSWin32') {
	$exec = 'echo ' . $params . ' | curl --insecure -s -o - --header "Content-Type: application/json" -d @- "http://touring.api.be-mobile.be/service/TravelTimes/Personal3"';
}
else {
	$exec = 'curl -s -o - --header "Content-Type: application/json" --data \'' . $params . '\' "http://touring.api.be-mobile.be/service/TravelTimes/Personal3"';
}

# lees de volledige response uit
my $response = `$exec`;

# de afstand, normale tijd en vertraging zullen geparsed worden
my $distance;
my $baseTime;
my $delay;

# ga op zoek naar de L:value in de json response, dat is de afstand
if($response =~ /.*?\"?L\"?\:\s*(.*?),.*?/g) {
     $distance = int($1 / 1000);
}

# ga op zoek naar de TA:value, property dat is de tijd die nodig is "Travel Arrival Time"
while($response =~ /.*?\"?TA\"?\:\s*(.*?),.*?/g) {
     $baseTime = $1;
}

# ga op zoek naar de TD:value json property,  dat is de totale delay om de route af te lopen
while($response =~ /.*?\"?TD\"?\:\s*(.*?),.*?/g) {
     $delay = $1;
}

# print het resultaat in een ; delimited string met header
print "totalDistanceMeters;totalTimeSeconds;totalDelaySeconds\n";
print $distance . ";" . $baseTime . ";" . $delay . "\n";




