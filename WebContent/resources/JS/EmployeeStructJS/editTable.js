var controller = (function () {
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

    
    var table = document.getElementById("EmpStructTable");
    var sortedArray = new Array();
    var children = new Array();
    var subs = new Array();
    var queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const code = urlParams.get('code');
    var arrayOfTotalChain;
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: "get", //send it through get method
        url: "http://localhost:8080/Payroll/employeeStructure/showEmployeeStructure",
        data: {
            code: code
        },
        success: function (response) {
            if (response.theChain == null) {
                $('#employeeStructModal').modal('show');
            } else {
                arrayOfTotalChain = response.theChain;

                var today = new Date();
                var dd = String(today.getDate()).padStart(2, '0');
                var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
                var yyyy = today.getFullYear();

                today = dd + '/' + mm + '/' + yyyy;
                $("#valid_date").val(today);

                $("tbody").remove();
                $('#EmpStructTable').append($('<tbody> <tr> </tr> </tbody>'));
                showTheEmpStructTable();
                for (var index = 0; index < sortedArray.length; index++) {
                    var startDateString = sortedArray[index].startDate;
                    var sday = startDateString.slice(0, 3);
                    var smo = startDateString.slice(3, 6);
                    var syear = startDateString.slice(6, 11);
                    var modifiedStartDate = new Date(smo + '/' + sday + '/' + syear);
                    var validDate = $("#valid_date").val();
                    var vday = validDate.slice(0, 3);
                    var vmo = validDate.slice(3, 6);
                    var vyear = validDate.slice(6, 11);
                    var modifiedValidDate = new Date(vmo + '/' + vday + '/' + vyear);
                    if (modifiedStartDate < modifiedValidDate) {
                        var trCode = sortedArray[index].code;
                        $('#' + trCode).remove();
                    }
                }

            }
        },
        error: function (xhr) {
        }
    });

    function showTheEmpStructTable() {
        sortedArray = new Array();
        var children = new Array();
        var subs = new Array();
        for (var index = 0; index < arrayOfTotalChain.length; index++) {
            if (arrayOfTotalChain[index].hasParent == false) {
                sortedArray[0] = arrayOfTotalChain[index];
            } else if (arrayOfTotalChain[index].hasChild == true) {
                subs.push(arrayOfTotalChain[index]);
            } else {
                children.push(arrayOfTotalChain[index]);
            }
        }
        sortedArray = sortedArray.concat(subs).concat(children);

        for (index = 0; index < sortedArray.length; index++) {
            var theCode = code;
            var theHrefForEdit = 'editEmpStructData.html?code='
                + sortedArray[index].code + '&theCode='
                + theCode;
            var theHrefFordelemit = 'delemitEmpStructData.html?code='
                + sortedArray[index].code
                + '&theCode='
                + theCode;
            var theHrefFordelete = 'deleteEmpStructData.html?code='
                + sortedArray[index].code
                + '&theCode='
                + theCode;

            var theHrefForAddSub = 'addSubParent.html?code='    
            + sortedArray[index].code
            + '&theCode='
            + theCode;

            var row = table.insertRow(-1);
            row.id = sortedArray[index].code;
            var cell1 = row.insertCell(0);
            var cell2 = row.insertCell(1);
            var cell3 = row.insertCell(2);
            var cell4 = row.insertCell(3);
            var cell5 = row.insertCell(4);
            var cell6 = row.insertCell(5);
            if (sortedArray[index].hasParent == false) {
                cell1.innerHTML = "Parent";
                var theHrefForCopy = 'copyEmpStructData.html?code='
                    + sortedArray[index].code
                    + '&theCode='
                    + theCode;
                var theHrefFordeleteParent = 'deleteEmpStructParentData.html?code='
                    + sortedArray[index].code
                    + '&theCode='
                    + theCode;
                cell6.innerHTML = "<a href=" + theHrefForEdit
                    + ">Edit    </a>" + "<a href="
                    + theHrefFordelemit + ">   Delimit</a>"
                    + "<a href=" + theHrefFordeleteParent
                    + ">   Delete</a>" + "<a href="
                    + theHrefForCopy + ">   Copy</a>"
                    +"<a href=" +theHrefForAddSub+ ">Add </a>" ;
            } else if (sortedArray[index].hasChild == true) {
                cell1.innerHTML = "SubParent";
                cell6.innerHTML = "<a href=" + theHrefForEdit
                    + ">Edit    </a>" + "<a href="
                    + theHrefFordelemit + ">   Delimit</a>"
                    + "<a href=" + theHrefFordelete
                    + ">   Delete</a>"
                    +"<a href=" +theHrefForAddSub
                    + ">Add</a>" ;
            } else {
                cell1.innerHTML = "Child";
                cell6.innerHTML = "<a href=" + theHrefForEdit
                    + ">Edit    </a>" + "<a href="
                    + theHrefFordelemit + ">   Delimit</a>"
                    + "<a href=" + theHrefFordelete
                    + ">   Delete</a>";
            }
            cell2.innerHTML = sortedArray[index].code;
            cell3.innerHTML = sortedArray[index].name;
            cell4.innerHTML = sortedArray[index].startDate;
            cell5.innerHTML = sortedArray[index].endDate;

        } $('#theEmpStruct').removeAttr('hidden');
    }

    jQuery(document).ready(function ($) {

        $("#modalOkButton").click(function (e) {
            location = 'editEmpStruct.html';

        });

        $("#valid_date").on('apply.daterangepicker', function () {
            $('#tableIsEmptyMSG').attr('hidden','');
            $("tbody").remove();
            $('#EmpStructTable').append($('<tbody> <tr> </tr> </tbody>'));
            showTheEmpStructTable();
            $('#EmpStructTable').removeAttr('hidden');
            for (var index = 0; index < sortedArray.length; index++) {
                var startDateString = sortedArray[index].startDate;
                var sday = startDateString.slice(0, 3);
                var smo = startDateString.slice(3, 6);
                var syear = startDateString.slice(6, 11);
                var modifiedStartDate = new Date(smo + '/' + sday + '/' + syear);
                var validDate = $("#valid_date").val();
                var vday = validDate.slice(0, 3);
                var vmo = validDate.slice(3, 6);
                var vyear = validDate.slice(6, 11);
                var modifiedValidDate = new Date(vmo + '/' + vday + '/' + vyear);
                if (modifiedStartDate < modifiedValidDate) {
                    var trCode = sortedArray[index].code;
                    $('#' + trCode).remove();
                }
            }
            if(($("#EmpStructTable > tbody > tr").length-1)==0){
                $('#EmpStructTable').attr('hidden','');
                $('#tableIsEmptyMSG').removeAttr('hidden','');
            }
        });

    });
})();
