############################
# Bemobilepoi.pl scraped  alle poi gegevens binnen de opgegeven bounding box
############################
use strict;
use JSON;
use utf8; 
binmode(STDOUT, ":utf8");

if(scalar @ARGV < 4) {
	print "Usage: bemobilepoi min_lat min_lng max_lat max_lng\n";
	exit(1);
}

# Argumenten
# geo coordinaten voor bounding box
my $fromLat = @ARGV[0]; #"51.05633";
my $fromLng = @ARGV[1]; #"3.69485";
my $toLat = @ARGV[2]; #"51.038768";
my $toLng = @ARGV[3]; #"3.736953";

# vraag poi gegevens op van de interne api
my $exec = 'curl --insecure -s -o - "http://touring.api.be-mobile.be/service/TrafficEvents2/All/nl"';

my $json = `$exec`;

# parse json naar object model
my $response = from_json($json);


my @items = @{ $response->{"LI"} };

# mapping van types naar de juiste enum waarden
my %categories = ( "0" => "0", 
				   "1" => "3", # traffic jam
				   "2" => "8", # accident
				   "3" => "2", #incident
				   "4" => "1", # construction
				   "5" => "6"); # police trap 

# print header				   
print "id;lat;lng;type;traffictype;comments";
print "\n";
	
for my $item (@items) {

	my $lat = $item->{"PL"}->{"LC"}->{"LA"};
	my $lng = $item->{"PL"}->{"LC"}->{"LO"};
	
	# controleer of de lat,lng binnen de bounding box ligt, er is geen mogelijkheid om aan de call de bbox op te geven
	if($lat >= $fromLat && $lat <= $toLat &&
	   $lng >= $fromLng && $lng <= $toLng) {
	 
		# print id
		print $item->{"I"};
		print ";";
		
		# print lat & lng van de poi
		print $lat;
		print ";";
		print $lng;
		print ";";
		
		# print de categorie enum als het gemapped kan worden, anders 0 (unknown)
		if(!$categories{$item->{"C"}}) {
			print "0";
		}
		else {
			print $categories{$item->{"C"}};
		}
		print ";";
		
		# er is geen major/minor onderscheiding
		print "";
		print ";";
		
		# print de comments
		print $item->{"FT"}->{"TM"};
		print "\n";
	}
}
