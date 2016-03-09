declare namespace L {
    class Google {
        constructor(type: string);
    }
}

declare var MAIN_ROOT: string;

namespace MapManagement {

    interface MapData {
        routes: MapRoute[];
        pois: MapPOI[];
    }
    interface MapPOI {
        id: number;
        info: string;
        latitude: number;
        longitude: number;
        category: POICategoryEnum;
        source:string;
        since:string;
    }

    enum POICategoryEnum {
        Unknown = 0,
        Construction = 1,
        Incident = 2,
        TrafficJam = 3,
        LaneClosed = 4,
        RoadClosed = 5,
        PoliceTrap = 6,
        Hazard = 7,
        Accident = 8
    }

    interface MapRoute {
        id: number;
        name: string;
        distance: number;
        averageCurrentTravelTime: number;
        currentDelay: number;
        trafficDelayPercentage: number;
        waypoints: MapWaypoint[];
    }
    interface MapWaypoint {
        latitude: number;
        longitude: number;
    }

    class LeafletMapRoute {
        constructor(public layer: L.Path, public layer2: L.Path, public route: MapRoute, public points: L.LatLng[]) { }
    }

    class LeafletMapPOI {
        constructor(public marker: L.Marker, public poi: MapPOI, public point: L.LatLng) { }
    }

    class MapManager {

        private leafletMapRouteById = {};
        private leafletMapPOIById = {};

        private map: L.Map;

        private markerCluster: L.MarkerClusterGroup = null;

        constructor(private mapElementId: string) {
            this.initialize();
        }

        private initialize() {
            this.map = L.map(this.mapElementId);
            this.map.setView([51.1072199, 3.7676438], 11);

            var googleLayer = new L.Google('ROADMAP');
            this.map.addLayer(<L.ILayer>googleLayer, true);


        }

        protected showRoute(r: MapRoute) {
            let llmr: LeafletMapRoute;
            if (!this.leafletMapRouteById[r.id]) {

                let latLngs = MapManager.convertWaypointsToLatLng(r.waypoints);

                let color = MapManager.getColor(r.currentDelay, false);
                let colordark = MapManager.getColor(r.currentDelay, true);
                let path = L.polyline(latLngs, { stroke: true, weight: 5, color: color, opacity: 1, });
                let path2 = L.polyline(latLngs, { stroke: true, weight: 3, color: colordark, opacity: 1, className: "animated-polyline" });

                this.initializePathPopup(path, r);
                this.initializePathPopup(path2, r);

                this.map.addLayer(path, false);
                this.map.addLayer(path2, false);

                llmr = new LeafletMapRoute(path, path2, r, latLngs);
                this.leafletMapRouteById[r.id] = llmr;
            }
            else {
                // already exists, update layer
                llmr = this.leafletMapRouteById[r.id];
                llmr.layer.setStyle({ fillColor: MapManager.getColor(r.currentDelay, false) });
                llmr.layer2.setStyle({ fillColor: MapManager.getColor(r.currentDelay, true) });
                llmr.layer.redraw();
                llmr.layer2.redraw();
            }
        }

        protected showPOIs(pois: MapPOI[]) {

            this.markerCluster = (<any>L).markerClusterGroup({ disableClusteringAtZoom: 13});

            for (let p of pois)
                this.showPOI(p);

            this.map.addLayer(this.markerCluster);

        }

        protected showPOI(p: MapPOI) {
            let llmp: LeafletMapPOI;
            if (!this.leafletMapPOIById[p.id]) {

                let latLng = new L.LatLng(p.latitude, p.longitude);

                let color: string;
                let iconUrl: string = MAIN_ROOT + "/static/images/poi_";
                switch (p.category) {
                    case POICategoryEnum.Incident:
                        color = "blue";
                        iconUrl += "incident.png";
                        break;
                    case POICategoryEnum.Construction:
                        color = "yellow";
                        iconUrl += "construction.png";
                        break;
                    case POICategoryEnum.TrafficJam:
                        color = "red";
                        iconUrl += "jam.png";
                        break;
                    case POICategoryEnum.LaneClosed:
                        color = "red";
                        iconUrl += "laneclosed.png";
                        break;
                    case POICategoryEnum.RoadClosed:
                        color = "red";
                        iconUrl += "roadclosed.png";
                        break;
                    case POICategoryEnum.PoliceTrap:
                        color = "red";
                        iconUrl += "police.png";
                        break;
                    case POICategoryEnum.Hazard:
                        color = "red";
                        iconUrl += "hazard.png";
                        break;
                    case POICategoryEnum.Accident:
                        color = "red";
                        iconUrl += "accident.png";
                        break;                        
                    case POICategoryEnum.Unknown:
                        color = "black";
                        iconUrl += "unknown.png";
                        break;
                }

                let icon = L.icon({ iconSize: new L.Point(24, 24), iconUrl: iconUrl })
                let marker = L.marker(latLng, { icon: icon, clickable: true });

                this.markerCluster.addLayer(marker);

                this.initializePOIPopup(marker, p);

                llmp = new LeafletMapPOI(marker, p, latLng);
                this.leafletMapRouteById[p.id] = llmp;
            }
            else {
                // already exists, update layer
                llmp = this.leafletMapRouteById[p.id];
                llmp.marker.update();
            }
        }

        private initializePOIPopup(marker: L.Marker, poi: MapPOI) {
            marker.bindPopup(`
                ${poi.info}
                <br/>
                Since: ${poi.since}
                <br/>
                Provided by: ${poi.source}
            `, {});
        }

        centerMap(): void {
            let allPoints: L.LatLng[] = [];
            for (let id in this.leafletMapRouteById) {
                for (let p of (<LeafletMapRoute>this.leafletMapRouteById[id]).points) {
                    allPoints.push(p);
                }
            }
            let bounds = new L.LatLngBounds(allPoints);
            this.map.fitBounds(bounds, {
                animate: false,
                zoom: { animate: false },
                pan: { animate: false }
            });
        }

        centerMapOnRoute(id: number) {
            let llmr = (<LeafletMapRoute>this.leafletMapRouteById[id]);
            if (llmr) {
                let bounds = new L.LatLngBounds(llmr.points);
                this.map.fitBounds(bounds, {});
            }
        }


        private initializePathPopup(path: L.Path, route: MapRoute) {
            path.bindPopup(`
                ${route.name} (${route.distance}m)
                <br>
                  <span class="label label-info time" data-time="${route.averageCurrentTravelTime}">
                      ${route.averageCurrentTravelTime}
                  </span>                  
                  <span class="label time label-delay" data-time="${route.currentDelay}">
                    ${route.currentDelay}
                  </span>
                  <div class="pull-right">
                        <a href='${MAIN_ROOT}/route/detail/${route.id}'>Detail</a>
                  </div>
            `, {});
            path.on("popupopen", () => {
                // todo beter afhandelen
                (<any>window).labelDelays();
                (<any>window).formatTimes();
            });
        }

        private static getColor(delay: number, dark: boolean): string {
            let level: number = (<any>window).getDelayLevel(delay);
            // spijtig genoeg zijn paths met svg en kunnen er geen css klassen gebruikt worden
            if (level == 0)
                return dark ? "#306e30" : "#5cb85c";
            else if (level == 1)
                return dark ? "#df8a13" : "#f0ad4e";
            else if (level == 2)
                return dark ? "#b52b27" : "#d9534f";

            return "#5cb85c";
        }

        private static convertWaypointsToLatLng(waypoints: MapWaypoint[]): L.LatLng[] {
            let latlngs: L.LatLng[] = [];
            for (let wp of waypoints) {
                latlngs.push(L.latLng(wp.latitude, wp.longitude));
            }
            return latlngs;
        }

        setRouteVisibility(id: number, visible: boolean) {
            let path = (<LeafletMapRoute>this.leafletMapRouteById[id]).layer;
            let path2 = (<LeafletMapRoute>this.leafletMapRouteById[id]).layer2;
            // great Open source (TM): https://github.com/Leaflet/Leaflet/issues/2662
            let element = <HTMLElement>(<any>path)._path;
            let element2 = <HTMLElement>(<any>path2)._path;
            if (visible) {
                $(element).show();
                $(element2).show();
            }
            else {
                $(element).hide();
                $(element2).hide();
            }

        }
    }

    class RemoteRouteManager extends MapManager {

        constructor(mapElementId: string, private mapRouteUrl: string) {
            super(mapElementId);
        }

        updateRoutes() {
            $.ajax(this.mapRouteUrl, {
                method: "GET",
                dataType: "json",
                success: (data: MapData) => {
                    this.showRoutes(data);
                    this.showPOIs(data.pois);
                    this.centerMap();
                },
            });
        }

        private showRoutes(data: MapData) {
            for (let r of data.routes) {
                this.showRoute(r);
            }
        }
    }

    export function intializeRouteMap(elementId: string, ajaxUrl: string): MapManager {
        let mgr = new RemoteRouteManager(elementId, ajaxUrl);
        mgr.updateRoutes();
        return mgr;
    }
}