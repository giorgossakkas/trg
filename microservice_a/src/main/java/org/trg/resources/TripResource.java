package org.trg.resources;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.trg.dataobjects.Car;
import org.trg.dataobjects.Trip;
import org.trg.interfaces.CarDao;
import org.trg.interfaces.TripDao;

import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;

/**
 * @author Giorgos Sakkas
 * This class implements the trips REST endpoints.
 *
 */

@Path("/trips")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TripResource extends Resource{

	@Inject
	TripDao tripDao;
	
	@Inject
	CarDao carDao;

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") Long id){
    	Trip trip = tripDao.get(id);
        if (trip == null) 
        {
            throw new WebApplicationException(404);
        }
        return Response.status(Response.Status.OK).entity(trip).build();
    }

    @GET
    public Response list(@QueryParam("sort") List<String> sortQuery,
            @QueryParam("page") @DefaultValue("0") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize) {

    	 Page page = Page.of(pageIndex, pageSize);
         Sort sort = getSortFromQuery(sortQuery);
         List<Trip> trips = tripDao.list(page, sort);
         

         return Response.status(Response.Status.OK).entity(trips).build();
    }

    @Transactional
    @POST
    public Response add(String tripStr) {
    	    
    	Trip trip = new Trip();
		
    	trip = copyValues(trip, new JSONObject(tripStr));
    	validate(trip);
		trip = tripDao.add(trip);
    	
		JSONObject response = new JSONObject();
		response.put("id", trip.getId());
		
		return Response.status(Response.Status.OK).entity(response.toString()).build();
    }
    
    private Trip copyValues(Trip trip,JSONObject tripJSON) {
    	
    	if (tripJSON.has("from_lat"))
    	{
    		trip.setFromLat(tripJSON.getBigDecimal("from_lat"));
    	}
    	if (tripJSON.has("to_lat"))
    	{
    		trip.setToLat(tripJSON.getBigDecimal("to_lat"));
    	}
    	if (tripJSON.has("from_lon"))
    	{
    		trip.setFromLon(tripJSON.getBigDecimal("from_lon"));
    	}
    	if (tripJSON.has("to_lon"))
    	{
    		trip.setToLon(tripJSON.getBigDecimal("to_lon"));
    	}
    	
    	if (tripJSON.has("car_id"))
    	{
        	Car car = carDao.get(tripJSON.getLong("car_id"));
            if (car == null) 
            {
                throw new WebApplicationException(404);
            }
    		trip.setCar(car);
    	}
    	
    	return trip;
    }

    @Transactional
    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") Long id, String tripStr) {
        
    	Trip trip = tripDao.get(id);
        if (trip == null) 
        {
            throw new WebApplicationException(404);
        }
    	
        trip = copyValues(trip, new JSONObject(tripStr));
    	validate(trip);
        trip = tripDao.update(id, trip);
        
        JSONObject response = new JSONObject();
		response.put("id", trip.getId());
        
		return Response.status(Response.Status.OK).entity(response.toString()).build();
    }

    @Transactional
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
    	
    	if (!carDao.delete(id)) 
    	{
            throw new WebApplicationException(404);
        }
    }
	
}
