package org.trg;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ProcessTripKafkaController {

	private static final Logger logger = Logger.getLogger(ProcessTripKafkaController.class.getName());
    @Inject @Channel("process-trip-out") Emitter<String> emitter;

    public void produce(String message) {
		if(emitter!=null)
		{
			logger.info("Adding trip to queue:"+message);
			emitter.send(message);
			logger.info("Trip added to queue:"+message);
		}
    }
}
