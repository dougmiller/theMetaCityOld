$(document).ready(function() {
    $('#nojavascript').hide();

    $('#aboutdiv').hide();

    $('#abouth3').click(function() {
        if $('#aboutdiv').opacy(1000);
        $('#aboutdiv').fadeIn(1000);
    });

    $('#aboutdiv #aboutclose').click(function() {
        $('#aboutdiv').fadeOut(1000);
    });
});