var RouteEdit;
(function (RouteEdit) {
    var GooglePositionSelector = (function () {
        function GooglePositionSelector(elementId, mapId, addressElementId) {
            var _this = this;
            this.elementId = elementId;
            this.mapId = mapId;
            this.addressElementId = addressElementId;
            this.geocoder = new google.maps.Geocoder();
            $(document.getElementById(elementId)).change(function () { return _this.onValueChanged(true, false); });
            this.initializeMap(mapId);
            this.marker = new google.maps.Marker();
            this.onValueChanged(false, true);
        }
        GooglePositionSelector.prototype.initializeMap = function (mapId) {
            this.map = new google.maps.Map(document.getElementById(mapId));
            var self = this;
            google.maps.event.addListener(this.map, "rightclick", function (event) {
                $(document.getElementById(self.elementId)).val(event.latLng.lat() + "," + event.latLng.lng());
                self.onValueChanged(true, false);
            });
        };
        GooglePositionSelector.prototype.onValueChanged = function (geocodeAddress, centerMap) {
            var _this = this;
            var value = $(document.getElementById(this.elementId)).val();
            var parts = value.split(',');
            var lat = parseFloat(parts[0]);
            var lng = parseFloat(parts[1]);
            var latLng = new google.maps.LatLng(lat, lng);
            this.updateMarker(latLng);
            if (geocodeAddress) {
                this.geocoder.geocode({ location: latLng }, function (results, status) {
                    var address = results[0].formatted_address;
                    $(document.getElementById(_this.addressElementId)).val(address);
                });
            }
            if (centerMap) {
                this.map.setZoom(14);
                this.map.setCenter(latLng);
            }
        };
        GooglePositionSelector.prototype.updateMarker = function (latLng) {
            this.marker.setMap(this.map);
            this.marker.setPosition(latLng);
        };
        return GooglePositionSelector;
    })();
    function bindLatLngBox(elementId, mapId, addressElementId) {
        return new GooglePositionSelector(elementId, mapId, addressElementId);
    }
    RouteEdit.bindLatLngBox = bindLatLngBox;
})(RouteEdit || (RouteEdit = {}));
