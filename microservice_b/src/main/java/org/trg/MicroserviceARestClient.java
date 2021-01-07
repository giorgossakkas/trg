package org.trg;

import java.util.ArrayList;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 * @author Giorgos Sakkas
 * This class implements a rest client in order to communicate with microservice a 
 *
 */

@ApplicationScoped
@RegisterRestClient
public interface MicroserviceARestClient {

	@GET
	@Path("/cars")
	@Produces("application/json")
	ArrayList<CarRepresentation> listCars();
	
}
