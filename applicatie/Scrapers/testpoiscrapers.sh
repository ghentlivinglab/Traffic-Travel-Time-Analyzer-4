#!/bin/bash

minLat="50.98022"
minLng="3.62719"
maxLat="51.19782"
maxLng="3.83324"


echo "TomTom:"
perl tomtompoi.pl $minLat $minLng $maxLat $maxLng

echo "Here:"
perl herepoi.pl $minLat $minLng $maxLat $maxLng

echo "Be-mobile:"
perl bemobilepoi.pl $minLat $minLng $maxLat $maxLng
