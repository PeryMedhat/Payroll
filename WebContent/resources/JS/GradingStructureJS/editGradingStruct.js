var controller = (function () {
    var code;
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
            grade = $(".input--style-4").val();
            window.location = 'showGradingStruct.html?grade='+grade;
            
        });

    });
})();
