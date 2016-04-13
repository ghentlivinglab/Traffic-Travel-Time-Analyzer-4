############################
# Wazepoi.pl scraped  alle poi gegevens binnen de opgegeven bounding box
############################
use strict;
use JSON;
use utf8; 
binmode(STDOUT, ":utf8");

if(scalar @ARGV < 4) {
	print "Usage: wazepoi min_lat min_lng max_lat max_lng\n";
	exit(1);
}

# Argumenten
# geo coordinaten voor bounding box
my $fromLat = @ARGV[0]; #"51.05633";
my $fromLng = @ARGV[1]; #"3.69485";
my $toLat = @ARGV[2]; #"51.038768";
my $toLng = @ARGV[3]; #"3.736953";

# bouw url op en voer de request uit met curl
my $url = 'https://www.waze.com/row-rtserver/web/TGeoRSS?ma=600&mj=100&mu=100&left=' . $fromLng . '&right=' . $toLng . '&bottom=' . $fromLat . '&top=' . $toLat . '&_=' . time;
my $exec = 'curl --insecure -s -o - "' . $url . '"';

my $json = `$exec`;

# parse de json naar objecten
my $response = from_json($json);


my @items = @{ $response->{"alerts"} };

# mapping van types naar de juiste enum waarden
my %categories = ( "ACCIDENT" => "8", "ROAD_CLOSED" => "5", "JAM" => "3", "POLICE" => "6", "CONSTRUCTION" => "1", "HAZARD" => "7" );

# print header
print "id;lat;lng;type;traffictype;comments";
print "\n";
	
for my $item (@items) {

	my $lat = $item->{"location"}->{"y"};
	my $lng = $item->{"location"}->{"x"};
	
	# print id
	print $item->{"uuid"};
	print ";";
	
	# print lat & lng van de poi
	print $lat;
	print ";";
	print $lng;
	print ";";
	
	# print type als het kan gemapped worden, anders 0 (unknown)
	if(!exists $categories{$item->{"type"}}) {
		print "0";
	}
	else {
		print $categories{$item->{"type"}};
	}
	print ";";
	
	# er is geen major/minor onderscheiding
	print "";
	print ";";
	
	# print de omschrijving als er 1 is, anders het sub type
	if(!$item->{"reportDescription"} || $item->{"reportDescription"} eq "") {
		print $item->{"subtype"};
	}
	else {
		print $item->{"reportDescription"};
	}
	print "\n";
}