package com.controllers.payType;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.models.payType.PayTypeModel;
import com.service.payType.PayTypeService;


@RestController
@RequestMapping("/payType")
public class PayTypeRestController {

	@Autowired
	PayTypeService payTypeService;
	
	@RequestMapping(value = { "/addPayType" }, method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void addPayType(@RequestBody PayTypeModel payTypeModel) throws Exception {	
		payTypeService.addPayType(payTypeModel);
	}
	
	
}
