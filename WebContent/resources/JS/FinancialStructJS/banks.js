$(document).ready(function () {
    
    $("#getBanksBtn").click(function () {
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "get", 
            url: location.href.split('/Payroll')[0]+"/Payroll/dummyFinancialStructure/getAllBanks",
            success: function (response) {
                if(response.theChainOfBanks==null){
                    $('#BanksModal').modal('show');
                }else{
                    $("tbody").remove();
                    $('#BanksTable').append($('<tbody> <tr> </tr> </tbody>'));
                    var banksArray = response.theChainOfBanks;
                    for (var counter = 0; counter < banksArray.length; counter++) {
                        var table = document.getElementById("BanksTable");
                        var row = table.insertRow(-1);
                        row.id = banksArray[counter].code;
                        var cell1 = row.insertCell(0);
                        var cell2 = row.insertCell(1);
                        var cell3 = row.insertCell(2);
                        var cell4 = row.insertCell(3);
                        cell1.innerHTML = banksArray[counter].code;
                        cell2.innerHTML = banksArray[counter].name;
                        cell3.innerHTML = banksArray[counter].country;
                        cell4.innerHTML = banksArray[counter].branch;
                        
                        $('#divforbtn').attr('hidden','');
                        $('#getBanksBtn').attr('hidden','');
                        $('#banksDiv').removeAttr('hidden');
                    }
                }               
            },
            error: function (xhr) {
                console.log(xhr);
            }
        });
    });

    $("#saveBanksBtn").click(function () {
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "get", 
            url: location.href.split('/Payroll')[0]+"/Payroll/dummyFinancialStructure/getAllBanks",
            success: function (response) {
                if(response.theChainOfBanks==null){
                    $('#BanksModal').modal('show');
                }else{
                    var banksArray = response.theChainOfBanks;
                    var formData=JSON.stringify(banksArray);
                    $.ajax({
                        headers: {
                            'Accept': 'application/json',
                            'Content-Type': 'application/json'
                        },
                        type: "post", 
                        url: location.href.split('/Payroll')[0]+"/Payroll/financialStructure/addBanks",
                        data :formData,
                        success: function (response) {
                            $('#fail_msg').attr('hidden','');
                            $('#success_msg').removeAttr('hidden');
                            $('#BanksSavedModal').modal('show'); 
                        },
                        error: function (xhr) {
                            $('#success_msg').attr('hidden','');
                            $('#fail_msg').removeAttr('hidden');
                            $('#BanksSavedModal').modal('show'); 
                            console.log(xhr);
                        }
                    });
                }               
            },
            error: function (xhr) {
                $('#success_msg').attr('hidden','');
                $('#fail_msg').removeAttr('hidden');
                $('#BanksSavedModal').modal('show'); 
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