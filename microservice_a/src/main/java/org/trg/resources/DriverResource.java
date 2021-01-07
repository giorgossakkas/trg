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
import org.trg.dataobjects.Driver;
import org.trg.interfaces.DriverDao;

import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;

/**
 * @author Giorgos Sakkas
 * This class implements the drivers REST endpoints.
 *
 */

@Path("/drivers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DriverResource extends Resource{

	@Inject
	DriverDao driverDao;

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") Long id){
    	Driver driver = driverDao.get(id);
        if (driver == null) 
        {
            throw new WebApplicationException(404);
        }
        return Response.status(Response.Status.OK).entity(driver).build();
    }

    @GET
    public Response list(@QueryParam("sort") List<String> sortQuery,
            @QueryParam("page") @DefaultValue("0") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize) {

	   	 Page page = Page.of(pageIndex, pageSize);
	     Sort sort = getSortFromQuery(sortQuery);
	     List<Driver> drivers = driverDao.list(page, sort);
	     
	     return Response.status(Response.Status.OK).entity(drivers).build();
    }

    @Transactional
    @POST
    public Response add(String driverStr) {
      	
    	Driver driver = new Driver();
    	
    	driver = copyValues(driver, new JSONObject(driverStr));
    	
    	validate(driver);
    	
    	driver = driverDao.add(driver);
    	
    	JSONObject response = new JSONObject();
 		response.put("id", driver.getId());
    	
		return Response.status(Response.Status.OK).entity(response.toString()).build();

    }

    private Driver copyValues(Driver driver,JSONObject driverJSON) {
    	
    	if (driverJSON.has("first_name"))
    	{
    		driver.setFirstName(driverJSON.getString("first_name"));
    	}
    	if (driverJSON.has("last_name"))
    	{
        	driver.setLastName(driverJSON.getString("last_name"));
    	}
    	if (driverJSON.has("id_number"))
    	{
        	driver.setIdnumber(driverJSON.getString("id_number"));
    	}
    	
    	return driver;
    }
    
    @Transactional
    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") Long id, String driverStr) {
        
    	Driver driver = driverDao.get(id);
        if (driver == null) 
        {
            throw new WebApplicationException(404);
        }

    	driver = copyValues(driver, new JSONObject(driverStr));

    	validate(driver);
    	
    	driver = driverDao.update(id, driver);

    	JSONObject response = new JSONObject();
 		response.put("id", driver.getId());
 		
		return Response.status(Response.Status.OK).entity(response.toString()).build();
       
    		
    }

    @Transactional
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {

    	if (!driverDao.delete(id)) 
    	{
            throw new WebApplicationException(404);
        }

    }
	
}
