package org.trg.repositories;

import javax.enterprise.context.ApplicationScoped;
import org.trg.dataobjects.Trip;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class TripRepository implements PanacheRepository<Trip> {

}
