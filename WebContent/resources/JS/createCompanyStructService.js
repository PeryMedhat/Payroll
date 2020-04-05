(function ($) {
    'use strict';
    /*==================================================================
        [ Daterangepicker ]*/
    try {
        $('.js-datepicker').daterangepicker({
            "singleDatePicker": true,
            "showDropdowns": true,
            "autoUpdateInput": false,
            locale: {
                format: 'DD/MM/YYYY'
            },
        });
    
        var myCalendar = $('.js-datepicker');
        var isClick = 0;
    
        $(window).on('click',function(){
            isClick = 0;
        });
    
        $(myCalendar).on('apply.daterangepicker',function(ev, picker){
            isClick = 0;
            $(this).val(picker.startDate.format('DD/MM/YYYY'));
    
        });
    
        $('.js-btn-calendar').on('click',function(e){
            e.stopPropagation();
    
            if(isClick === 1) isClick = 0;
            else if(isClick === 0) isClick = 1;
    
            if (isClick === 1) {
                myCalendar.focus();
            }
        });
    
        $(myCalendar).on('click',function(e){
            e.stopPropagation();
            isClick = 1;
        });
    
        $('.daterangepicker').on('click',function(e){
            e.stopPropagation();
        });
    
    
    } catch(er) {console.log(er);}
    /*[ Select 2 Config ]
        ===========================================================*/
    
    try {
        var selectSimple = $('.js-select-simple');
    
        selectSimple.each(function () {
            var that = $(this);
            var selectBox = that.find('select');
            var selectDropdown = that.find('.select-dropdown');
            selectBox.select2({
                dropdownParent: selectDropdown
            });
        });
    
    } catch (err) {
        console.log(err);
    }
    

})(jQuery);
var x = 0;
var empObjectsArray = Array();
var newChildInSameLevel = false;

	"use strict";

function yesnoCheck() {
    if (document.getElementById('yesCheck').checked) {
		document.getElementById('buttonSubmit').innerHTML = 'Next';
	
    } 
	if (document.getElementById('noCheck').checked) {
		document.getElementById('buttonSubmit').innerHTML = 'Submit';
	
	

	}
}

function NextOrSubmit(){
	// in case of the next condition

	if (document.getElementById('yesCheck').checked) {    	
		var code =document.getElementById("companystruct_code");
		var codeValue = code.value;

		var name =document.getElementById("companystruct_name");
		var nameValue = name.value;


		var startDate =document.getElementById("start_date");
		var startDateValue = startDate.value;


		var endDate =document.getElementById("end_date");
		var endDateValue = endDate.value;
		
		if(empObjectsArray.length===0){
			addEmpObject(false,"",true,codeValue,nameValue,startDateValue,endDateValue);
		}
		else{
			var lastParent = empObjectsArray[empObjectsArray.length - 1]
			var lastParentValue = lastParent.code;
			addEmpObject(true,lastParentValue	,true,codeValue,nameValue,startDateValue,endDateValue);
		}
		
		
	}
 // in case of submission condition "we reached a child"
	if (document.getElementById('noCheck').checked) {
		var lastParent;
		var lastParentValue
		var code =document.getElementById("companystruct_code");
		var codeValue = code.value;

		var name =document.getElementById("companystruct_name");
		var nameValue = name.value;


		var startDate =document.getElementById("start_date");
		var startDateValue = startDate.value;


		var endDate =document.getElementById("end_date");
		var endDateValue = endDate.value;


		if(newChildInSameLevel){

         for(var i=empObjectsArray.length-1 ;i<empObjectsArray.length;i-- ){
             if(empObjectsArray[i].hasChild===true){
				 lastParent =empObjectsArray[i];
				 lastParentValue = lastParent.code;
				 break;
				 
			 }

		 }

		}else {

			 lastParent = empObjectsArray[empObjectsArray.length - 1]
			 lastParentValue = lastParent.code;
	
		}
		

		addEmpObject(true,lastParentValue,false,codeValue,nameValue,startDateValue,endDateValue);

		document.getElementById('buttonSubmit').setAttribute("data-toggle","modal");
		document.getElementById('buttonSubmit').setAttribute("data-target","#exampleModalCenter");
	
		
		  console.log(empObjectsArray);
	}
}

function addEmpObject(hasParent,parentCode,hasChild,code ,name ,startDate,endDate){
var code =code;
var name = name;
var startDate = startDate;
var endDate = endDate;
	var empObject = {
		"code" : code,
		"name" :name,
		"startDate" :startDate,
		"endDate":endDate,
		"hasParent":hasParent,
		"parentCode":parentCode,
		"hasChild":hasChild	
		
}
empObjectsArray[x] = empObject;
	x++;
document.getElementById("companystruct_code").value="";
document.getElementById("companystruct_name").value="";
document.getElementById("start_date").value="";
document.getElementById("end_date").value="";
console.log(empObjectsArray);
}

function sendToDB(){


	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var x = 0;
	 empObjectsArray = Array();
	 localStorage.removeItem('items');
	 
		}
	  };

	  xhttp.open("POST", "http://localhost:8080/Payroll/companyStructure/addCompanyStructure", true);
	  xhttp.setRequestHeader("Content-type", "application/json");

	  xhttp.send(JSON.stringify(empObjectsArray));
}


function resetForm(){
	document.getElementById("companystruct_code").value="";
	document.getElementById("companystruct_name").value="";
	document.getElementById("start_date").value="";
	document.getElementById("end_date").value="";
 newChildInSameLevel = true;
}