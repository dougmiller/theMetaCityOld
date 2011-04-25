$(document).ready(function() {

	var searchBox = $('#searchinput');
	var workshop = $('.workshopentry');
	var workshopli = $('.workshopentry .left li');

	var reset = $('#reset');

    function resetSearch() {
		var noresult = $('#noresults');

        workshop.fadeOut(1000, function() {
            workshopli.removeClass('searchMatch');

			searchBox.val("");

			if (noresult){
				noresult.fadeOut(1000, function(){
					noresult.remove();				
				});
			}
        });
        
		workshop.fadeIn(1000);
    }

    function doSearch(searchTerm) {
		var toFadeIn = [];
		
		var noresult = $('#noresults');	
		if (noresult) {noresult.remove();}

		workshop.fadeOut(1000, function() {
            var tags = $('.left ul li', this);
            tags.removeClass('searchMatch');      //reset the search results
            tags.each(function() {                
				if ($(this).text().toLowerCase() === searchTerm.toLowerCase()) {
                    $(this).addClass('searchMatch');
                    toFadeIn.push($(this).parent().parent().parent());
                }
			});

			if (searchTerm === "") {
				toFadeIn.push(workshop);
			}

			if (toFadeIn.length < 1) {		//No results found
			var main = $('#main');
				main.append('<h2 id="noresults">No matches. Please try again.</h2>');
			}
		});

		$(toFadeIn).each(function(index, div){
			div.fadeIn(1000);		
		});    
	}	


	// Run the search when clicking a tag
	workshopli.click(function () {
		doSearch($(this).text());
	});    

	// Also run the search after the timeout period of people not pressing anything in the search box
	var searchTimout = null;
	searchBox.bind("keypress", function() {
		clearTimeout(searchTimout);
		if ($(this).val().length) {
			searchTimout = setTimeout(function() {
				doSearch(searchBox.val());
			}, 800);
		}
	});

	reset.click(function () {
		resetSearch();
	});
	
});
