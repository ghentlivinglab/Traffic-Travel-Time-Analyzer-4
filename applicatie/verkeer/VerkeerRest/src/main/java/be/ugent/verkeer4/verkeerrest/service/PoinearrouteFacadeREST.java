/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerrest.service;

import be.ugent.verkeer4.verkeerrest.Poinearroute;
import be.ugent.verkeer4.verkeerrest.PoinearroutePK;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author Niels
 */
@Stateless
@Path("be.ugent.verkeer4.verkeerrest.poinearroute")
public class PoinearrouteFacadeREST extends AbstractFacade<Poinearroute> {

    @PersistenceContext(unitName = "be.ugent.verkeer4_VerkeerRest_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    private PoinearroutePK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;pOIId=pOIIdValue;routeId=routeIdValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        be.ugent.verkeer4.verkeerrest.PoinearroutePK key = new be.ugent.verkeer4.verkeerrest.PoinearroutePK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> pOIId = map.get("pOIId");
        if (pOIId != null && !pOIId.isEmpty()) {
            key.setPOIId(new java.lang.Integer(pOIId.get(0)));
        }
        java.util.List<String> routeId = map.get("routeId");
        if (routeId != null && !routeId.isEmpty()) {
            key.setRouteId(new java.lang.Integer(routeId.get(0)));
        }
        return key;
    }

    public PoinearrouteFacadeREST() {
        super(Poinearroute.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Poinearroute entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, Poinearroute entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        be.ugent.verkeer4.verkeerrest.PoinearroutePK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Poinearroute find(@PathParam("id") PathSegment id) {
        be.ugent.verkeer4.verkeerrest.PoinearroutePK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Poinearroute> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Poinearroute> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
