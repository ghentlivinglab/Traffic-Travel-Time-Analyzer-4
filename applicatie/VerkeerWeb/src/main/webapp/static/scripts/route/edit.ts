namespace RouteEdit {

    class GooglePositionSelector {

        private geocoder = new google.maps.Geocoder();
        private map: google.maps.Map;

        private marker: google.maps.Marker;

        constructor(private elementId: string, private mapId: string, private addressElementId: string) {
            $(document.getElementById(elementId)).change(() => this.onValueChanged(true, false));
            this.initializeMap(mapId);
            this.marker = new google.maps.Marker();

            this.onValueChanged(false, true);
        }

        private initializeMap(mapId:string) {
            this.map = new google.maps.Map(document.getElementById(mapId));
            let self = this;
            google.maps.event.addListener(this.map, "rightclick", function(event) {
                $(document.getElementById(self.elementId)).val(event.latLng.lat() + "," + event.latLng.lng());
                self.onValueChanged(true, false);
            });
        }

        private onValueChanged(geocodeAddress: boolean, centerMap: boolean) {
            let value = <string>$(document.getElementById(this.elementId)).val();

            let parts = value.split(',');
            let lat = parseFloat(parts[0]);
            let lng = parseFloat(parts[1]);
            let latLng = new google.maps.LatLng(lat, lng);
            this.updateMarker(latLng);

            if (geocodeAddress) {
                this.geocoder.geocode({ location: latLng }, (results, status) => {
                    let address = results[0].formatted_address;
                    $(document.getElementById(this.addressElementId)).val(address);
                });
            }
            if (centerMap) {
                this.map.setZoom(14);
                this.map.setCenter(latLng);
            }
        }

        private updateMarker(latLng: google.maps.LatLng) {
            this.marker.setMap(this.map);
            this.marker.setPosition(latLng);
        }
    }

    export function bindLatLngBox(elementId: string, mapId: string, addressElementId: string) {
        return new GooglePositionSelector(elementId, mapId, addressElementId);
    }
}