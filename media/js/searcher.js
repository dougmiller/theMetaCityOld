$(document).ready(function () {
    "use strict";
    var noResults, searchBox, entries, searchTimeout, firstRun, loc, hist, win;
    noResults = $('#noresults');
    searchBox = $('#searchinput');
    entries = $('#workshopBlurbEntries');
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
        var searchPattern = new RegExp('(' + searchTerm + ')', 'ig');  // The brackets add a capture group

        entries.fadeOut(150, function () {
            noResults.hide();

            $('header', this).each(function () {
                $(this).parent().hide();

                // Clear results of previous search
                $('li', this).removeClass('searchMatchTag');

                // Check the title
                $('h1', this).each(function () {
                    var textToCheck = $(this).text();
                    if (textToCheck.match(searchPattern)) {
                        textToCheck = textToCheck.replace(searchPattern, '<span class="searchMatchTitle">$1</span>');  //capture group ($1) used so that the replacement matches the case and you don't get weird capitolisations
                        $(this).html(textToCheck);
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

    $('header ul li a', entries).on('click', function () {
        hist.pushState(null, null, $(this).text());
        filter($(this).text());
        return false;
    });

    searchBox.on('keyup', function () {
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

    $('#reset').on('click', function () {
        searchBox.val('');
        reset();
    });

    function handlePathChange() {
        var address = loc.pathname.split('/'), searchString = address[2];
        if (searchString !== "") {
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