

use strict;
use JSON;

my $username = "110971610";
my $password = "50c20b94";


my $redir = "/dev/null";
if($^O eq 'MSWin32') {
	$redir = "NUL";
}
my $execHeader = "curl --insecure -s -D- \"https://maps.coyotesystems.com/traffic/index.php\" -H \"Origin: https://maps.coyotesystems.com\" -H \"Accept-Encoding: gzip, deflate\" -H \"Accept-Language: en-US,en;q=0.8\" -H \"User-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2453.0 Safari/537.36\" -H \"HTTPS: 1\" -H \"Content-Type: application/x-www-form-urlencoded\" -H \"Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\" -H \"Cache-Control: max-age=0\" -H \"Referer: https://maps.coyotesystems.com/traffic/login.php\" -H \"Connection: keep-alive\" --data \"login=$username&password=$password\" -L --location-trusted --max-redirs 10 -o $redir";

#print $execHeader;
my $headers = `$execHeader`;


# Set-Cookie: PHPSESSID=1nsrjm6n9tacpv2us4394ba552; path=/

if($headers =~ /.*PHPSESSID=(.*);/g) {
	
	#print "Session found, is $1\n";
	my $session = $1;
	
	my $time = time;

	my $exec = "curl --insecure -s -o - \"https://maps.coyotesystems.com/traffic/ajax/get_perturbation_list.ajax.php?_=$time\" -H \"Cookie: PHPSESSID=$session; _ga=GA1.2.1517436056.1456162908\" -H \"Accept-Language: en-US,en;q=0.8\" -H \"User-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2453.0 Safari/537.36\" -H \"Accept: application/json, text/javascript, */*; q=0.01\" -H \"Referer: https://maps.coyotesystems.com/traffic/index.php\" -H \"X-Requested-With: XMLHttpRequest\" -H \"Connection: keep-alive\" -L --location-trusted  --max-redirs 10";
	
	my $json =  `$exec`;
	
	
	my $response =  from_json($json);	

	print "totalDistanceMeters;totalTimeSeconds;totalDelaySeconds;name\n";
	
	for my $rootElement (keys %{ $response }) {
		if($rootElement ne "alerts") {
			for my $routeName (keys %{ $response->{$rootElement} }) {
			
				my $route = $response->{$rootElement}->{$routeName};

				my $distance = $route->{"length"};
				my $normaltime = $route->{"normal_time"};
				my $realtime = $route->{"real_time"};
				
				my $delay = $realtime - $normaltime;
				if($delay < 0) {
					$delay = 0;
				}
				print $distance . ";" . int($realtime) . ";" . int($delay) . ";" . $routeName . "\n";
			}
		}
	}
}
