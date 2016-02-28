declare namespace L {
    class Google {
        constructor(type: string);
    }
}


namespace MapManagement {

    interface MapData {
        routes: MapRoute[];
        pois: MapPOI[];
    }
    interface MapPOI {
        id:number;
        info:string;
        latitude:number;
        longitude:number;
    }
    
    interface MapRoute {
        id: number;
        name: string;
        distance: number;
        trafficDelayPercentage: number;
        waypoints: MapWaypoint[];
    }
    interface MapWaypoint {
        latitude: number;
        longitude: number;
    }

    class LeafletMapRoute {
        constructor(public layer: L.Path, public route: MapRoute, public points: L.LatLng[]) { }
    }

   class LeafletMapPOI {
       constructor(public layer: L.Circle, public poi: MapPOI, public point: L.LatLng) { }
   }
   
    class MapManager {

        private leafletMapRouteById = {};
        private leafletMapPOIById = {};
        
        private map: L.Map;

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

                let color = MapManager.getColorFromTrafficDelayPercentage(r.trafficDelayPercentage);

                let path = L.polyline(latLngs, { color: color, });

                this.initializePathPopup(path, r);

                this.map.addLayer(path, false);
                llmr = new LeafletMapRoute(path, r, latLngs);
                this.leafletMapRouteById[r.id] = llmr;
            }
            else {
                // already exists, update layer
                llmr = this.leafletMapRouteById[r.id];
                llmr.layer.setStyle({ fillColor: MapManager.getColorFromTrafficDelayPercentage(r.trafficDelayPercentage) });
                llmr.layer.redraw();
            }
        }
        
        protected showPOI(p: MapPOI) {
            let llmp: LeafletMapPOI;
            if (!this.leafletMapPOIById[p.id]) {

                let latLng = new L.LatLng(p.latitude,p.longitude);

                let color = "blue";

                let circle = L.circle(latLng, 30, { color: color, });

                this.initializePOIPopup(circle, p);
              
                this.map.addLayer(circle, false);
                llmp = new LeafletMapPOI(circle, p, latLng);
                this.leafletMapRouteById[p.id] = llmp;
            }
            else {
                // already exists, update layer
                llmp = this.leafletMapRouteById[p.id];
                llmp.layer.redraw();
            }
        }
       
        private initializePOIPopup(circle: L.Circle, poi: MapPOI) {
            circle.bindPopup(`${poi.info}`, {});
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
            path.bindPopup(`${route.name} (${route.distance}m)`, {});
        }

        private static getColorFromTrafficDelayPercentage(delayPercentage: number): string {
            if (delayPercentage >= 0 && delayPercentage < 0.10)
                return "green";
            else if (delayPercentage >= 0.1 && delayPercentage < 0.30)
                return "yellow";
            else if (delayPercentage >= 0.3 && delayPercentage < 0.60)
                return "orange";
            else if (delayPercentage >= 0.6 && delayPercentage < 0.90)
                return "red";
            else if (delayPercentage >= 0.9)
                return "brown";

            return "green";
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
            // great Open source (TM): https://github.com/Leaflet/Leaflet/issues/2662
            let element = <HTMLElement>(<any>path)._path;
            if (visible)
                $(element).show();
            else
                $(element).hide();

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
                    this.showPOIs(data);
                    this.centerMap();
                },
            });
        }

        private showRoutes(data: MapData) {
            for (let r of data.routes) {
                this.showRoute(r);
            }
        }
        
        private showPOIs(data:MapData) {
            for(let p of data.pois)
                this.showPOI(p);
        }
    }

    export function intializeRouteMap(elementId: string, ajaxUrl: string): MapManager {
        let mgr = new RemoteRouteManager(elementId, ajaxUrl);
        mgr.updateRoutes();
        return mgr;
    }
}