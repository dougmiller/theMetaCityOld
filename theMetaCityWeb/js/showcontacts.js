$(document).ready(function() {
    $(".contactinfo").hide();
    $("#references > div > h3").click(function() {
        $(this).next('.contactinfo').toggle('fast');
    });
});

