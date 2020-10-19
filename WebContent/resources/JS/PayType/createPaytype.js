var controller = (function () {
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

        $(window).on('click', function () {
            isClick = 0;
        });

        $(myCalendar).on('apply.daterangepicker', function (ev, picker) {
            isClick = 0;
            $(this).val(picker.startDate.format('DD/MM/YYYY'));

        });

        $('.js-btn-calendar').on('click', function (e) {
            e.stopPropagation();

            if (isClick === 1) isClick = 0;
            else if (isClick === 0) isClick = 1;

            if (isClick === 1) {
                myCalendar.focus();
            }
        });

        $(myCalendar).on('click', function (e) {
            e.stopPropagation();
            isClick = 1;
        });

        $('.daterangepicker').on('click', function (e) {
            e.stopPropagation();
        });


    } catch (er) { console.log(er); }
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
    //dropdowns
    var inputVals;
    var intervals;
    var types;
    var Taxes;

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: "get",
        url: location.href.split('/Payroll')[0]
        +"/Payroll/lookUps/getInputVals",

        success: function (response) {
            inputVals = response;
            for (var i = 0; i < inputVals.length; i++) {
                if (i == 0) {
                    $('#inputValue').append($('<option selected>').val(inputVals[i].name).text(inputVals[i].name));
                } else {
                    $('#inputValue').append($('<option>').val(inputVals[i].name).text(inputVals[i].name));
                }
            }
        },
        error: function (xhr) {
            console.log(xhr);
        }
    });
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: "get",
        url: location.href.split('/Payroll')[0]
        +"/Payroll/lookUps/getIntervals",
        success: function (response) {
            intervals = response;
            for (var i = 0; i < intervals.length; i++) {
                if (i == 0) {
                    $('#interval').append($('<option selected>').val(intervals[i].name).text(intervals[i].name));
                } else {
                    $('#interval').append($('<option>').val(intervals[i].name).text(intervals[i].name));
                }
            }
        },
        error: function (xhr) {
            console.log(xhr);
        }
    });

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: "get",
        url: location.href.split('/Payroll')[0]
        +"/Payroll/lookUps/gettaxes",
        success: function (response) {
            Taxes = response;
            for (var i = 0; i < Taxes.length; i++) {
                if (i == 0) {
                    $('#Taxes').append($('<option selected>').val(Taxes[i].name).text(Taxes[i].name));
                } else {
                    $('#Taxes').append($('<option>').val(Taxes[i].name).text(Taxes[i].name));
                }
            }
        },
        error: function (xhr) {
            console.log(xhr);
        }
    });


    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: "get",
        url: location.href.split('/Payroll')[0]
        +"/Payroll/lookUps/getTypes",
        success: function (response) {
            types = response;
            for (var i = 0; i < types.length; i++) {
                if (i == 0) {
                    $('#type').append($('<option selected>').val(types[i].name).text(types[i].name));
                } else {
                    $('#type').append($('<option>').val(types[i].name).text(types[i].name));
                }
            }
        },
        error: function (xhr) {
            console.log(xhr);
        }
    });


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
            location = '../../index.jsp';
        });

        $('#inputValue').on('change', function (e) {
            if($('#inputValue').val()=== "unit"||$('#inputValue').val()=== "both") {
                $('#payTypeUnit').removeAttr('hidden','');
            } 
            if(!($('#inputValue').val()=== "unit"||$('#inputValue').val()=== "both")) {
                $('#payTypeUnit').attr('hidden','');
            } 
        });

        $("#buttonSubmit").click(function (e) {
            var code = document.getElementById("payType_code");
            var name = document.getElementById("payType_name");
            var startDate = document.getElementById("start_date");
            var endDate = document.getElementById("end_date");
            var interval = document.getElementById("interval");
            var type = document.getElementById("type");
            var inputValue = document.getElementById("inputValue");
            var unit = document.getElementById("unit");
            var taxes =document.getElementById("Taxes");
            var unitisEmpty = document.getElementById("unitisEmpty");
            var nameisEmpty = document.getElementById("nameisEmpty");
            var codeisEmpty = document.getElementById("codeisEmpty");
            var startDateisEmpty = document.getElementById("startDateisEmpty");
            var endDateisEmpty = document.getElementById("endDateisEmpty");
            var unitisEmpty = document.getElementById("unitisEmpty");
            
            if (code.value == '' || name.value == '' || startDate.value == '' || endDate.value == '' || interval.value == '' || type.value == '' || inputValue.value == ''||((inputValue.value==="unit"||inputValue.value=== "both")&&unit.value == '')) {
                if (unit.value == '' ) {
                    unitisEmpty.removeAttribute('hidden');
                    unit.setAttribute("class", "input--style-4-redBorder");
                }
                if (code.value == '') {
                    codeisEmpty.removeAttribute('hidden');
                    code.setAttribute("class", "input--style-4-redBorder");
                }

                if (name.value == '') {
                    nameisEmpty.removeAttribute('hidden');
                    name.setAttribute("class", "input--style-4-redBorder");
                }
                if (startDate.value == '') {
                    startDateisEmpty.removeAttribute('hidden');
                    startDate.setAttribute("class", "input--style-4-redBorder");
                }
                if (endDate.value == '') {
                    endDateisEmpty.removeAttribute('hidden');
                    endDate.setAttribute("class", "input--style-4-redBorder");
                 }

            }else if(isNaN(unit.value)){
                $('#unitisEmpty').html('*should be a number');
                unit.setAttribute("class", "input--style-4-redBorder");
                unitisEmpty.removeAttribute('hidden');
            } else {
                var percentageUnit = unit.value /100;
                var payTypeModel = {
                    "code": code.value,
                    "name": name.value,
                    "startDate": startDate.value,
                    "endDate": endDate.value,
                    "interval": interval.value,
                    "type": type.value,
                    "inputValue": inputValue.value,
                    "unit":percentageUnit,
                    "taxes":taxes.value
                };
                var formData = JSON.stringify(payTypeModel);
                $.ajax({
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    type: "post",
                    url: location.href.split('/Payroll')[0]
                    +"/Payroll/payType/addPayType",
                    data: formData,
                    success: function (response) {
                        $('#ResultOfPayTypeCreation').modal('show');
                        $('#success_msg').removeAttr('hidden');
                    },
                    error: function (xhr) {
                        var errorMessage = xhr.responseJSON.message;
                        $('#success_msg').attr('hidden', '');
                        $('#ResultOfPayTypeCreation').modal('show');
                        $('#fail_msg').removeAttr('hidden');
                        document.getElementById('fail_msg').innerHTML = "Error!!" + errorMessage;
                    }
                });

            }
            

        });

    });

})();