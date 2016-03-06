var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
var MapManagement;
(function (MapManagement) {
    var LeafletMapRoute = (function () {
        function LeafletMapRoute(layer, route, points) {
            this.layer = layer;
            this.route = route;
            this.points = points;
        }
        return LeafletMapRoute;
    })();
    var MapManager = (function () {
        function MapManager(mapElementId) {
            this.mapElementId = mapElementId;
            this.leafletMapRouteById = {};
            this.initialize();
        }
        MapManager.prototype.initialize = function () {
            this.map = L.map(this.mapElementId);
            this.map.setView([51.1072199, 3.7676438], 11);
            var googleLayer = new L.Google('ROADMAP');
            this.map.addLayer(googleLayer, true);
        };
        MapManager.prototype.showRoute = function (r) {
            var llmr;
            if (!this.leafletMapRouteById[r.id]) {
                var latLngs = MapManager.convertWaypointsToLatLng(r.waypoints);
                var color = MapManager.getColor(r.currentDelay);
                var path = L.polyline(latLngs, { stroke: true, weight: 5, color: color, opacity: 1 });
                this.initializePathPopup(path, r);
                this.map.addLayer(path, false);
                llmr = new LeafletMapRoute(path, r, latLngs);
                this.leafletMapRouteById[r.id] = llmr;
            }
            else {
                // already exists, update layer
                llmr = this.leafletMapRouteById[r.id];
                llmr.layer.setStyle({ fillColor: MapManager.getColor(r.currentDelay) });
                llmr.layer.redraw();
            }
        };
        MapManager.prototype.centerMap = function () {
            var allPoints = [];
            for (var id in this.leafletMapRouteById) {
                for (var _i = 0, _a = this.leafletMapRouteById[id].points; _i < _a.length; _i++) {
                    var p = _a[_i];
                    allPoints.push(p);
                }
            }
            var bounds = new L.LatLngBounds(allPoints);
            this.map.fitBounds(bounds, {
                animate: false,
                zoom: { animate: false },
                pan: { animate: false }
            });
        };
        MapManager.prototype.centerMapOnRoute = function (id) {
            var llmr = this.leafletMapRouteById[id];
            if (llmr) {
                var bounds = new L.LatLngBounds(llmr.points);
                this.map.fitBounds(bounds, {});
            }
        };
        MapManager.prototype.initializePathPopup = function (path, route) {
            path.bindPopup("\n                " + route.name + " (" + route.distance + "m)\n                <br>\n                  <span class=\"label label-info time\" data-time=\"" + route.averageCurrentTravelTime + "\">\n                      " + route.averageCurrentTravelTime + "\n                  </span>                  \n                  <span class=\"label time label-delay\" data-time=\"" + route.currentDelay + "\">\n                    " + route.currentDelay + "\n                  </span>\n                  <div class=\"pull-right\">\n                        <a href='detail/" + route.id + "'>Detail</a>\n                  </div>\n            ", {});
            path.on("popupopen", function () {
                // todo beter afhandelen
                window.labelDelays();
                window.formatTimes();
            });
        };
        MapManager.getColor = function (delay) {
            var level = window.getDelayLevel(delay);
            // spijtig genoeg zijn paths met svg en kunnen er geen css klassen gebruikt worden
            if (level == 0)
                return "#5cb85c";
            else if (level == 1)
                return "#f0ad4e";
            else if (level == 2)
                return "#d9534f";
            return "#5cb85c";
        };
        MapManager.convertWaypointsToLatLng = function (waypoints) {
            var latlngs = [];
            for (var _i = 0; _i < waypoints.length; _i++) {
                var wp = waypoints[_i];
                latlngs.push(L.latLng(wp.latitude, wp.longitude));
            }
            return latlngs;
        };
        MapManager.prototype.setRouteVisibility = function (id, visible) {
            var path = this.leafletMapRouteById[id].layer;
            // great Open source (TM): https://github.com/Leaflet/Leaflet/issues/2662
            var element = path._path;
            if (visible)
                $(element).show();
            else
                $(element).hide();
        };
        return MapManager;
    })();
    var RemoteRouteManager = (function (_super) {
        __extends(RemoteRouteManager, _super);
        function RemoteRouteManager(mapElementId, mapRouteUrl) {
            _super.call(this, mapElementId);
            this.mapRouteUrl = mapRouteUrl;
        }
        RemoteRouteManager.prototype.updateRoutes = function () {
            var _this = this;
            $.ajax(this.mapRouteUrl, {
                method: "GET",
                dataType: "json",
                success: function (data) {
                    _this.showRoutes(data);
                    _this.centerMap();
                }
            });
        };
        RemoteRouteManager.prototype.showRoutes = function (data) {
            for (var _i = 0, _a = data.routes; _i < _a.length; _i++) {
                var r = _a[_i];
                this.showRoute(r);
            }
        };
        return RemoteRouteManager;
    })(MapManager);
    function intializeRouteMap(elementId, ajaxUrl) {
        var mgr = new RemoteRouteManager(elementId, ajaxUrl);
        mgr.updateRoutes();
        return mgr;
    }
    MapManagement.intializeRouteMap = intializeRouteMap;
})(MapManagement || (MapManagement = {}));
