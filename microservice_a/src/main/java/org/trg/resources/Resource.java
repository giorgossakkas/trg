package org.trg.resources;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;

import org.trg.dataobjects.Entity;

import io.quarkus.panache.common.Sort;
import io.quarkus.panache.common.Sort.Direction;


/**
 * @author Giorgos Sakkas
 * This class implements the common functionality for all REST endpoints classes.
 *
 */

public class Resource {

	@Inject
	Validator validator;
	
	protected Sort getSortFromQuery(List<String> sortQueryList) {
		Sort sort =null;
		
		if (sortQueryList!=null && sortQueryList.size() > 0)
		{
			for (int i=0;i<sortQueryList.size();i++)
			{
				String sortQuery = sortQueryList.get(i);
				Direction direction = Direction.Ascending;
				String sortField = null;
				String[] tokens = sortQuery.split(" ");
				
				if (tokens.length == 2)
				{
					sortField = tokens[0];
					if (tokens[1].equalsIgnoreCase("desc"))
					{
						direction= Direction.Descending;
					}
				}
				else
				{
					sortField = sortQuery;
				}
				
				if (sort == null)
				{
					sort = Sort.by(sortField,direction);
				}
				else
				{
					sort = sort.and(sortField,direction);
				}
				
			}
		}
		
		return sort;
	}
	
	protected void validate(Entity entity) {
		Set<ConstraintViolation<Entity>> violations = validator.validate(entity);
        if (!violations.isEmpty()) 
        {
        	ArrayList<String> errorMessages = new ArrayList<String>();
        	Iterator<ConstraintViolation<Entity>> iter = violations.iterator();
        	while (iter.hasNext())
        	{
        		ConstraintViolation<Entity> violation = iter.next();
        		errorMessages.add(violation.getMessage());
        	}
        	throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST).entity(errorMessages).build());
        } 
	}
	
}
