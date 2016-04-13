############################
# Coyotepoi.pl scraped  alle poi gegevens binnen de opgegeven bounding box
############################

use utf8; 
binmode(STDOUT, ":utf8");
use strict;
use JSON;

# coyote gebruikt een user & ww
my $username = "110971610";
my $password = "50c20b94";

# mapping van de categorieen op de enum waarden
my %categories = ( "24" => "3",  #incident trafic -> jam
				   "25" => "8",  # accident -> accident
				   "26" => "7",  # incident (stilstaand voertuig) -> hazard
				   "27" => "7",  # obstacle -> hazard
				   "28" => "0",  # danger -> unknown
				   "35" => "3",  # bouchon utilisateur -> jam
				   "127" => "3",  # bouchon automatique -> jam
				   "128" => "0",  # conditions dangereuses -> unknown
				   "122" => "4",  # retrissement -> lane closed
				   "125" => "5",  # fermeture de route -> road closed
				   "132" => "2", # beschadigd wegdek -> incident
				   "133" => "2", # visibilite reduite -> unknown
				   "139" => "2" # route glissante -> unknown
);

# mapping van de codes naar omschrijvingen
my %descriptions = ( "24" => "Traffic incident",  #incident trafic -> jam
				   "25" => "Accident",  # accident -> incident
				   "26" => "Stopped vehicle",  # incident (stilstaand voertuig) -> hazard
				   "27" => "Obstacle",  # obstacle -> hazard
				   "28" => "Danger",  # danger -> unknown
				   "35" => "Queueing traffic",  # bouchon utilisateur -> jam
				   "127" => "Standstill traffic",  # bouchon automatique -> jam
				   "128" => "Dangerous conditions",  # conditions dangereuses -> unknown
				   "122" => "Lane closed",  # retrissement -> lane closed
				   "125" => "Road cloased",  # fermeture de route -> road closed
				   "132" => "Road damaged", # beschadigd wegdek -> unknown
				   "133" => "Visibility reduced", # visibilite reduite -> unknown
				   "139" => "Icy road" # route glissante -> unknown
);


# login op coyote zodat er een PHPSESSID gegenereerd wordt
my $redir = "/dev/null";
if($^O eq 'MSWin32') {
	$redir = "NUL";
}
my $execHeader = "curl --insecure -s -D- \"https://maps.coyotesystems.com/traffic/index.php\" -H \"Origin: https://maps.coyotesystems.com\" -H \"Accept-Encoding: gzip, deflate\" -H \"Accept-Language: en-US,en;q=0.8\" -H \"User-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2453.0 Safari/537.36\" -H \"HTTPS: 1\" -H \"Content-Type: application/x-www-form-urlencoded\" -H \"Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\" -H \"Cache-Control: max-age=0\" -H \"Referer: https://maps.coyotesystems.com/traffic/login.php\" -H \"Connection: keep-alive\" --data \"login=$username&password=$password\" -L --location-trusted --max-redirs 10 -o $redir";

# vraag headers op van de response
my $headers = `$execHeader`;


# Set-Cookie: PHPSESSID=1nsrjm6n9tacpv2us4394ba552; path=/

# als er een PHPSESSID is 
if($headers =~ /.*PHPSESSID=(.*);/g) {
	
	my $session = $1;
	
	# vraag alle gegevens op
	my $time = time;
	my $exec = "curl --insecure -s -o - \"https://maps.coyotesystems.com/traffic/ajax/get_perturbation_list.ajax.php?_=$time\" -H \"Cookie: PHPSESSID=$session; _ga=GA1.2.1517436056.1456162908\" -H \"Accept-Language: en-US,en;q=0.8\" -H \"User-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2453.0 Safari/537.36\" -H \"Accept: application/json, text/javascript, */*; q=0.01\" -H \"Referer: https://maps.coyotesystems.com/traffic/index.php\" -H \"X-Requested-With: XMLHttpRequest\" -H \"Connection: keep-alive\" -L --location-trusted  --max-redirs 10";
	
	my $json =  `$exec`;	
	
	# parse json response naar objecten
	my $response =  from_json($json);	

	#print de header
	print "id;lat;lng;type;traffictype;comments";
	print "\n";
	
	my @items = keys %{ $response->{"alerts"} };
	
	for my $key (@items) {
		my $item =  $response->{"alerts"}->{$key};
		
		# print id van de poi
		print $item->{"id"};
		print ";";
		
		# print lat & lng
		print $item->{"lat"};
		print ";";
		print $item->{"lng"};
		print ";";
		
		# print gemapped type of 0 (unknown) als het niet gemapped kan worden
		if(!exists $categories{$item->{"type_id"}}) {
			print "0";
		}
		else {
			print $categories{$item->{"type_id"}};
		}
		print ";";
		
		# er is geen major/minor onderscheiding
		print "";
		print ";";
		
		# print de comments
		print $descriptions{$item->{"type_id"}};
		print "\n";
	}
}
