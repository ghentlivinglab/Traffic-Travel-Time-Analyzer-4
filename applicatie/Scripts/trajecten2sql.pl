use strict;

@ARGV = ("trajecten.txt");
$/="";
my $txt = <>;

#print $txt;


while($txt =~ /.*?google(.*?)\s/gc) {
#	print "match\n";	
	my $url = $1;

	my %obj;
	
	if($url =~ /.*?(\d+\.\d+),(\d+\.\d+)\/(\d+\.\d+),(\d+\.\d+)/gsc) {
#		print "van lat: " . $1 . "\n";
#		print "van lng: " . $2 . "\n";
#		print "tot lat: " . $3 . "\n";
#		print "tot lng: " . $3 . "\n";
		$obj{"fromLatitude"} = $1;
		$obj{"fromLongitude"} = $2;
		$obj{"toLatitude"} = $3;
		$obj{"toLongitude"} = $4;
	}
	if($txt =~ /.*?ROUTE\sNAME\s+(.*?)\s+From\s+(.*?)\s+To\s+(.*?)\s+(ROUTE\sNAME|From)\s+(.*?)From/gsc) {
		
#		print "Naam: " . $1 . "\n";
#		print "Van: " . $2 . "\n";
#		print "Tot: " . $3 . "\n";

		$obj{"name"} = $1;
		$obj{"fromAddress"} = $2;
		$obj{"toAddress"} = $3;
		$obj{"reversename"} = $5;
	}


	print "INSERT INTO Route (Name, Distance, FromAddress, FromLatitude, FromLongitude, ToAddress, ToLatitude, ToLongitude)";
	print " VALUES ";
	print "(";
	print "'" . $obj{name} . "'" . ",";
	print "0" . ",";
	print "'" . $obj{fromAddress} . "'" . ",";
	print $obj{fromLatitude} . ",";
	print $obj{fromLongitude} . ",";
	print "'" . $obj{toAddress} . "'" . ",";
	print $obj{toLatitude} . ",";
	print $obj{toLongitude};
	print ")";
	print ";";
	print "\n";

        print "INSERT INTO Route (Name, Distance, FromAddress, FromLatitude, FromLongitude, ToAddress, ToLatitude, ToLongitude)";
        print " VALUES ";
        print "(";
        print "'" . $obj{reversename} . "'" . ",";
        print "0" . ",";
        print "'" . $obj{toAddress} . "'" . ",";
        print $obj{toLatitude} . ",";
        print $obj{toLongitude} . ",";
        print "'" . $obj{fromAddress} . "'" . ",";
        print $obj{fromLatitude} . ",";
        print $obj{fromLongitude};
        print ")";
	print ";";
        print "\n";

}
