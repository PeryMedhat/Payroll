var controller = (function () {

    var table = document.getElementById("EmpStructTable");
    var sortedArray = new Array();
    var children = new Array();
    var subs = new Array();
    var queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const code = urlParams.get('code');
    var arrayOfTotalChain;
    var arrangedArray = new Array();
    var children = new Array();
    var subs = new Array();
    var payTypes;

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

                $.ajax({
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    type: "get", //send it through get method
                    url: "http://localhost:8080/Payroll/payType/getAllPayTypes",
                    success: function (response) {
                        if (response == null || response == '') {
                            payTypes = null;
                        } else {
                            payTypes = response;
                            $("tbody").remove();
                            $('#EmpStructTable').append($('<tbody> <tr> </tr> </tbody>'));
                            showTheEmpStructTable();
                            $('#EmpStructTable').removeAttr('hidden');

                        }
                    },
                    error: function (xhr) {
                    }
                });
            }
        },
        error: function (xhr) {
        }
    });

    function showTheEmpStructTable() {
        sortedArray = new Array();
        children = new Array();
        subs = new Array();
        arrangedArray = new Array();
        for (var x = 0; x < arrayOfTotalChain.length; x++) {
            if (arrayOfTotalChain[x].hasParent == false) {
                sortedArray[0] = arrayOfTotalChain[x];
            } else if (arrayOfTotalChain[x].hasChild == true) {
                subs.push(arrayOfTotalChain[x]);
            } else {
                children.push(arrayOfTotalChain[x]);
            }
        }
        sortedArray = sortedArray.concat(subs).concat(children);

        arrangeTheTable();
        for (var counter = 0; counter < arrangedArray.length; counter++) {
            var theCode = code;

            var row = table.insertRow(-1);
            row.id = arrangedArray[counter].code;
            var cell1 = row.insertCell(0);
            var cell2 = row.insertCell(1);
            var cell3 = row.insertCell(2);
            var cell4 = row.insertCell(3);
            var cell5 = row.insertCell(4); //payTypes check boxes XD

            if (arrangedArray[counter].hasParent == false) {
                cell1.innerHTML = "Parent";
                cell3.innerHTML = "N/A";
                row.setAttribute('class', 'parent');

            } else if (arrangedArray[counter].hasChild == true) {
                cell1.innerHTML = "SubParent";
                cell3.innerHTML = arrangedArray[counter].parentCode;
            } else {
                cell1.innerHTML = "Child";
                cell3.innerHTML = arrangedArray[counter].parentCode;
            }
            cell2.innerHTML = arrangedArray[counter].code;
            cell4.innerHTML = arrangedArray[counter].name;
            if (payTypes == null) {
                cell5.innerHTML = "No paytypes created yet";
            } else {
                var payTypesCheckBoxs = " ";
                // var arrayOfEmpCodes = new Array();
                // for (var u = 0; u < arrangedArray.length; u++) {
                //     arrayOfEmpCodes.push(arrangedArray[u].code);
                // }
                // console.log(arrayOfEmpCodes);
                // var EmpCodes = JSON.stringify(arrayOfEmpCodes);
                // var theAssignedPayTypes;
                // $.ajax({
                //     headers: {
                //         'Accept': 'application/json',
                //         'Content-Type': 'application/json'
                //     },
                //     type: "POST",
                //     url: "http://localhost:8080/Payroll/employeeStructure/getAllPaytypesAssignedToEmpStruct",
                //     data: EmpCodes,
                //     success: function (response) {
                //         theAssignedPayTypes == new Array()
                //         theAssignedPayTypes = response;


                //     },
                //     error: function (xhr) {
                //     }
                // });
                for (var p = 0; p < payTypes.length; p++) {
                    
                    // if (theAssignedPayTypes.code.localeCompare(arrangedArray[counter].code) == 0
                    //     && theAssignedPayTypes.payTypeCodes.includes(payTypes[p].code)) {
                    //     payTypesCheckBoxs += "<label><input checked id=\"" + payTypes[p].code + "-" + arrangedArray[counter].code + "\"" + "type=\"checkbox\"  autocomplete=\"off\"> " + payTypes[p].code + "</label> ";
                    // } else {
                        payTypesCheckBoxs += "<label><input id=\"" + payTypes[p].code + "-" + arrangedArray[counter].code + "\"" + "type=\"checkbox\"  autocomplete=\"off\"> " + payTypes[p].code + "</label> ";
                    //}
                }
                cell5.innerHTML = payTypesCheckBoxs;
            }

        }
        $('#theEmpStruct').removeAttr('hidden');
    }

    function arrangeTheTable() {
        var noOfChildren;
        var noOfSubParents;
        var parentCode;
        var oldParentCode;
        var childFounded;
        var parent_Code;
        for (var b = 0; b < sortedArray.length; b++) {
            if (sortedArray[b].hasParent == false) {
                parent_Code = sortedArray[b].code;
                var index = b;
            }
        }
        for (; index < sortedArray.length; index++) {
            parentCode = sortedArray[index].code;
            arrangedArray.push(sortedArray[index]);
            var isThisParent = sortedArray[index].hasChild;
            sortedArray.splice(index, 1);
            if (isThisParent) {
                oldParentCode = parentCode;
                noOfChildren = 0;
                noOfSubParents = 0;
                childFounded = false;
                for (var i = 0; i < sortedArray.length; i++) {
                    if (parentCode.localeCompare(sortedArray[i].parentCode) == 0 && sortedArray[i].hasChild == false) {
                        index = i - 1;
                        noOfChildren++;
                        childFounded = true;
                    }
                    if (parentCode.localeCompare(sortedArray[i].parentCode) == 0 && sortedArray[i].hasChild == true) {
                        noOfSubParents++;
                    }
                }
                if (noOfChildren > 0) { noOfChildren--; }
                if (!childFounded) {
                    for (var i = 0; i < sortedArray.length; i++) {
                        if (parentCode.localeCompare(sortedArray[i].parentCode) == 0) {
                            index = i - 1;
                        }
                    } if (noOfSubParents > 0) { noOfSubParents--; }
                }

            } else if (noOfChildren != 0) {
                for (var i = 0; i < sortedArray.length; i++) {
                    if (oldParentCode.localeCompare(sortedArray[i].parentCode) == 0 && sortedArray[i].hasChild == false) {
                        index = i - 1;
                        noOfChildren--;
                        childFounded = true;
                    }
                }
            } else if (noOfSubParents != 0) {
                for (var i = 0; i < sortedArray.length; i++) {
                    if (oldParentCode.localeCompare(sortedArray[i].parentCode) == 0) {
                        index = i - 1;
                        noOfSubParents--;
                    }
                }
            } else {
                for (var i = 0; i < sortedArray.length; i++) {
                    if (parent_Code.localeCompare(sortedArray[i].parentCode) == 0) {
                        index = i - 1;
                        break;
                    } else { index = -1; }
                }
            }

        }
        if (!sortedArray.length == 0) {
            arrangeTheTable();
        }
    }

    var unassignedModel;
    var listOfUnAssignedModel = new Array();
    var y = 0;
    var assignmentModel;
    var listOfModels = new Array();
    var x = 0;
    var empStructCode;
    var listOfAssignedPayTypes;
    var j; var i;
    function addUnAssignedModel(unassignedModel) {
        listOfUnAssignedModel[y] = unassignedModel;
        y++;
    }
    function saveUnAssignedModelValues(code, listOfPayTypes) {
        unassignedModel = {
            "code": code,
            "payTypeCodes": listOfPayTypes
        }
        addUnAssignedModel(unassignedModel);
    }
    function addModel(assignmentModel) {
        listOfModels[x] = assignmentModel;
        x++;
    }
    function saveModelValues(code, listOfPayTypes) {
        assignmentModel = {
            "code": code,
            "payTypeCodes": listOfPayTypes
        }
        addModel(assignmentModel);
    }

    jQuery(document).ready(function ($) {

        $("#modalOkButton").click(function (e) {
            location = 'assignPayTypeToEmpStruct.html';

        });

        $("#buttonSubmit").mouseenter(function () {
            $(this).removeClass('btn-primary');
            $(this).addClass('bg-success');
        });

        $("#buttonSubmit").mouseout(function () {
            $(this).removeClass('bg-success');
            $(this).addClass('btn-primary');
        });

        $("#buttonSubmit").click(function () {
            y = 0;
            listOfUnAssignedModel = new Array();
            x = 0;
            listOfModels = new Array();
            $('#EmpStructTable > tbody  > tr').each(function () {
                empStructCode = this.id;
                if (empStructCode != '' || empStructCode != null || empStructCode != "undefined") {
                    listOfAssignedPayTypes = new Array();
                    listOfUnAssigned = new Array();
                    for (i = 0; i < payTypes.length; i++) {
                        if (document.getElementById(payTypes[i].code + "-" + empStructCode) != null
                            && document.getElementById(payTypes[i].code + "-" + empStructCode).checked
                        ) {
                            listOfAssignedPayTypes.push(payTypes[i].code);
                        }
                    }
                    saveUnAssignedModelValues(empStructCode, null);
                    saveModelValues(empStructCode, listOfAssignedPayTypes);
                }
            });
            listOfModels.shift();

            var formData = JSON.stringify(listOfModels);
            $.ajax({
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                type: "POST",
                url: "http://localhost:8080/Payroll/employeeStructure/assignPaytypeToEmployeeStruct",
                data: formData,
                success: function (response) {
                    $('#ResultOfEmployeeStructAssignment').modal('show');
                    $('#success_msg').removeAttr('hidden');
                },
                error: function (xhr) {
                    var errorMessage = xhr.responseJSON;
                    $('#ResultOfEmployeeStructAssignment').modal('show');
                    $('#fail_msg').removeAttr('hidden');
                    document.getElementById('fail_msg').innerHTML = "Error!!" + errorMessage;
                }
            });
        });
    });
})();
