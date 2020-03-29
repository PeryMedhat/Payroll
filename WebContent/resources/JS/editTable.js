var controller = (function () {
    queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const code = urlParams.get('code');
    var arrayOfTotalChain;
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: "get", //send it through get method
        url: "http://localhost:8080/Payroll/employeeStructure/showEmployeeStructure",
        data: {
            code: code
        },
        success: function (response) {
            if(response.theChain==null){
                $('.theEmpStruct').attr('hidden','');
                $('#employeeStructModal').modal('show');
            }else{
                arrayOfTotalChain = response.theChain;
            var table = document.getElementById("EmpStructTable");
            var sortedArray=new Array();
            var children=new Array();
            var subs=new Array();
            for (index = 0; index < arrayOfTotalChain.length; index++) {
                if(arrayOfTotalChain[index].hasParent==false){
                    sortedArray[0]=arrayOfTotalChain[index];
                }else if(arrayOfTotalChain[index].hasChild==true){
                    subs.push(arrayOfTotalChain[index]);
                }else{children.push(arrayOfTotalChain[index]);}
            }
            sortedArray = sortedArray.concat(subs).concat(children);
            for (index = 0; index < sortedArray.length; index++) { 
                var theCode = code;
                var theHrefForEdit = 'editEmpStructData.html?code='+sortedArray[index].code+'&theCode='+theCode;
                var theHrefFordelemit = 'delemitEmpStructData.html?code='+sortedArray[index].code+'&theCode='+theCode;
                var theHrefFordelete =  'deleteEmpStructData.html?code='+sortedArray[index].code+'&theCode='+theCode;
                var row = table.insertRow(-1);
                var cell1=row.insertCell(0);
                var cell2=row.insertCell(1);
                var cell3=row.insertCell(2);
                var cell4=row.insertCell(3);
                var cell5=row.insertCell(4);
                var cell6=row.insertCell(5);
                if(sortedArray[index].hasParent==false){
                    cell1.innerHTML="Parent";
                    var theHrefForCopy = 'copyEmpStructData.html?code='+sortedArray[index].code+'&theCode='+theCode;
                    cell6.innerHTML="<a href="+theHrefForEdit+">Edit    </a>"
                    +"<a href="+theHrefFordelemit+">   Delimit</a>"
                    +"<a href="+theHrefFordelete+">   Delete</a>"
                    +"<a href="+theHrefForCopy+">   Copy</a>";
                }else if(sortedArray[index].hasChild==true){
                    cell1.innerHTML="SubParent";
                    cell6.innerHTML="<a href="+theHrefForEdit+">Edit    </a>"
                    +"<a href="+theHrefFordelemit+">   Delimit</a>"
                    +"<a href="+theHrefFordelete+">   Delete</a>";
                }else{ 
                    cell1.innerHTML="Child";
                    cell6.innerHTML="<a href="+theHrefForEdit+">Edit    </a>"
                      +"<a href="+theHrefFordelemit+">   Delimit</a>"
                    +"<a href="+theHrefFordelete+">   Delete</a>";}
                cell2.innerHTML =sortedArray[index].code;
                cell3.innerHTML =sortedArray[index].name;
                cell4.innerHTML =sortedArray[index].startDate;
                cell5.innerHTML =sortedArray[index].endDate;
                
               
            } 
            }
            
        },
        error: function (xhr) {
            console.log(xhr);
        }
    });

    
    jQuery(document).ready(function ($) {
        $("#modalOkButton").click(function (e) {
            location='editEmpStruct.html';
        });
        
    });
})();





