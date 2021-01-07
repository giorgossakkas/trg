package org.trg.repositories;

import javax.enterprise.context.ApplicationScoped;
import org.trg.dataobjects.Car;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class CarRepository implements PanacheRepository<Car> {

}
