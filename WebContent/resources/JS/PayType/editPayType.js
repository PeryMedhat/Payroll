var arrayOfTotalChain;
var table = document.getElementById("PayTypeTable");
var row;
$.ajax({
    headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
    },
    type: "get", 
    url: "http://localhost:8080/Payroll/payType/getAllPayTypes",
    success: function (response) {
        if (response == null || response=='') {
            $('#choosePaytype').attr('hidden', '');
            $('#tableIsEmptyMSG').removeAttr('hidden', '');
        } else {
            $('#choosePaytype').removeAttr('hidden', '');
            arrayOfTotalChain = response;
            $('#PayTypeTable').append($('<tbody> <tr> </tr> </tbody>'));

            for (var counter = 0; counter < arrayOfTotalChain.length; counter++) {
                row = table.insertRow(-1);
                $(row).addClass("theTblRows");
                row.id = arrayOfTotalChain[counter].code;
                var cell1 = row.insertCell(0);
                var cell2 = row.insertCell(1);
                cell1.innerHTML = arrayOfTotalChain[counter].code;
                cell2.innerHTML = arrayOfTotalChain[counter].name;
            } $('#PayTypeTable').removeAttr('hidden');
        }
    },
    error: function (xhr) {
    }
});




var controller = (function () {
  
    jQuery(document).ready(function ($) {
        var code;
        var oldID = null;
        var newId;
        $('#exampleModalCenter').on('show.bs.modal', function(e) {
            $(" tr" ).on( "click", function( event ) {
                newId = $(this).attr('id');
                if (oldID != null) {
                    theId = '#' + oldID;
                    $(theId).removeClass('bg-success');
                }
                oldID = newId;
                console.log(oldID);
                $(this).addClass('bg-success');
            });
        });
        
        $("#choosePaytype").click(function () {
            if (oldID != null) {
                $(".input--style-4").val(oldID);
                $('#exampleModalCenter').modal('hide');
            }
        }); 
        $("#buttonSubmit").mouseenter(function () {
            $(this).removeClass('btn-primary');
            $(this).addClass('bg-success');
        });

        $("#buttonSubmit").mouseout(function () {
            $(this).removeClass('bg-success');
            $(this).addClass('btn-primary');
        });

        $("#buttonSubmit").click(function (e) {
            code = $(".input--style-4").val();
            window.location = 'showPayTypeElement.html?code='+code;
            
        });
    });
})();
