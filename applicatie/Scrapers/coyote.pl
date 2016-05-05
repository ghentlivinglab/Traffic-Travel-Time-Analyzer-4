############################
# Coyote.pl scraped de coyote website waar alle routes worden opgelijst.
# De naam van de route zal later gematched worden met de naam in de database
############################

use strict;

# Maak gebruik van de XS::JSON library
use JSON;

# Coyote heeft een username & wachtwoord nodig om in te loggen
my $username = "190097429";
my $password = "1e5fd22f";

# redirect output naar /dev/null of NUL naargelang linux of windows
my $redir = "/dev/null";
if($^O eq 'MSWin32') {
	$redir = "NUL";
}

# Logt in op coyote zodat er een PHPSESSID cookie wordt teruggegeven
# De curl statement komt van Chrome -> Developer Tools -> Copy url as curl command line
# Zorg ook dat er redirects gefollowed worden met --max-redirs
my $execHeader = "curl --insecure -s -D- \"https://maps.coyotesystems.com/traffic/index.php\" -H \"Origin: https://maps.coyotesystems.com\" -H \"Accept-Encoding: gzip, deflate\" -H \"Accept-Language: en-US,en;q=0.8\" -H \"User-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2453.0 Safari/537.36\" -H \"HTTPS: 1\" -H \"Content-Type: application/x-www-form-urlencoded\" -H \"Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\" -H \"Cache-Control: max-age=0\" -H \"Referer: https://maps.coyotesystems.com/traffic/login.php\" -H \"Connection: keep-alive\" --data \"login=$username&password=$password\" -L --location-trusted --max-redirs 10 -o $redir";

# De curl statement geeft enkel de headers terug aangezien we enkel geinteresseerd zijn in de Set-Cookie, vb  Set-Cookie: PHPSESSID=1nsrjm6n9tacpv2us4394ba552; path=/
my $headers = `$execHeader`;

# Parse de PHPSESSID uit de headers met regex
if($headers =~ /.*PHPSESSID=(.*);/g) {
	
	# de session id is de 1e capture groep
	my $session = $1;
	# de time wordt gebruikt zodat de gegevens niet gecached worden (de ?_=... query parameter)
	my $time = time;
	# vraag de json gegevens op op dezelfde manier als op de website
	my $exec = "curl --insecure -s -o - \"https://maps.coyotesystems.com/traffic/ajax/get_perturbation_list.ajax.php?_=$time\" -H \"Cookie: PHPSESSID=$session; _ga=GA1.2.1517436056.1456162908\" -H \"Accept-Language: en-US,en;q=0.8\" -H \"User-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2453.0 Safari/537.36\" -H \"Accept: application/json, text/javascript, */*; q=0.01\" -H \"Referer: https://maps.coyotesystems.com/traffic/index.php\" -H \"X-Requested-With: XMLHttpRequest\" -H \"Connection: keep-alive\" -L --location-trusted  --max-redirs 10";
	
	# het resultaat is een json
	my $json =  `$exec`;
	
	# parse de json naar een object tree met XS:Json
	my $response =  from_json($json);	

	# print de header in ; formaat
	print "totalDistanceMeters;totalTimeSeconds;totalDelaySeconds;name\n";
	
	# overloop het resultaat, Coyote heeft nog nooit van arrays gehoord en gebruikt overal aparte objecten voor
	for my $rootElement (keys %{ $response }) {
		if($rootElement ne "alerts") {
			for my $routeName (keys %{ $response->{$rootElement} }) {
			
				my $route = $response->{$rootElement}->{$routeName};

				my $distance = $route->{"length"};
				my $normaltime = $route->{"normal_time"};
				my $realtime = $route->{"real_time"};
				
				# bepaal vertraging door het verschil te nemen van de real time met de normale tijd
				my $delay = $realtime - $normaltime;
				if($delay < 0) {
					$delay = 0;
				}
				# print de verzamelde gegevens in een ; formaat
				print $distance . ";" . int($realtime) . ";" . int($delay) . ";" . $routeName . "\n";
			}
		}
	}
}