package be.ugent.verkeer4.verkeerdomain;

import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import be.ugent.verkeer4.verkeerdomain.provider.BeMobileProvider;
import be.ugent.verkeer4.verkeerdomain.provider.CoyoteProvider;
import be.ugent.verkeer4.verkeerdomain.provider.HereMapsProvider;
import be.ugent.verkeer4.verkeerdomain.provider.IProvider;
import be.ugent.verkeer4.verkeerdomain.provider.ISummaryProvider;
import be.ugent.verkeer4.verkeerdomain.provider.TomTomProvider;
import be.ugent.verkeer4.verkeerdomain.provider.WazeProvider;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    public ProviderService(IRouteService routeService) throws ClassNotFoundException {
        super();
        this.routeService = routeService;
        this.perRouteProviders = new ArrayList<>();
        perRouteProviders.add(new TomTomProvider());
        perRouteProviders.add(new BeMobileProvider());
        perRouteProviders.add(new HereMapsProvider());
        perRouteProviders.add(new WazeProvider());

        this.summaryProviders = new ArrayList<>();
        summaryProviders.add(new CoyoteProvider(routeService));
    }

    private synchronized void saveRouteData(RouteData data) {
        Logger.getLogger(ProviderService.class.getName()).log(Level.INFO, "Saving new route data for route " + data.getRouteId() + " and provider " + data.getProvider());
        repo.getRouteDataSet().insert(data);
    }

    @Override
    public void poll() throws ClassNotFoundException {

        ExecutorService pool = Executors.newFixedThreadPool(10);
        ExecutorCompletionService ecs = new ExecutorCompletionService(pool);

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

        for (Route route : routeService.getRoutes()) {
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
                    future.get(30, TimeUnit.SECONDS);
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
}
