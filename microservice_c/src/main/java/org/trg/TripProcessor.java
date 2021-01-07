package org.trg;

import java.math.BigDecimal;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.bson.Document;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;
import org.json.JSONObject;

import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.runtime.util.ExceptionUtil;
import io.smallrye.reactive.messaging.annotations.Blocking;

/**
 * @author Giorgos Sakkas
 * This class implements the consumer of the event from the microservice b.
 *
 */

@ApplicationScoped
public class TripProcessor {

	private static final Logger logger = Logger.getLogger(TripProcessor.class.getName());
	
    @Inject ReactiveMongoClient reactiveMongoClient;
	
	@Incoming("process-trip-in")
	@Blocking
	public void processTrip(String input) throws Exception {
		try
		{		
			logger.info("Consuming Incoming topic: process-trip-in with message:"+input);	
			JSONObject json=new JSONObject(input);
			
			long driverId = json.getLong("driver_id");
			BigDecimal speed = json.getBigDecimal("speed");
			Integer penaltyPoints = Integer.valueOf(0);
			
			if (speed.compareTo(BigDecimal.valueOf(80)) > 0)
			{
				Integer diff = speed.subtract(BigDecimal.valueOf(80)).intValue();
				penaltyPoints+=diff * 5;
			}
			
			if (speed.compareTo(BigDecimal.valueOf(60)) > 0)
			{
				Integer diff = speed.subtract(BigDecimal.valueOf(60)).intValue();
				if (diff > 20)
				{
					diff = 20;
				}
				penaltyPoints+=diff * 2;
			}
			
			logger.info("Driver: "+driverId + " received "+penaltyPoints+" penalty points");
			
			json.put("penalty_points", penaltyPoints);
			
			reactiveMongoClient.getDatabase("trg").getCollection("penaltypoints").insertOne(Document.parse(json.toString())).await().indefinitely();

			logger.info("Succesfuly Consumed Incoming topic: process-trip-in with message:"+input);
		}
		catch(Exception e)
		{
			logger.error("Error Incoming topic: process-trip-in with message:"+input+" received error message:"+ ExceptionUtil.generateStackTrace(e));
		}
	}
}
