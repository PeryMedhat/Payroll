
var controller = (function () {
    queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const code = urlParams.get('code');

    jQuery(document).ready(function ($) {
        $("#buttonSubmit").mouseenter(function () {
            $(this).removeClass('btn-primary');
            $(this).addClass('bg-success');
        });

        $("#buttonSubmit").mouseout(function () {
            $(this).removeClass('bg-success');
            $(this).addClass('btn-primary');
        });
        $("#modalOkButton").click(function (e) {
            location='editPayType.html';
        });
        $("#buttonSubmit").click(function (e) {
            $('#success_msg').attr('hidden','');
            $('#fail_msg').attr('hidden','');

            $.ajax({
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                type: "get", 
                url: location.href.split('/Payroll')[0]
                +"/Payroll/payType/deletePayType",
                data: {
                    code: code
                },
                success: function (response) {
                    $('#payTypeModal').modal('show');
                   $('#success_msg').removeAttr('hidden');
                   
                },
                error: function (xhr) {
                    var errorMessage = xhr.responseJSON.message;
                    $('#success_msg').attr('hidden', '');
                    $('#payTypeModal').modal('show');
                    $('#fail_msg').removeAttr('hidden');
                    document.getElementById('fail_msg').innerHTML = "Error!!" + errorMessage;
                }
            });
            
        });
        
    });
})();
