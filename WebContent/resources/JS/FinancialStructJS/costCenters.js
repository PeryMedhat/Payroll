$(document).ready(function () {
    
    $("#getCostCentersBtn").click(function () {
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "get", 
            url: location.href.split('/Payroll')[0]+"/Payroll/dummyFinancialStructure/getAllCostCenters",
            success: function (response) {
                if(response.theChainOfcostCenters==null){
                    $('#costCenterModal').modal('show');
                }else{
                    $("tbody").remove();
                    $('#costCenterTable').append($('<tbody> <tr> </tr> </tbody>'));
                    var costCentersArray = response.theChainOfcostCenters;
                    for (var counter = 0; counter < costCentersArray.length; counter++) {
                        var table = document.getElementById("costCenterTable");
                        var row = table.insertRow(-1);
                        row.id = costCentersArray[counter].code;
                        var cell1 = row.insertCell(0);
                        var cell2 = row.insertCell(1);
                        
                        cell1.innerHTML = costCentersArray[counter].code;
                        cell2.innerHTML = costCentersArray[counter].name;
                        
                        $('#divforbtn').attr('hidden','');
                        $('#getCostCentersBtn').attr('hidden','');
                        $('#costCenterDiv').removeAttr('hidden');
                    }
                }               
            },
            error: function (xhr) {
                console.log(xhr);
            }
        });
    });

    $("#savecostCentersBtn").click(function () {
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "get", 
            url: location.href.split('/Payroll')[0]+"/Payroll/dummyFinancialStructure/getAllCostCenters",
            success: function (response) {
                if(response.theChainOfcostCenters==null){
                    $('#costCenterModal').modal('show');
                }else{
                    var costCentersArray = response.theChainOfcostCenters;
                    var formData=JSON.stringify(costCentersArray);
                    $.ajax({
                        headers: {
                            'Accept': 'application/json',
                            'Content-Type': 'application/json'
                        },
                        type: "post", 
                        url: location.href.split('/Payroll')[0]+"/Payroll/financialStructure/addCostCenters",
                        data :formData,
                        success: function (response) {
                            $('#fail_msg').attr('hidden','');
                            $('#success_msg').removeAttr('hidden');
                            $('#costCenterSavedModal').modal('show'); 
                        },
                        error: function (xhr) {
                            $('#success_msg').attr('hidden','');
                            $('#fail_msg').removeAttr('hidden');
                            $('#costCenterSavedModal').modal('show'); 
                            console.log(xhr);
                        }
                    });
                }               
            },
            error: function (xhr) {
                $('#success_msg').attr('hidden','');
                $('#fail_msg').removeAttr('hidden');
                $('#costCenterSavedModal').modal('show'); 
                console.log(xhr);
            }
        });

    });


    $("#modalOkButton").click(function (e) {
        location = '../../index.html';

    });

    $(".btn").mouseenter(function () {
        $(this).removeClass('btn-primary');
        $(this).addClass('bg-success');
    });

    $(".btn").mouseout(function () {
        $(this).removeClass('bg-success');
        $(this).addClass('btn-primary');
    });

});