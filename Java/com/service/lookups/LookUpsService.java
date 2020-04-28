package com.service.lookups;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.lookups.InputValueLookupDAO;
import com.dao.lookups.IntervalLookupDAO;
import com.dao.lookups.TypeLookupDAO;
import com.entities.lookups.InputValue;
import com.entities.lookups.Interval;
import com.entities.lookups.Type;

@Service
public class LookUpsService {
	@Autowired
	private InputValueLookupDAO inputValDAO;
	
	@Autowired
	private IntervalLookupDAO intervalDAO;
	
	@Autowired
	private TypeLookupDAO typeDAO;
	
	@Transactional
	public List<InputValue> getAllInputValues() {
		return inputValDAO.getListOfInputValues();
	}
	@Transactional
	public List<Interval> getAllIntervals() {
		return intervalDAO.getListOfIntervals();
	}
	@Transactional
	public List<Type> getAllTypes() {
		return typeDAO.getListOfTypes();
	}
	

}
