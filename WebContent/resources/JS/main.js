(function($) {
	

	var fullHeight = function() {

		$('.js-fullheight').css('height', $(window).height());
		$(window).resize(function(){
			$('.js-fullheight').css('height', $(window).height());
		});

	};
	fullHeight();

	$('#sidebarCollapse').on('click', function () {
      $('#sidebar').toggleClass('active');
  });


})(jQuery);
var x = 0;
var empObjectsArray = Array();

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
		var code =document.getElementById("empstruct_code");
		var codeValue = code.value;

		var name =document.getElementById("empstruct_name");
		var nameValue = name.value;


		var startDate =document.getElementById("start_date");
		var startDateValue = startDate.value;


		var endDate =document.getElementById("end_date");
		var endDateValue = endDate.value;
		const data = JSON.parse(localStorage.getItem('items'))
		if(data === null){
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
		var code =document.getElementById("empstruct_code");
		var codeValue = code.value;

		var name =document.getElementById("empstruct_name");
		var nameValue = name.value;


		var startDate =document.getElementById("start_date");
		var startDateValue = startDate.value;


		var endDate =document.getElementById("end_date");
		var endDateValue = endDate.value;
		var lastParent = empObjectsArray[empObjectsArray.length - 1]
		var lastParentValue = lastParent.code;
		addEmpObject(true,lastParentValue,false,codeValue,nameValue,startDateValue,endDateValue);
		//send to XHR
		console.log(empObjectsArray);
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				var x = 0;
		 empObjectsArray = Array();
		 localStorage.removeItem('items');
		 
			}
		  };

		  xhttp.open("POST", "http://localhost:8080/Payroll/employeeStructure/addEmployeeStructure", true);
		  xhttp.setRequestHeader("Content-type", "application/json");

		  xhttp.send(JSON.stringify(empObjectsArray));
		
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
localStorage.setItem('items', JSON.stringify(empObjectsArray))
	x++;
document.getElementById("empstruct_code").value="";
document.getElementById("empstruct_name").value="";
document.getElementById("start_date").value="";
document.getElementById("end_date").value="";
console.log(empObjectsArray);
}


