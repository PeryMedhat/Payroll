(function ($) {
    'use strict';
    /*==================================================================
        [ Daterangepicker ]*/
    try {
        $('.js-datepicker').daterangepicker({
            "singleDatePicker": true,
            "showDropdowns": true,
            "autoUpdateInput": false,
            locale: {
                format: 'DD/MM/YYYY'
            },
        });
    
        var myCalendar = $('.js-datepicker');
        var isClick = 0;
    
        $(window).on('click',function(){
            isClick = 0;
        });
    
        $(myCalendar).on('apply.daterangepicker',function(ev, picker){
            isClick = 0;
            $(this).val(picker.startDate.format('DD/MM/YYYY'));
    
        });
    
        $('.js-btn-calendar').on('click',function(e){
            e.stopPropagation();
    
            if(isClick === 1) isClick = 0;
            else if(isClick === 0) isClick = 1;
    
            if (isClick === 1) {
                myCalendar.focus();
            }
        });
    
        $(myCalendar).on('click',function(e){
            e.stopPropagation();
            isClick = 1;
        });
    
        $('.daterangepicker').on('click',function(e){
            e.stopPropagation();
        });
    
    
    } catch(er) {console.log(er);}
    /*[ Select 2 Config ]
        ===========================================================*/
    
    try {
        var selectSimple = $('.js-select-simple');
    
        selectSimple.each(function () {
            var that = $(this);
            var selectBox = that.find('select');
            var selectDropdown = that.find('.select-dropdown');
            selectBox.select2({
                dropdownParent: selectDropdown
            });
        });
    
    } catch (err) {
        console.log(err);
    }
    

})(jQuery);
var controller = (function () {
    queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const grade = urlParams.get('grade');

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
            location='showGradingStruct.html?grade='+grade;
        });
        
        $("#buttonSubmit").click(function (e) {
            $('#success_msg').attr('hidden','');
            $('#fail_msg').attr('hidden','');

            var endDate = $("#end_date").val();
            $.ajax({
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                type: "get", 
                url: location.href.split('/Payroll')[0]
                +"/Payroll/GradingStruct/delemitGradingAndSalary",
                data: {
                    grade: grade,
                    endDate:endDate
                },
                success: function (response) {
                    $('#success_msg').removeAttr('hidden');
                    $('#gradingAndSalaryModal').modal('show');      
                },
                error: function (xhr) {
                    $('#fail_msg').removeAttr('hidden');
                    $('#gradingAndSalaryModal').modal('show');       
                    console.log(xhr);
                }
            });
            
        });
        
    });
})();





