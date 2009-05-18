$(document).ready(function() {
    $('#nojavascript').hide();

    $('#aboutdiv').hide();

    $('#abouth3').click(function() {
        var displayed = $('#aboutdiv').css('display');
        if (displayed == 'none') {
            $('#aboutdiv').fadeIn(1000);
        } else {
            $('#aboutdiv').fadeOut(1000);
        }
    });

    $('#aboutclose').click(function() {
        $('#aboutdiv').fadeOut(1000);
    });
});