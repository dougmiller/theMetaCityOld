$(document).ready(function () {
	"use strict";
	var noResults, searchBox, entries, searchTimout;
	noResults = $('#noresults');
	searchBox = $('#searchinput');
	entries = $('#entries');
	searchTimout = null;

	noResults.hide();

	function filter(searchTerm) {
		var searchPattern = new RegExp(searchTerm, 'ig');

		entries.fadeOut(500, function () {
			noResults.hide();

			$('article.workshopentry header', this).each(function () {
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

			entries.fadeIn();
		});
	}

	function reset() {
		entries.fadeOut(300, function () {
			$('header ul li').removeClass('searchMatchTag');
			$('header h1', this).removeClass('searchMatchTitle');
			$('.workshopentry', this).show();
			noResults.hide();
			entries.fadeIn();
		});
	}

	$('article.workshopentry header ul li').click(function () {
		filter($(this).text());
	});

	searchBox.bind('keyup', function () {
		clearTimeout(searchTimout);
		if ($(this).val().length) {
			searchTimout = setTimeout(function () {
				filter(searchBox.val());
			}, 500);
		}

		if ($(this).val().length === 0) {
			searchTimout = setTimeout(function () {
				reset();
			}, 500);
		}
	});

	$('#reset').click(function () {
		searchBox.val('');
		reset();
	});
});
