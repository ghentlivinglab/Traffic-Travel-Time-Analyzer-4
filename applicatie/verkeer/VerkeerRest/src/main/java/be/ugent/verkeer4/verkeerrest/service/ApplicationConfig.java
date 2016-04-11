/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerrest.service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Niels
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(be.ugent.verkeer4.verkeerrest.service.LoggingFacadeREST.class);
        resources.add(be.ugent.verkeer4.verkeerrest.service.PoiFacadeREST.class);
        resources.add(be.ugent.verkeer4.verkeerrest.service.PoinearrouteFacadeREST.class);
        resources.add(be.ugent.verkeer4.verkeerrest.service.RouteFacadeREST.class);
        resources.add(be.ugent.verkeer4.verkeerrest.service.RoutedataFacadeREST.class);
        resources.add(be.ugent.verkeer4.verkeerrest.service.RoutetrafficjamFacadeREST.class);
        resources.add(be.ugent.verkeer4.verkeerrest.service.RoutetrafficjamcauseFacadeREST.class);
        resources.add(be.ugent.verkeer4.verkeerrest.service.RoutewaypointFacadeREST.class);
        resources.add(be.ugent.verkeer4.verkeerrest.service.WeatherdataFacadeREST.class);
    }
    
}
