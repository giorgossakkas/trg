package org.trg.interfaces;

import org.trg.dataobjects.Car;
import org.trg.repositories.CarRepository;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;

public interface CarDao extends PanacheRepositoryResource<CarRepository, Car, Long> {

}
