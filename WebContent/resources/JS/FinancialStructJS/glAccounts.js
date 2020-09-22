$(document).ready(function () {
    
    $("#getglAccountsBtn").click(function () {
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "get", 
            url: location.href.split('/Payroll')[0]+"/Payroll/dummyFinancialStructure/getAllGLAccounts",
            success: function (response) {
                if(response.theChainOfgLAccounts==null){
                    $('#glAccountsModal').modal('show');
                }else{
                    $("tbody").remove();
                    $('#glAccountsTable').append($('<tbody> <tr> </tr> </tbody>'));
                    var glAccountsArray = response.theChainOfgLAccounts;
                    for (var counter = 0; counter < glAccountsArray.length; counter++) {
                        var table = document.getElementById("glAccountsTable");
                        var row = table.insertRow(-1);
                        row.id = glAccountsArray[counter].code;
                        var cell1 = row.insertCell(0);
                        var cell2 = row.insertCell(1);
                        var cell3 = row.insertCell(2);
                        
                        cell1.innerHTML = glAccountsArray[counter].code;
                        cell2.innerHTML = glAccountsArray[counter].name;
                        cell3.innerHTML = glAccountsArray[counter].type;

                        $('#divforbtn').attr('hidden','');
                        $('#getglAccountsBtn').attr('hidden','');
                        $('#glAccountsDiv').removeAttr('hidden');
                    }
                }               
            },
            error: function (xhr) {
                console.log(xhr);
            }
        });
    });

    $("#saveglAccountsBtn").click(function () {
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "get", 
            url: location.href.split('/Payroll')[0]+"/Payroll/dummyFinancialStructure/getAllGLAccounts",
            success: function (response) {
                if(response.theChainOfgLAccounts==null){
                    $('#glAccountsModal').modal('show');
                }else{
                    var glAccountsArray = response.theChainOfgLAccounts;
                    var formData=JSON.stringify(glAccountsArray);
                    $.ajax({
                        headers: {
                            'Accept': 'application/json',
                            'Content-Type': 'application/json'
                        },
                        type: "post", 
                        url: location.href.split('/Payroll')[0]+"/Payroll/financialStructure/addGLAccounts",
                        data :formData,
                        success: function (response) {
                            $('#fail_msg').attr('hidden','');
                            $('#success_msg').removeAttr('hidden');
                            $('#glAccountsSavedModal').modal('show'); 
                        },
                        error: function (xhr) {
                            $('#success_msg').attr('hidden','');
                            $('#fail_msg').removeAttr('hidden');
                            $('#glAccountsSavedModal').modal('show'); 
                            console.log(xhr);
                        }
                    });
                }               
            },
            error: function (xhr) {
                $('#success_msg').attr('hidden','');
                $('#fail_msg').removeAttr('hidden');
                $('#glAccountsSavedModal').modal('show'); 
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