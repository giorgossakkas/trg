package org.trg.interfaces;

import org.trg.dataobjects.Driver;
import org.trg.repositories.DriverRepository;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;

public interface DriverDao extends PanacheRepositoryResource<DriverRepository, Driver, Long> {

}
