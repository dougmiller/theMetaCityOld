$(document).ready(function() {

    function resetSearch() {
        $('.workshopentry').fadeOut(1000, function() {
            $('.workshopentry .left li').removeClass('searchMatch');
            $('#searchinput').val("");
            $('#noresults').remove();
            $('.workshopentry').fadeIn(1000);
        });
    }

    function doSearch(searchTerm) {
        var total = 0;
        $('.workshopentry, #noresults').fadeOut(1000, function() {
            $('#noresults').remove();
            $('.workshopentry .left ul li').removeClass('searchMatch');      //reset the search results
            $('.workshopentry .left ul li').each(function() {
                if ($(this).text().toLowerCase() == searchTerm.toLowerCase()) {
                    total += 1;
                    $(this).addClass('searchMatch');
                    $(this).parent().parent().parent().fadeIn(1000);
                }

                if (searchTerm == "") {
                    total = 1;
                    $('.workshopentry').fadeIn(1000);
                }
            });
            if (!total) {
                $('#main').append('<h2 id="noresults">No matches. Please try again.</h2>');
            }
        });
    }

    $('.workshopentry .left li').click(function () {
        doSearch($(this).text());
    });

    var searchTimout = null;
    $('#searchinput').bind("keypress", function() {
        clearTimeout(searchTimout);
        if ($(this).val().length) {
            searchTimout = setTimeout(function() {
                doSearch($('#searchinput').val());
            }, 800);
        }
    });

    
    if ($('#searchinput').val().length) {
        $('#searchinput').addClass('nobgimg');
        $('#searchinput').removeClass('bgimg');
    } else {
        $('#searchinput').removeClass('nobgimg');
        $('#searchinput').addClass('bgimg');
    }

    $('#searchinput').keyup(function () {
        if ($('#searchinput').val().length) {
            $('#searchinput').addClass('nobgimg');
            $('#searchinput').removeClass('bgimg');
        } else {
            $('#searchinput').removeClass('nobgimg');
            $('#searchinput').addClass('bgimg');
        }
    });

    $('#reset').click(function () {
        resetSearch();
    });
});