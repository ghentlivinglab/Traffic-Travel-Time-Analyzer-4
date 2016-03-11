use strict;
use JSON;
use utf8; 
binmode(STDOUT, ":utf8");

if(scalar @ARGV < 4) {
	print "Usage: wazepoi min_lat min_lng max_lat max_lng\n";
	exit(1);
}

my $fromLat = @ARGV[0]; #"51.05633";
my $fromLng = @ARGV[1]; #"3.69485";
my $toLat = @ARGV[2]; #"51.038768";
my $toLng = @ARGV[3]; #"3.736953";


my $url = 'https://www.waze.com/row-rtserver/web/TGeoRSS?ma=600&mj=100&mu=100&left=' . $fromLng . '&right=' . $toLng . '&bottom=' . $fromLat . '&top=' . $toLat . '&_=' . time;
my $exec = 'curl --insecure -s -o - "' . $url . '"';

#print $url;
my $json = `$exec`;

#print $json;
my $response = from_json($json);


my @items = @{ $response->{"alerts"} };

my @jams = @{ $response->{"alerts"} };

my %categories = ( "ACCIDENT" => "8", "ROAD_CLOSED" => "5", "JAM" => "3", "POLICE" => "6", "CONSTRUCTION" => "1", "HAZARD" => "7" );

print "id;lat;lng;type;traffictype;comments";
print "\n";
	
for my $item (@items) {

	my $lat = $item->{"location"}->{"y"};
	my $lng = $item->{"location"}->{"x"};
	
	print $item->{"uuid"};
	print ";";
	print $lat;
	print ";";
	print $lng;
	print ";";
	if(!exists $categories{$item->{"type"}}) {
		print "0";
	}
	else {
		print $categories{$item->{"type"}};
	}
	print ";";
	print "";
	print ";";
	if(!$item->{"reportDescription"} || $item->{"reportDescription"} eq "") {
		print $item->{"subtype"};
	}
	else {
		print $item->{"reportDescription"};
	}
	print "\n";
}