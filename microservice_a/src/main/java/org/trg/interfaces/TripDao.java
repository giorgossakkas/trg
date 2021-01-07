package org.trg.interfaces;

import org.trg.dataobjects.Trip;
import org.trg.repositories.TripRepository;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;

public interface TripDao extends PanacheRepositoryResource<TripRepository, Trip, Long> {

}
