#!/bin/bash

while true; do
    read -p "---Choose an option---
    1) Install features (excludes perl)
    2) Restart services (mysql/glassfish)
    3) Deploy war (located in /verkeer/VerkeerWeb.war)
    4) Exit" answer
    case $answer in
        [1]* ) sudo timedatectl set-timezone Europe/Brussels; sudo add-apt-repository ppa:openjdk-r/ppa; sudo apt-get update; sudo apt-get install openjdk-8-jdk -y; sudo apt-get install mariadb-server-y; sudo apt-get install unzip gcc make libjson-perl -y; wget http://download.oracle.com/glassfish/4.1/release/glassfish-4.1.zip; unzip glassfish-4.1.1.zip -d /opt;;
        [2]* ) sudo service mysql restart; sudo /opt/glassfish4/bin/asadmin restart-domain domain1;;
        [3]* ) sudo /opt/glassfish4/bin/asadmin restart-domain domain1; /opt/glassfish4/bin/asadmin deploy -s --contextroot / /verkeer/VerkeerWeb.war;;
        [4]* ) break;;
        * ) printf "\nInvalid operation!\n";;
    esac
done

