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
        type: "get",
        url: "http://localhost:8080/Payroll/payType/getPayType",
        data: {
            code: code
        },
        success: function (response) {
            $('#payType_code').val(response.code);
            $('#payType_name').val(response.name);
            $('#start_date').val(response.startDate);
            $('#end_date').val(response.endDate);
            $('#interval').val(response.interval);
            $('#type').val(response.type);
            $('#inputValue').val(response.inputValue);


        },
        error: function (xhr) {
            console.log(xhr);
        }
    });

    jQuery(document).ready(function ($) {
        $("#modalOkButton").click(function (e) {
            location = 'editPayType.html';

        });
    });
})();
