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
import org.trg.dataobjects.Driver;
import org.trg.enums.CarType;
import org.trg.interfaces.CarDao;
import org.trg.interfaces.DriverDao;

import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;

/**
 * @author Giorgos Sakkas
 * This class implements the cars REST endpoints.
 *
 */

@Path("/cars")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CarResource extends Resource{

	@Inject
	CarDao carDao;
	
	@Inject
	DriverDao driverDao;

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response get(@PathParam("id") Long id){
    	Car car = carDao.get(id);
        if (car == null) 
        {
            throw new WebApplicationException(404);
        }
        return Response.status(Response.Status.OK).entity(car).build();
    }

    @GET
    @Produces("application/json")
    public Response list(@QueryParam("sort") List<String> sortQuery,
            @QueryParam("page") @DefaultValue("0") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize) {

    	 Page page = Page.of(pageIndex, pageSize);
         Sort sort = getSortFromQuery(sortQuery);
         List<Car> cars = carDao.list(page, sort);
         
         return Response.status(Response.Status.OK).entity(cars).build();
    }

    @Transactional
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response add(String carStr) {
    	
    	Car car = new Car();
    	
    	car = copyValues(car, new JSONObject(carStr));
    	validate(car);
    	car = carDao.add(car);
    	
        JSONObject response = new JSONObject();
 		response.put("id", car.getId());
        
    	return Response.status(Response.Status.OK).entity(response.toString()).build();
    }
    
    private Car copyValues(Car car,JSONObject carJSON) {
    	
    	if (carJSON.has("brand"))
    	{
    		car.setBrand(carJSON.getString("brand"));
    	}
    	if (carJSON.has("type"))
    	{
    		car.setType(CarType.valueOf(carJSON.getString("type")));
    	}
    	
    	if (carJSON.has("license_plates"))
    	{
    		car.setLicensePlates(carJSON.getString("license_plates"));
    	}
    	
    	if (carJSON.has("driver_id"))
    	{
        	Driver driver = driverDao.get(carJSON.getLong("driver_id"));
            if (car == null) 
            {
                throw new WebApplicationException(404);
            }
    		car.setDriver(driver);
    	}
    	
    	return car;
    }
    
    @Transactional
    @PUT
    @Path("{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response update(@PathParam("id") Long id, String carStr) {
        
    	Car car = carDao.get(id);
        if (car == null) 
        {
            throw new WebApplicationException(404);
        }
        
        car = copyValues(car, new JSONObject(carStr));
    	validate(car);
        car = carDao.update(id, car);
        
        JSONObject response = new JSONObject();
 		response.put("id", car.getId());
        
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
    
    @Transactional
    @PUT
    @Path("{car_id}/assign-to-driver/{driver_id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response assignCarToDriver(@PathParam("car_id") Long car_id, @PathParam("driver_id") Long driver_id) {
        
    	Car car = carDao.get(car_id);
        if (car == null) 
        {
            throw new WebApplicationException(404);
        }
        
        Driver driver = driverDao.get(driver_id);
        if (driver == null) 
        {
            throw new WebApplicationException(404);
        }
        
        car.setDriver(driver);
        car = carDao.update(car_id, car);
        
        JSONObject response = new JSONObject();
 		response.put("id", car.getId());
        
    	return Response.status(Response.Status.OK).entity(response.toString()).build();
    }
	
}
