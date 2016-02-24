use strict;

@ARGV = ( "tomtomdata.csv");

# data in csv is for route 'Drongensesteenweg (N466) eastbound', which is id  27
my $routeid = 27;
my $provider = 0; #tom tom is 0 in enum"

while(<>) {
	chomp;
	
	my @parts = split(';', $_);
	
	#departureTime;lengthInMeters;travelTimeInSeconds;trafficDelayInSeconds
	#2016-02-13T20:40:17+01:00;3823;456;0
	
	my $date = $parts[0];
	$date =~ s/([0-9\-]+)T([0-9]+:[0-9]+:[0-9]+).*/\1 \2/gc;
	
	
	print "INSERT INTO RouteData (RouteId, Timestamp, Provider, TravelTime) VALUES ";
	print "(";
	print $routeid;
	print ",";
	print "'" . $date . "'";
	print ",";
	print $provider;
	print ",";
	print $parts[2];
	print ");";
	print "\n";
}