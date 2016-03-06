use strict;
use JSON;
binmode(STDOUT, ":utf8");

if(scalar @ARGV < 4) {
	print "Usage: bemobilepoi min_lat min_lng max_lat max_lng\n";
	exit(1);
}

my $fromLat = @ARGV[0]; #"51.05633";
my $fromLng = @ARGV[1]; #"3.69485";
my $toLat = @ARGV[2]; #"51.038768";
my $toLng = @ARGV[3]; #"3.736953";


my $exec = 'curl --insecure -s -o - "http://touring.api.be-mobile.be/service/TrafficEvents2/All/nl"';


my $json = `$exec`;
my $response = from_json($json);


my @items = @{ $response->{"LI"} };

my %categories = ( 0 => "0", 1 => "trafficjam", 2 => "2", 3 => "incident", 4 => "construction", 5 => "5" );

print "id;lat;lng;type;traffictype;comments";
print "\n";
	
for my $item (@items) {

	my $lat = $item->{"PL"}->{"LC"}->{"LA"};
	my $lng = $item->{"PL"}->{"LC"}->{"LO"};
	
	if($lat >= $fromLat && $lat <= $toLat &&
	   $lng >= $fromLng && $lng <= $toLng) {
	 
			print $item->{"I"};
			print ";";
			print $lat;
			print ";";
			print $lng;
			print ";";
			if($categories{$item->{"C"}} eq "trafficjam") {
				print "3"; # traffic jam
			}
			elsif($categories{$item->{"C"}} eq "incident") {
				print "2"; # incident
			}
			elsif($categories{$item->{"C"}} eq "construction") {
				print "1"; #construction
			}
			else {
				print "0"; # unknown
			}
			print ";";
			print "";
			print ";";
			print $item->{"FT"}->{"TM"};
			print "\n";
	}
}