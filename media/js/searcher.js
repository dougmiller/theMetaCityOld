$(document).ready(function(){

	var noResults = $('#noresults');
	var searchBox = $('#searchinput');
	var entries = $('#entries');

	noResults.hide();

	function filter(searchTerm) {
		var searchPattern = new RegExp(searchTerm, 'ig');

		entries.fadeOut(500, function() {
			noResults.hide();

			$('.workshopentry .left', this).each(function() {
				$(this).parent().hide();

				// Clear results of previous search
				$('li', this).removeClass('searchMatchTag');
				$('h3', this).removeClass('searchMatchTitle');

				// Cheack the title
				$('h3', this).each(function() {
					if ($(this).text().match(searchPattern)) {
						$(this).closest('.workshopentry').show();
						$(this).addClass('searchMatchTitle');
					}
				});

				// Check the tags
				$('li', this).each(function() {
					if ($(this).text().match(searchPattern)) {
						$(this).closest('.workshopentry').show();
						$(this).addClass('searchMatchTag');
					}
				});
			});

			if ($('.workshopentry[style*="block"]').length === 0) {
				noResults.show();
			}

			entries.fadeIn();
		});
	}

	function reset() {
		entries.fadeOut(500, function() {
			$('.left ul li').removeClass('searchMatchTag');
			$('.left h3').removeClass('searchMatchTitle');
			$('.workshopentry', this).show();
			noResults.hide();
			entries.fadeIn();
		});
	}

	$('.workshopentry .left ul li').click(function () {
		filter($(this).text());
	});

	var searchTimout = null;
	searchBox.bind('keyup', function() {
		clearTimeout(searchTimout);
		if ($(this).val().length) {
			searchTimout = setTimeout(function() {
				filter(searchBox.val());
			}, 500);
		}

		if ($(this).val().length === 0) {
			searchTimout = setTimeout(function() {
				reset();
			}, 500);
		}

	});

	$('#reset').click(function() {
		searchBox.val('');
		reset();
	});
});
