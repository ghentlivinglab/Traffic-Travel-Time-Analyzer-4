package be.ugent.verkeer4.verkeerdomain;

import be.ugent.verkeer4.verkeerdomain.data.LogTypeEnum;
import be.ugent.verkeer4.verkeerdomain.data.composite.BoundingBox;
import be.ugent.verkeer4.verkeerdomain.data.POI;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import be.ugent.verkeer4.verkeerdomain.provider.BeMobileProvider;
import be.ugent.verkeer4.verkeerdomain.provider.BingMapsProvider;
import be.ugent.verkeer4.verkeerdomain.provider.CoyoteProvider;
import be.ugent.verkeer4.verkeerdomain.provider.GoogleProvider;
import be.ugent.verkeer4.verkeerdomain.provider.HereMapsProvider;
import be.ugent.verkeer4.verkeerdomain.provider.IPOIProvider;
import be.ugent.verkeer4.verkeerdomain.provider.IProvider;
import be.ugent.verkeer4.verkeerdomain.provider.ISummaryProvider;
import be.ugent.verkeer4.verkeerdomain.provider.TomTomProvider;
import be.ugent.verkeer4.verkeerdomain.provider.ViaMichelinProvider;
import be.ugent.verkeer4.verkeerdomain.provider.WazeProvider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ProviderService extends BaseService implements IProviderService {

    // alle providers waarvoor de verkeergegevens route per route opgevraagd moeten worden
    private final List<IProvider> perRouteProviders;
    // alle providers waarvoor de verkeergegevens van alle routes in 1x opgevraagd kunnen worden
    private final List<ISummaryProvider> summaryProviders;
    // alle providers waar POI gegevens kunnen bij opgevraagd worden
    private final List<IPOIProvider> poiProviders;
    
    private final IRouteService routeService;
    private final IPOIService poiService;
    
    public ProviderService(IRouteService routeService, IPOIService poiService) throws ClassNotFoundException {
        super();
        this.routeService = routeService;
        this.poiService = poiService;

        TomTomProvider tomtomProvider = new TomTomProvider();
        BeMobileProvider beMobileProvider = new BeMobileProvider();
        HereMapsProvider hereMapsProvider = new HereMapsProvider();
        WazeProvider wazeProvider = new WazeProvider();
        CoyoteProvider coyoteProvider = new CoyoteProvider(routeService);

        this.perRouteProviders = new ArrayList<>();
        perRouteProviders.add(tomtomProvider);
        perRouteProviders.add(beMobileProvider);
        perRouteProviders.add(hereMapsProvider);
        perRouteProviders.add(new GoogleProvider());
        perRouteProviders.add(wazeProvider);
        perRouteProviders.add(new ViaMichelinProvider());
        perRouteProviders.add(new BingMapsProvider());

        this.summaryProviders = new ArrayList<>();
        summaryProviders.add(coyoteProvider);

        this.poiProviders = new ArrayList<>();
        this.poiProviders.add(tomtomProvider);
        this.poiProviders.add(hereMapsProvider);
        this.poiProviders.add(beMobileProvider);
        this.poiProviders.add(wazeProvider);
        this.poiProviders.add(coyoteProvider);
        
    }

    /**
     * Slaat de gegeven route data op, in een thread safe manier
     * @param data 
     */
    private synchronized void saveRouteData(RouteData data) {
        LogService.getInstance().insert(LogTypeEnum.Info, ProviderService.class.getName(), "Saving new route data for route " + data.getRouteId() + " and provider " + data.getProvider()); 
        repo.getRouteDataSet().insert(data);
    }

    /**
     * Polled voor nieuwe vertraging gegevens voor alle routes
     * Elke route wordt in parallel gepolled
     * @throws ClassNotFoundException 
     */
    @Override
    public void poll() throws ClassNotFoundException {

        ExecutorService pool = Executors.newFixedThreadPool(10);

        List<Future> futures = new ArrayList<>();
        // schedule een fetch van alle summary gegevens
        futures.add(pool.submit(() -> {
            for (ISummaryProvider provider : summaryProviders) {
                LogService.getInstance().insert(LogTypeEnum.Info, ProviderService.class.getName(), "Polling for summary on provider " + provider.getClass().getName());
                List<RouteData> lst = provider.poll();
                LogService.getInstance().insert(LogTypeEnum.Info, ProviderService.class.getName(), "Polling for summary on provider " + provider.getClass().getName() + " COMPLETE");
                if (lst != null) {
                    for (RouteData rd : lst) {
                        if (rd != null) {
                            saveRouteData(rd);
                        }
                    }
                } else {
                    LogService.getInstance().insert(LogTypeEnum.Error, ProviderService.class.getName(), "Could not fetch summary for provider " + provider.getClass().getName()); 
                }
            }
        }));

        // voor alle routes
        List<Route> routes = routeService.getRoutes();
        for (Route route : routes) {
            Route r = route; // CLOSURE!
            long curTime = new Date().getTime();
            
            // schedule per provider een poll voor de route
            for (IProvider prov : perRouteProviders) {
                IProvider provider = prov; // CLOSURE
                futures.add(pool.submit(() -> {
                    LogService.getInstance().insert(LogTypeEnum.Info, ProviderService.class.getName(), "Polling for route " + r.getId() + " on provider " + provider.getClass().getName());
                    RouteData data = provider.poll(r);
                    LogService.getInstance().insert(LogTypeEnum.Info, ProviderService.class.getName(), "Polling for route " + r.getId() + " on provider " + provider.getClass().getName() + " COMPLETE");
                    if (data != null) {
                        saveRouteData(data);
                    } else {
                        LogService.getInstance().insert(LogTypeEnum.Warning, ProviderService.class.getName(), "Could not fetch route for provider " + provider.getClass().getName() + " for route " + route.getId() + " - " + r.getName());
                    }
                }));
            }

            // wacht tot alle gegevens zijn opgevraagd zodat er geen 2
            // requests tegelijk naar dezelfde provider kan verstuurd worden
            for (Future future : futures) {
                try {
                    future.get(60, TimeUnit.SECONDS);
                } catch (InterruptedException | ExecutionException | TimeoutException ex) {
                    LogService.getInstance().insert(LogTypeEnum.Warning, ProviderService.class.getName(), ex.getMessage());
                }
            }
            futures.clear();

            // wacht de resterende tijd van de 5sec
            long diff = new Date().getTime() - curTime;
            if (diff > 0 && diff < 5000) { // sleep resterende van de 5 seconden
                try {
                    LogService.getInstance().insert(LogTypeEnum.Info, ProviderService.class.getName(), "Waiting for " + (5000 - diff) + "ms before continuing");
                    Thread.sleep(5000 - diff);
                } catch (InterruptedException ex) {
                    LogService.getInstance().insert(LogTypeEnum.Warning, ProviderService.class.getName(), ex.getMessage());
                }
            }
        }
        
        pool.shutdownNow();
    }

    /**
     * Geeft de route data terug voor een bepaalde periode voor een route
     * @param routeId
     * @param from
     * @param to
     * @return 
     */
    @Override
    public List<RouteData> getRouteDataForRoute(int routeId, Date from, Date to, String order) {
        return repo.getRouteDataSet().getItemsForRoute(routeId, from, to, order);
    }

    /**
     * Polled alle POI providers om nieuwe POI's te detecteren en op te slaan
     * @param bbox
     * @throws ClassNotFoundException 
     */
    @Override
    public void pollPOI(BoundingBox bbox) throws ClassNotFoundException {
        ExecutorService pool = Executors.newFixedThreadPool(6);

        List<Future> futures = new ArrayList<>();

        // schedule een poll voor alle poll providers
        for (IPOIProvider prov : poiProviders) {
            futures.add(pool.submit(() -> {

                // vraag bestaande active POI's op van de provider
                Map<String, POI> existingPOIsByReferenceId = poiService.getActivePOIPerReferenceIdForProvider(prov.getProvider());
                List<POI> pois = prov.pollPOI(bbox);
                if (pois != null) {
                    for (POI poi : pois) {
                        if (existingPOIsByReferenceId.containsKey(poi.getReferenceId())) {
                            // poi bestaat al, update waarden
                            POI oldPOI = existingPOIsByReferenceId.get(poi.getReferenceId());
                            // id & matched overnemen
                            poi.setId(oldPOI.getId());
                            poi.setMatchedWithRoutes(oldPOI.isMatchedWithRoutes());
                            poiService.update(poi);

                        } else {
                            // nieuwe poi
                            poiService.insert(poi);
                        }
                        existingPOIsByReferenceId.remove(poi.getReferenceId());
                    }

                    // overblijvende poi's komen niet meer voor, sluit ze af door Until in te vullen
                    for (POI poi : existingPOIsByReferenceId.values()) {
                        poi.setUntil(new Date());
                        poiService.update(poi);
                    }
                }
            }));
        }

        // wacht tot alle geschedulede polls gedaan zijn
        for (Future future : futures) {
            try {
                future.get(60, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException ex) {
                LogService.getInstance().insert(LogTypeEnum.Warning, ProviderService.class.getName(), ex.getMessage());
            }
        }
        
        pool.shutdownNow();
    }
}
