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

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
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

    /**
     * This method implements the functionality of retrieving the information of a trip based on an id
     * @param id - The id of the trip that will be retrieved
     * @throws WebApplicationException
     * @return a json object representation of the trip
     */
    @GET
    @Path("{id}")
    @Counted(name = "readTrip", description = "How many times the read trip api is called.")
    @Timed(name = "readTripTimer", description = "A measure of how long it takes to read a trip.", unit = MetricUnits.MILLISECONDS)
    public Response get(@PathParam("id") Long id){
    	Trip trip = tripDao.get(id);
        if (trip == null) 
        {
            throw new WebApplicationException(404);
        }
        return Response.status(Response.Status.OK).entity(trip).build();
    }

    /**
     * This method implements the functionality of retrieving a list of trips based on a set of condition
     * @param sort - The trips that will be retrieved are sort with the specified parameter
     * @param page - The trips that will be retrieved belongs to specified page. (Default 1st page)
     * @param size - The number of trips that will retrieved. (Default 20)
     * @return a json array of the trips 
     *
     */
    @GET
    @Counted(name = "listTrips", description = "How many times the list trips api is called.")
    @Timed(name = "listTripsTimer", description = "A measure of how long it takes to list trips.", unit = MetricUnits.MILLISECONDS)
    public Response list(@QueryParam("sort") List<String> sortQuery,
            @QueryParam("page") @DefaultValue("0") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize) {

    	 Page page = Page.of(pageIndex, pageSize);
         Sort sort = getSortFromQuery(sortQuery);
         List<Trip> trips = tripDao.list(page, sort);
         

         return Response.status(Response.Status.OK).entity(trips).build();
    }


    /**
     * This method implements the functionality of creating a trip
     * @param tripStr - The json object contains the information of the trip that will be created
     * @return the id of the created trip 
     *
     */
    @Transactional
    @POST
    @Counted(name = "addTrip", description = "How many times the add trip api is called.")
    @Timed(name = "addTripTimer", description = "A measure of how long it takes to add a trip.", unit = MetricUnits.MILLISECONDS)
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

    /**
     * This method implements the functionality of updating a trip
     * @param id - The id of the trip that will be updated
     * @param tripStr - The json object contains the information of trip car that will be updated
     * @return the id of the updated trip
     *
     */
    @Transactional
    @PUT
    @Path("{id}")
    @Counted(name = "updateTrip", description = "How many times the update trip api is called.")
    @Timed(name = "updateTripTimer", description = "A measure of how long it takes to update a trip.", unit = MetricUnits.MILLISECONDS)
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

    /**
     * This method implements the functionality of deleting a trip
     * @param id - The id of the trip that will be deleted
     *
     */
    @Transactional
    @DELETE
    @Path("{id}")
    @Counted(name = "deleteTrip", description = "How many times the delete trip api is called.")
    @Timed(name = "deleteTripTimer", description = "A measure of how long it takes to delete a trip.", unit = MetricUnits.MILLISECONDS)
    public void delete(@PathParam("id") Long id) {
    	
    	if (!carDao.delete(id)) 
    	{
            throw new WebApplicationException(404);
        }
    }
	
}
