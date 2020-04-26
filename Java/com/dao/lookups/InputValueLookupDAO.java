package com.dao.lookups;

import java.util.List;

import com.entities.lookups.InputValue;

public interface InputValueLookupDAO {
	public List<InputValue> getListOfIntervals();
	public InputValue getIntervalByName(String name);
	public InputValue getIntervalByCode(String code);	
}
