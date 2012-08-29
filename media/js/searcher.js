$(document).ready(function () {
    "use strict";
    var noResults, searchBox, entries, searchTimeout, firstRun, loc, hist, win;
    noResults = $('#noresults');
    searchBox = $('#searchinput');
    entries = $('#entries');
    searchTimeout = null;
    firstRun = true;
    loc = location;
    hist = history;
    win = window;

    function clearFilter() {
        entries.fadeOut(150, function () {
            noResults.hide();

            $('header', this).each(function () {
                $(this).parent().hide();

                // Clear results of previous search
                $('li', this).removeClass('searchMatchTag');
                $('h1', this).removeClass('searchMatchTitle');
                $(this).closest('.workshopentry').show();
            });
            entries.fadeIn(150);
        });
    }

    function filter(searchTerm) {
        var searchPattern = new RegExp(searchTerm, 'ig');

        entries.fadeOut(150, function () {
            noResults.hide();

            $('header', this).each(function () {
                $(this).parent().hide();

                // Clear results of previous search
                $('li', this).removeClass('searchMatchTag');
                $('h1', this).removeClass('searchMatchTitle');

                // Check the title
                $('h1', this).each(function () {
                    if ($(this).text().match(searchPattern)) {
                        $(this).addClass('searchMatchTitle');
                        $(this).closest('.workshopentry').show();
                    }
                });

                // Check the tags
                $('li', this).each(function () {
                    if ($(this).text().match(searchPattern)) {
                        $(this).addClass('searchMatchTag');
                        $(this).closest('.workshopentry').show();
                    }
                });
            });

            if ($('.workshopentry[style*="block"]').length === 0) {
                noResults.show();
            }

            entries.fadeIn(150);
        });
    }

    function reset() {
        entries.fadeOut(150, function () {
            loc.pathname = '/workshop/';           // This seems a terrible way to do this but it is working for now so im not complaining
            $('header ul li', this).removeClass('searchMatchTag');
            $('header h1', this).removeClass('searchMatchTitle');
            $('.workshopentry', this).show();
            noResults.hide();
            entries.fadeIn(150);
        });
    }

    $('article.workshopentry header ul li').click(function () {
        hist.pushState(null, null, $(this).text());
        filter($(this).text());
    });

    searchBox.bind('keyup', function () {
        clearTimeout(searchTimeout);
        if ($(this).val().length) {
            searchTimeout = setTimeout(function () {
                var searchVal = searchBox.val();
                hist.pushState(null, null, searchVal);
                filter(searchVal);
            }, 500);
        }

        if ($(this).val().length === 0) {
            searchTimeout = setTimeout(function () {
                reset();
            }, 500);
        }
    });

    $('#reset').click(function () {
        searchBox.val('');
        reset();
    });

    function handlePathChange() {
        var address = loc.pathname.split('/');
        if (address[2] !== "") {
            filter(address[2]);
        } else {
            if (!firstRun) {
                clearFilter();
            }
        }
    }

    win.addEventListener("popstate", function () {
        handlePathChange();
    });

    noResults.hide();

    if (firstRun) {
        handlePathChange();
        firstRun = false;
    }

});