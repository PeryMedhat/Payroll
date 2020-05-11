package com.controllers.payType;



import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@RequestMapping(value = { "/getPayType" }, method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public PayTypeModel getPayType(@RequestParam("code") String code) {
		return payTypeService.getPayType(code);	
	}
	
	@RequestMapping(value = { "/updatePayType" }, method = RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void updatePayType(@RequestBody PayTypeModel payTypeModel) throws ParseException {
		payTypeService.updatePayTypeData(payTypeModel);
	}
	
	@RequestMapping(value = { "/deletePayType" }, method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void deleteEmployeeStructure(@RequestParam("code") String code) {
		payTypeService.deletePayType(code);
	}
	
	@RequestMapping(value = { "/delemitPayType" }, method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void delemitPayType(@RequestParam("code") String code,@RequestParam("endDate") String endDate) throws ParseException {
		payTypeService.delimitPayType(code, endDate);
	}
	
	
	
}
