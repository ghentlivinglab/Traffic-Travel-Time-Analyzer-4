/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerdomain.provider;

import be.ugent.verkeer4.verkeerdomain.data.ProviderEnum;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import be.ugent.verkeer4.verkeerdomain.provider.google.Element;
import be.ugent.verkeer4.verkeerdomain.provider.google.GoogleMapsClient;
import be.ugent.verkeer4.verkeerdomain.provider.google.Row;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Niels
 */
public class GoogleProvider extends BaseProvider implements IProvider {

    public GoogleProvider() {
        super(ProviderEnum.GoogleMaps);
    }
   
    @Override
    public RouteData poll(Route route) {
        try {
            // haal route gegevens op
            Row row = GoogleMapsClient.GetRoute(route.getFromLatitude(), route.getFromLongitude(), route.getToLatitude(), route.getToLongitude());
          
            if (row != null) {
                Element googleRoute = row.getElements().get(0);

                int delay = googleRoute.getDurationInTraffic().getValue() - googleRoute.getDuration().getValue();
                
                RouteData rd = setRouteData(route,
                        googleRoute.getDistance().getValue(),
                        googleRoute.getDurationInTraffic().getValue(),
                        delay);
                return rd;
            }

            return null;
        } catch (Exception ex) {
             Logger.getLogger(GoogleProvider.class.getName()).log(Level.WARNING, "Scraping failed for route " + route.getId(), ex);
            return null;
        }     
    }   
    
}
