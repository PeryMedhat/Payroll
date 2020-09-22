$(document).ready(function () {
    
    $("#getpaymentMethodsBtn").click(function () {
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "get", 
            url: location.href.split('/Payroll')[0]+"/Payroll/dummyFinancialStructure/getAllPaymentMethods",
            success: function (response) {
                if(response.theChainOfpaymentMethods==null){
                    $('#paymentMethodsModal').modal('show');
                }else{
                    $("tbody").remove();
                    $('#paymentMethodsTable').append($('<tbody> <tr> </tr> </tbody>'));
                    var paymentMethodsArray = response.theChainOfpaymentMethods;
                    for (var counter = 0; counter < paymentMethodsArray.length; counter++) {
                        var table = document.getElementById("paymentMethodsTable");
                        var row = table.insertRow(-1);
                        row.id = paymentMethodsArray[counter].code;
                        var cell1 = row.insertCell(0);
                        var cell2 = row.insertCell(1);
                        var cell3 = row.insertCell(2);
                        
                        cell1.innerHTML = paymentMethodsArray[counter].startDate;
                        cell2.innerHTML = paymentMethodsArray[counter].endDate;
                        cell3.innerHTML = paymentMethodsArray[counter].paymentMethod;

                        $('#divforbtn').attr('hidden','');
                        $('#getpaymentMethodsBtn').attr('hidden','');
                        $('#paymentMethodsDiv').removeAttr('hidden');
                    }
                }               
            },
            error: function (xhr) {
                console.log(xhr);
            }
        });
    });

    $("#savepaymentMethodsBtn").click(function () {
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "get", 
            url: location.href.split('/Payroll')[0]+"/Payroll/dummyFinancialStructure/getAllPaymentMethods",
            success: function (response) {
                if(response.theChainOfpaymentMethods==null){
                    $('#paymentMethodsModal').modal('show');
                }else{
                    var paymentMethodsArray = response.theChainOfpaymentMethods;
                    var formData=JSON.stringify(paymentMethodsArray);
                    $.ajax({
                        headers: {
                            'Accept': 'application/json',
                            'Content-Type': 'application/json'
                        },
                        type: "post", 
                        url: location.href.split('/Payroll')[0]+"/Payroll/financialStructure/addPaymentMethods",
                        data :formData,
                        success: function (response) {
                            $('#fail_msg').attr('hidden','');
                            $('#success_msg').removeAttr('hidden');
                            $('#paymentMethodsSavedModal').modal('show'); 
                        },
                        error: function (xhr) {
                            $('#success_msg').attr('hidden','');
                            $('#fail_msg').removeAttr('hidden');
                            $('#paymentMethodsSavedModal').modal('show'); 
                            console.log(xhr);
                        }
                    });
                }               
            },
            error: function (xhr) {
                $('#success_msg').attr('hidden','');
                $('#fail_msg').removeAttr('hidden');
                $('#paymentMethodsSavedModal').modal('show'); 
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