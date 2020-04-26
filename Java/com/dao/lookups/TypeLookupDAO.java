package com.dao.lookups;

import java.util.List;

import com.entities.lookups.Type;

public interface TypeLookupDAO {
	public List<Type> getListOfIntervals();
	public Type getIntervalByName(String name);
	public Type getIntervalByCode(String code);	
	
}
