#!/bin/bash

fromLat=51.038663
fromLng=3.725996
toLat=51.056146
toLng=3.695193

echo "Tomtom:"
perl tomtom.pl $fromLat $fromLng $toLat $toLng

echo "Here:"
perl here.pl $fromLat $fromLng $toLat $toLng

echo "Waze:"
perl waze.pl $fromLat $fromLng $toLat $toLng

echo "Be-mobile:"
perl bemobile.pl $fromLat $fromLng $toLat $toLng

echo "Coyote:"
perl coyote.pl
