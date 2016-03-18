#!/bin/bash

fromLat=50.9801997
fromLng=3.6680424
toLat=51.03842967
toLng=3.72649311
avoidHighways=1
echo "Tomtom:"
perl tomtom.pl $fromLat $fromLng $toLat $toLng $avoidHighways

echo "Here:"
perl here.pl $fromLat $fromLng $toLat $toLng $avoidHighways

echo "Waze:"
perl waze.pl $fromLat $fromLng $toLat $toLng $avoidHighways

echo "Be-mobile:"
perl bemobile.pl $fromLat $fromLng $toLat $toLng $avoidHighways

echo "Via Michelin"
perl viamichelin.pl $fromLat $fromLng $toLat $toLng $avoidHighways

#echo "Coyote:"
#perl coyote.pl
