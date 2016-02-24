use strict;

my $locations = "51.0387676,3.7369532:51.0563336,3.6948482";
my $key = "5j7n539vbsbf6frb7kwzxtc6";
my $url = "https://api.tomtom.com/routing/1/calculateRoute/$locations/json/?key=$key&traffic=true";



#@ARGV = ( "tomtomrequest.json" );
#$/ = "";
#my $json = <>;

my $json = `curl -s -o - $url`;

#print $json;

while($json =~ /"summary"/gc) {
}


if($json =~ /(.*?)}/gc) {
	my $summary = $1;
	
	my @parts = split(',', $summary);

	my %obj;
	
	for my $p (@parts) {
		my @subparts = split('":', $p);
	
		
		my $key= $subparts[0];
		$key =~ s/[^\w]//g;
		
	
		my $val = $subparts[1];
		$val =~ s/\"//g;
		
		$obj{$key} = $val;
		#print "$key: $val\n";
	}
	#print join("\n", @parts);

	print $obj{"departureTime"} . ";" . $obj{"lengthInMeters"} . ";" . $obj{"travelTimeInSeconds"} . ";" . $obj{"trafficDelayInSeconds"} . "\n";
}


