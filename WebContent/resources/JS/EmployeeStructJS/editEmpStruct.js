var arrayOfTotalChain;
var table = document.getElementById("EmpStructTable");
var row;
$.ajax({
    headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
    },
    type: "get", 
    url: "http://localhost:8080/Payroll/employeeStructure/getAllTheEmployeeStructures",
    success: function (response) {
        if (response.theChain == null || response.theChain=='') {
            $('#chooseEmpStruct').attr('hidden', '');
            $('#tableIsEmptyMSG').removeAttr('hidden', '');
        } else {
            $('#chooseEmpStruct').removeAttr('hidden', '');
            arrayOfTotalChain = response.theChain;
            $('#EmpStructTable').append($('<tbody> <tr> </tr> </tbody>'));

            for (var counter = 0; counter < arrayOfTotalChain.length; counter++) {
                row = table.insertRow(-1);
                $(row).addClass("theTblRows");
                row.id = arrayOfTotalChain[counter].code;
                var cell1 = row.insertCell(0);
                var cell2 = row.insertCell(1);
                var cell3 = row.insertCell(2);
                if (arrayOfTotalChain[counter].hasParent == false) {
                    cell1.innerHTML = "Parent";
                } else if (arrayOfTotalChain[counter].hasChild == true) {
                    cell1.innerHTML = "SubParent";
                } else {
                    cell1.innerHTML = "Child";
                }
                cell2.innerHTML = arrayOfTotalChain[counter].code;
                cell3.innerHTML = arrayOfTotalChain[counter].name;
            } $('#EmpStructTable').removeAttr('hidden');
        }

    },
    error: function (xhr) {
    }
});

$(document).ready(function () {
        var code;
        var oldID = null;
        var newId;
        $('#exampleModalCenter').on('show.bs.modal', function(e) {
            $(" tr" ).on( "click", function( event ) {
                newId = $(this).attr('id');
                if (oldID != null) {
                    theId = '#' + oldID;
                    $(theId).removeClass('bg-success');
                }
                oldID = newId;
                console.log(oldID);
                $(this).addClass('bg-success');
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