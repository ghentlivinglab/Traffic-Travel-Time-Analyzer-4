#!/bin/bash

while true; do
    read -p "---Choose an option---
    1) Set timezone to Europe/Brussels
    2) Install features (excludes perl)
    3) Configure glassfish credentials
    4) Restart (mysql/glassfish)
    5) Deploy war located as /verkeer/VerkeerWeb.war
    6) Undeploy VerkeerWeb.war
    7) Test scrapers
    8) Exit" answer
    case $answer in
        [1]* ) sudo timedatectl set-timezone Europe/Brussels; printf "\nTimezone changed to Europe/Brussels\n";; 
        [2]* ) sudo add-apt-repository ppa:openjdk-r/ppa; sudo apt-get update; sudo apt-get install openjdk-8-jdk -y; sudo apt-get install mariadb-server -y; sudo apt-get install unzip gcc make libjson-perl -y; wget http://download.oracle.com/glassfish/4.1/release/glassfish-4.1.zip; sudo unzip glassfish-4.1.zip -d /opt;;
        [3]* ) sudo /opt/glassfish4/bin/asadmin change-admin-password ; sudo /opt/glassfish4/bin/asadmin enable-secure-admin ; sudo /opt/glassfish4/bin/asadmin start-domain;;
        [4]* ) sudo service mysql restart; sudo /opt/glassfish4/bin/asadmin restart-domain;;
        [5]* ) read -p "Enter contextroot (example: /verkeer/verkeer4 (/ is default)):" contextroot ;
                contextroot=`if [[ -d $contextroot ]] ; then echo $contextroot ; else echo "/" ; fi`;
               sudo /opt/glassfish4/bin/asadmin deploy -s --contextroot $contextroot /verkeer/VerkeerWeb.war;;
        [6]* ) sudo /opt/glassfish4/bin/asadmin undeploy VerkeerWeb;;
        [7]* ) sudo bash /scrapers/testscrapers.sh;;
        [8]* ) break;;
        * ) printf "\nInvalid operation!\n";;
    esac
done

