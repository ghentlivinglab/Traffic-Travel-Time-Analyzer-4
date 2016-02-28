package be.ugent.verkeer4.verkeerdomain;

import be.ugent.verkeer4.verkeerdomain.data.BoundingBox;
import be.ugent.verkeer4.verkeerdomain.data.POI;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import be.ugent.verkeer4.verkeerdomain.provider.BeMobileProvider;
import be.ugent.verkeer4.verkeerdomain.provider.CoyoteProvider;
import be.ugent.verkeer4.verkeerdomain.provider.HereMapsProvider;
import be.ugent.verkeer4.verkeerdomain.provider.IPOIProvider;
import be.ugent.verkeer4.verkeerdomain.provider.IProvider;
import be.ugent.verkeer4.verkeerdomain.provider.ISummaryProvider;
import be.ugent.verkeer4.verkeerdomain.provider.TomTomProvider;
import be.ugent.verkeer4.verkeerdomain.provider.ViaMichelinProvider;
import be.ugent.verkeer4.verkeerdomain.provider.WazeProvider;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProviderService extends BaseService implements IProviderService {

    private final List<IProvider> perRouteProviders;
    private final List<ISummaryProvider> summaryProviders;
    private final IRouteService routeService;
    private final List<IPOIProvider> poiProviders;
    private final IPOIService poiService;

    public ProviderService(IRouteService routeService, IPOIService poiService) throws ClassNotFoundException {
        super();
        this.routeService = routeService;
        this.poiService = poiService;

        TomTomProvider tomtomProvider = new TomTomProvider();
        BeMobileProvider beMobileProvider = new BeMobileProvider();
        HereMapsProvider hereMapsProvider = new HereMapsProvider();

        this.perRouteProviders = new ArrayList<>();
        perRouteProviders.add(tomtomProvider);
        perRouteProviders.add(beMobileProvider);;
        perRouteProviders.add(hereMapsProvider);
        perRouteProviders.add(new WazeProvider());
        perRouteProviders.add(new ViaMichelinProvider());

        this.summaryProviders = new ArrayList<>();
        summaryProviders.add(new CoyoteProvider(routeService));

        this.poiProviders = new ArrayList<>();
        this.poiProviders.add(tomtomProvider);
        this.poiProviders.add(hereMapsProvider);
        this.poiProviders.add(beMobileProvider);
    }

    private synchronized void saveRouteData(RouteData data) {
        Logger.getLogger(ProviderService.class.getName()).log(Level.INFO, "Saving new route data for route " + data.getRouteId() + " and provider " + data.getProvider());
        repo.getRouteDataSet().insert(data);
    }

    @Override
    public void poll() throws ClassNotFoundException {

        ExecutorService pool = Executors.newFixedThreadPool(6);

        List<Future> futures = new ArrayList<>();
        futures.add(pool.submit(() -> {
            for (ISummaryProvider provider : summaryProviders) {
                Logger.getLogger(ProviderService.class.getName()).log(Level.INFO, "Polling for summary on provider " + provider.getClass().getName());
                List<RouteData> lst = provider.poll();
                Logger.getLogger(ProviderService.class.getName()).log(Level.INFO, "Polling for summary on provider " + provider.getClass().getName() + " COMPLETE");
                if (lst != null) {
                    for (RouteData rd : lst) {
                        if (rd != null) {
                            saveRouteData(rd);
                        }
                    }
                } else {
                    Logger.getLogger(ProviderService.class.getName()).log(Level.WARNING, "Could not fetch summary for provider " + provider.getClass().getName());
                }
            }
        }));

        List<Route> routes = routeService.getRoutes();
        for (Route route : routes) {
            Route r = route; // CLOSURE!
            long curTime = new Date().getTime();
            for (IProvider prov : perRouteProviders) {
                IProvider provider = prov; // CLOSURE
                futures.add(pool.submit(() -> {

                    Logger.getLogger(ProviderService.class.getName()).log(Level.INFO, "Polling for route " + r.getId() + " on provider " + provider.getClass().getName());
                    RouteData data = provider.poll(r);
                    Logger.getLogger(ProviderService.class.getName()).log(Level.INFO, "Polling for route " + r.getId() + " on provider " + provider.getClass().getName() + " COMPLETE");
                    if (data != null) {
                        saveRouteData(data);
                    } else {
                        Logger.getLogger(ProviderService.class.getName()).log(Level.WARNING, "Could not fetch route for provider " + provider.getClass().getName() + " for route " + route.getId() + " - " + r.getName());
                    }
                }));
            }

            // block until everything is finished
            for (Future future : futures) {
                try {
                    future.get(60, TimeUnit.SECONDS);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ProviderService.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExecutionException ex) {
                    Logger.getLogger(ProviderService.class.getName()).log(Level.SEVERE, null, ex);
                } catch (TimeoutException ex) {
                    Logger.getLogger(ProviderService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            futures.clear();

            long diff = new Date().getTime() - curTime;
            if (diff > 0 && diff < 7000) { // sleep resterende van de 7 seconden
                try {
                    Thread.sleep(diff);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ProviderService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public List<RouteData> getRouteDataForRoute(int routeId, Date from, Date to) {
        return repo.getRouteDataSet().getItemsForRoute(routeId, from, to);
    }

    @Override
    public void pollPOI(BoundingBox bbox) throws ClassNotFoundException {
        ExecutorService pool = Executors.newFixedThreadPool(6);

        List<Future> futures = new ArrayList<>();

        for (IPOIProvider prov : poiProviders) {
            futures.add(pool.submit(() -> {

                // vraag bestaande active POI's op van de provider
                Map<String, POI> existingPOIsByReferenceId = poiService.getActivePOIPerReferenceIdForProvider(prov.getProvider());
                List<POI> pois = prov.pollPOI(bbox);
                if (pois != null) {
                    for (POI poi : pois) {
                        if (existingPOIsByReferenceId.containsKey(poi.getReferenceId())) {
                            // poi bestaat al, update waarden?

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

        for (Future future : futures) {
            try {
                future.get(60, TimeUnit.SECONDS);
            } catch (InterruptedException ex) {
                Logger.getLogger(ProviderService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(ProviderService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TimeoutException ex) {
                Logger.getLogger(ProviderService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        futures.clear();
    }
}
