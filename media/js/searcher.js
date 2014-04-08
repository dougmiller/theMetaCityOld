$(document).ready(function () {
    "use strict";
    var $noResults, $searchBox, $entries, searchTimeout, firstRun, loc, hist, win;
    $noResults = $('#noresults');
    $searchBox = $('#searchinput');
    $entries = $('#workshopBlurbEntries');
    searchTimeout = null;
    firstRun = true;
    loc = location;
    hist = history;
    win = window;

    function reset() {
        if (hist.state !== undefined) {  // Avoid infinite loops
            hist.pushState({"tag": undefined}, "theMetaCity - Workshop", "/workshop/");
        }
        $noResults.hide();
        $entries.fadeOut(150, function () {
            $('header ul li', this).removeClass('searchMatchTag');
            $('header h1 a span', this).removeClass('searchMatchTitle');  // The span remains but it is destroyed when filtering using the text() function
            $(".workshopentry", this).show();
        });
        $entries.fadeIn(150);
    }

    function filter(searchTerm) {
        if (searchTerm === undefined) {  // Only history api should push undefined to this, explicitly taken care of otherwise
            reset();
        } else {
            var rePattern = searchTerm.replace(/[.?*+^$\[\]\\(){}|]/g, "\\$&"), searchPattern = new RegExp('(' + rePattern + ')', 'ig');  // The brackets add a capture group

            $entries.fadeOut(150, function () {
                $noResults.hide();

                $('header', this).each(function () {
                    $(this).parent().hide();

                    // Clear results of previous search
                    $('li', this).removeClass('searchMatchTag');

                    // Check the title
                    $('h1', this).each(function () {
                        var textToCheck = $('a', this).text();
                        if (textToCheck.match(searchPattern)) {
                            textToCheck = textToCheck.replace(searchPattern, '<span class="searchMatchTitle">$1</span>');  //capture group ($1) used so that the replacement matches the case and you don't get weird capitolisations
                            $('a', this).html(textToCheck);
                            $(this).closest('.workshopentry').show();
                        } else {
                            $('a', this).html(textToCheck);
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
                    $noResults.show();
                }

                $entries.fadeIn(150);
            });
        }
    }

    $('header ul li a', $entries).on('click', function () {
        hist.pushState({"tag": $(this).text()}, "theMetaCity - Workshop - " + $(this).text(), "/workshop/tag/" + $(this).text());
        $searchBox.val('');
        filter($(this).text());
        return false;  // Using the history API so no page reloads/changes
    });

    $searchBox.on('keyup', function () {
        clearTimeout(searchTimeout);
        if ($(this).val().length) {
            searchTimeout = setTimeout(function () {
                var searchVal = $searchBox.val();
                hist.pushState({"tag": searchVal}, "theMetaCity - Workshop - " + searchVal, "/workshop/tag/" + searchVal);
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
        $searchBox.val('');
        reset();
    });

    win.addEventListener("popstate", function (event) {
        console.info(hist.state);
        if (event.state === null) { // Start of history chain on this page, direct entry to page handled by firstRun)
            reset();
        } else {
            if (event.state.tag !== undefined) {
                $searchBox.val(event.state.tag);
                filter(event.state.tag);
            }
        }
    });

    $noResults.hide();

    if (firstRun) {                               // 0     1     2        3      4 (if / present)
        var locArray = loc.pathname.split('/');   // '/workshop/tag/searchString/
        if (locArray[2] === 'tag' && locArray[3] !== undefined) {    // Check for direct link to tag (i.e. if something in [3] search for it)
            hist.pushState({"tag": locArray[3]}, "theMetaCity - Workshop - " + locArray[3], "/workshop/tag/" + locArray[3]);
            filter(locArray[3]);
        } else if (locArray[2] === '') {   // Root page and really shouldn't do anything
            //hist.pushState({"tag": undefined}, "theMetaCity - Workshop", "/workshop/");
        }   // locArray[2] === somepagenum is an actual page and what should be allowed to happen by itself

        firstRun = false;
        // Save state on first page load
    }
});