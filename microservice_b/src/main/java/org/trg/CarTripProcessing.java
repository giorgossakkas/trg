package org.trg;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.runtime.util.ExceptionUtil;
import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.ScheduledExecution;

/**
 * @author Giorgos Sakkas
 * This class simulates a Car that is driven around a city.
 * Is running periodically, selects a random car from all the cars and adds an event with the trip information.
 *
 */

@ApplicationScoped
public class CarTripProcessing {

	@Inject
	@RestClient MicroserviceARestClient microserviceARestClient;
	
	@Inject ProcessTripKafkaController processTripKafkaController;
	
    private static final Logger logger = Logger.getLogger(CarTripProcessing.class.getName());

	
	@Scheduled(cron = "{cron.expr.process.trip}")
	public void processTrip(ScheduledExecution execution) throws Exception {
    	
	    logger.info("Process a trip: " + execution.getScheduledFireTime());
	    try
	    {
		    ArrayList<CarRepresentation> allCars = microserviceARestClient.listCars();
		    
		    logger.info(allCars.size()+ " cars retrieved");
		    
		    if (allCars.size() > 0)
		    {
			    logger.info("Generate a random number to retrieve a random car");
			    Random random = new Random(); 
				int randNumber = random.nextInt(allCars.size()); 
				
				CarRepresentation car = allCars.get(randNumber);

			    logger.info("Car with id:"+car.getId() +" is retrieved");
			    if (car.getDriver()!=null)
			    {
				    TripInformation tripInformation = new TripInformation();
				    tripInformation.setCarId(car.getId());
				    tripInformation.setDriverId(car.getDriver().getId());
				    tripInformation.setFromLat(BigDecimal.valueOf(random.nextInt(1000)));
				    tripInformation.setFromLon(BigDecimal.valueOf(random.nextInt(1000)));
				    tripInformation.setToLat(BigDecimal.valueOf(random.nextInt(1000)));
				    tripInformation.setToLon(BigDecimal.valueOf(random.nextInt(1000)));
				    tripInformation.setSpeed(BigDecimal.valueOf(random.nextInt(100)));
					
				    processTripKafkaController.produce(new ObjectMapper().writeValueAsString(tripInformation));
			    }
		    }
	
		    logger.info("The trip ends on " + new java.util.Date());
	    }
 	   	catch(Exception e) 
 	   	{
 	   		logger.info("Error on processing a trip "+ ExceptionUtil.generateStackTrace(e));
 	   	}
    }
	
}
