
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

        $("#buttonSubmit").click(function (e) {
            $.ajax({
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                type: "get", 
                url: "http://localhost:8080/Payroll/employeeStructure/deleteEmployeeStructure",
                data: {
                    code: code
                },
                success: function (response) {
                    if(response==true){
                        $('#success_msg').removeAttr('hidden');
                    }else{
                        $('#fail_msg').removeAttr('hidden');
                    }
                    
                },
                error: function (xhr) {
                    $('#fail_msg').removeAttr('hidden');
                    console.log(xhr);
                }
            });
            
        
        
        });
        
    });
})();




