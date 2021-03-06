package org.trg.resources;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.trg.dataobjects.Car;
import org.trg.dataobjects.Driver;
import org.trg.enums.CarType;
import org.trg.repositories.CarRepository;
import org.trg.repositories.DriverRepository;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

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
	CarRepository carRepository;
	
	@Inject
	DriverRepository driverRepository;

	
    /**
     * This method implements the functionality of retrieving the information of a car based on an id
     * @param id - The id of the car that will be retrieved
     * @throws WebApplicationException
     * @return a json object representation of the car
     */
    @GET
    @Path("{id}")
    @Produces("application/json")
    @Counted(name = "readCar", description = "How many times the read car api is called.")
    @Timed(name = "readCarTimer", description = "A measure of how long it takes to get a car.", unit = MetricUnits.MILLISECONDS)
    public Response get(@PathParam("id") Long id){
    	Car car = carRepository.findById(id);
        if (car == null) 
        {
            throw new WebApplicationException(404);
        }
        return Response.status(Response.Status.OK).entity(car).build();
    }

    /**
     * This method implements the functionality of retrieving a list of all the cars
     * @return a json array of the cars 
     *
     */
    @GET
    @Produces("application/json")
    @Counted(name = "listCar", description = "How many times the list cars api is called.")
    @Timed(name = "listCarTimer", description = "A measure of how long it takes to list cars.", unit = MetricUnits.MILLISECONDS)
    public Response list() {

         List<Car> cars = carRepository.listAll();
         
         return Response.status(Response.Status.OK).entity(cars).build();
    }

    /**
     * This method implements the functionality of creating a car
     * @param carStr - The json object contains the information of the car that will be created
     * @return the id of the created car 
     *
     */
    @Transactional
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Counted(name = "addCar", description = "How many times the add a car api is called.")
    @Timed(name = "addCarTimer", description = "A measure of how long it takes to add a car.", unit = MetricUnits.MILLISECONDS)
    public Response add(String carStr) {
    	
    	Car car = new Car();
    	
    	car = copyValues(car, new JSONObject(carStr));
    	validate(car);
    	carRepository.persist(car);
    	
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
        	Driver driver = driverRepository.findById(carJSON.getLong("driver_id"));
            if (driver == null) 
            {
                throw new WebApplicationException(404);
            }
    		car.setDriver(driver);
    	}
    	
    	return car;
    }
    
    /**
     * This method implements the functionality of updating a car
     * @param id - The id of the car that will be updated
     * @param carStr - The json object contains the information of the car that will be updated
     * @return the id of the updated car
     *
     */
    @Transactional
    @PUT
    @Path("{id}")
    @Consumes("application/json")
    @Produces("application/json")
    @Counted(name = "updateCar", description = "How many times the update a car api is called.")
    @Timed(name = "updateCarTimer", description = "A measure of how long it takes to update a car.", unit = MetricUnits.MILLISECONDS)
    public Response update(@PathParam("id") Long id, String carStr) {
        
    	Car car = carRepository.findById(id);
        if (car == null) 
        {
            throw new WebApplicationException(404);
        }
        
        car = copyValues(car, new JSONObject(carStr));
    	validate(car);
        carRepository.persist(car);
        
        JSONObject response = new JSONObject();
 		response.put("id", car.getId());
        
    	return Response.status(Response.Status.OK).entity(response.toString()).build();
    }

    /**
     * This method implements the functionality of deleting a car
     * @param id - The id of the car that will be deleted
     *
     */
    @Transactional
    @DELETE
    @Path("{id}")
    @Counted(name = "deleteCar", description = "How many times the delete a car api is called.")
    @Timed(name = "deleteCarTimer", description = "A measure of how long it takes to delete a car.", unit = MetricUnits.MILLISECONDS)
    public void delete(@PathParam("id") Long id) {
    	
    	Car car = carRepository.findById(id);
        if (car == null) 
        {
            throw new WebApplicationException(404);
        }
    	
        carRepository.delete(car);
    }
    
    /**
     * This method implements the functionality of assigning a car to a driver
     * @param car_id - The id of the car that will be assign to a driver
     * @param driver_id - The id of the driver that will assign to the car
     *
     */
    @Transactional
    @PUT
    @Path("{car_id}/assign-to-driver/{driver_id}")
    @Consumes("application/json")
    @Produces("application/json")
    @Counted(name = "assignCarToDriver", description = "How many times the assign a car to a driver api is called.")
    @Timed(name = "assignCarToDriverTimer", description = "A measure of how long it takes to assign a car to a driver a car.", unit = MetricUnits.MILLISECONDS)
    public Response assignCarToDriver(@PathParam("car_id") Long car_id, @PathParam("driver_id") Long driver_id) {
        
    	Car car = carRepository.findById(car_id);
        if (car == null) 
        {
            throw new WebApplicationException(404);
        }
        
        Driver driver = driverRepository.findById(driver_id);
        if (driver == null) 
        {
            throw new WebApplicationException(404);
        }
        
        car.setDriver(driver);
        carRepository.persist(car);
        
        JSONObject response = new JSONObject();
 		response.put("id", car.getId());
        
    	return Response.status(Response.Status.OK).entity(response.toString()).build();
    }
	
}
