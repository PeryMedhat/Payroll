
var arrayOfTotalChain;
var table = document.getElementById("EmpStructTable");
var row;
$.ajax({
    headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
    },
    type: "get", 
    url: location.href.split('/Payroll')[0]+"/Payroll/employeeStructure/getAllTheEmployeeStructures",
    success: function (response) {
        if (response.theChain == null || response.theChain=='') {
            $('#chooseEmpStruct').attr('hidden', '');
            $('#tableIsEmptyMSG').removeAttr('hidden', '');
            $('#searchDiv').attr('hidden', '');
        } else {
            $('#chooseEmpStruct').removeAttr('hidden', '');
            arrayOfTotalChain = response.theChain;
            $('#EmpStructTable').append($('<tbody style="line-height:10px;"> <tr> </tr> </tbody>'));

            for (var counter = 0; counter < arrayOfTotalChain.length; counter++) {
                row = table.insertRow(-1);
                $(row).addClass("theTblRows");
                row.id = arrayOfTotalChain[counter].code;
                var cell1 = row.insertCell(0);
                var cell2 = row.insertCell(1);
                var cell3 = row.insertCell(2);
                if (arrayOfTotalChain[counter].hasParent == false) {
                    cell3.innerHTML = "Parent";
                } else if (arrayOfTotalChain[counter].hasChild == true) {
                    cell3.innerHTML = "SubParent";
                } else {
                    cell3.innerHTML = "Child";
                }
                cell1.innerHTML = arrayOfTotalChain[counter].code;
                cell2.innerHTML = arrayOfTotalChain[counter].name;
            } $('#EmpStructTable').removeAttr('hidden');
            
        }

    },
    error: function (xhr) {
    }
});

function myFunction() {
    var input, filter, table, tr, td, i, txtValue,noOfhiddenTr;
    input = document.getElementById("myInput");
    filter = input.value.toUpperCase();
    table = document.getElementById("EmpStructTable");
    tr = table.getElementsByTagName("tr");
    noOfhiddenTr=0;
  
    // Loop through all table rows, and hide those who don't match the search query
    for (i = 0; i < tr.length; i++) {
      td = tr[i].getElementsByTagName("td")[0];
      if (td) {
        txtValue = td.textContent || td.innerText;
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
          tr[i].style.display = "";
        } else {
          tr[i].style.display = "none";
          noOfhiddenTr++;
        }
      }
      if(tr.length-2 == noOfhiddenTr){
        $("#EmpStructTable").attr('hidden','');
        console.log(true);
      }else{
        $("#EmpStructTable").removeAttr('hidden')
      }
    }
    
  }


$(document).ready(function () {
        var code;
        var oldID = null;
        var newId;
        $('#exampleModalCenter').on('show.bs.modal', function(e) {
            $(" tr" ).on( "click", function( event ) {
                newId = $(this).attr('id');
                if (oldID != null) {
                    theId = '#' + oldID;
                }
                oldID = newId;
                console.log(oldID);
                $(".input--style-4").val(oldID);
                $('#exampleModalCenter').modal('hide');
            });
        });
        
        //$("#exampleModalCenter > #EmpStructTable tbody tr" ).trigger('click');
        $("#chooseEmpStruct").click(function () {
            if (oldID != null) {
                $(".input--style-4").val(oldID);
                $('#exampleModalCenter').modal('hide');
            }
        }); 

        $("#buttonSubmit").mouseenter(function () {
            $(this).removeClass('btn-primary');
            $(this).addClass('bg-success');
        });

        $("#buttonSubmit").mouseout(function () {
            $(this).removeClass('bg-success');
            $(this).addClass('btn-primary');
        });

        $("#buttonSubmit").click(function () {
            code = $(".input--style-4").val();
            window.location = 'showEditTable.html?code=' + code;
        });

    });