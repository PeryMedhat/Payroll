function getTheCode (){

    var code =document.getElementById("empstruct_code");
    var codeValue = code.value;


        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
               
            
                var chainArray =[];
                chainArray = this.response;
                console.log(chainArray);
               
       
         
            }
          };
    
          xhttp.open("GET", "http://localhost:8080/Payroll/employeeStructure/showEmployeeStructure?code="+codeValue, true);
          xhttp.setRequestHeader("Content-type", "application/json");
    
          xhttp.send();
    

}