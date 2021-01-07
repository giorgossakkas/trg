package org.trg.repositories;

import javax.enterprise.context.ApplicationScoped;
import org.trg.dataobjects.Driver;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class DriverRepository implements PanacheRepository<Driver> {

}
