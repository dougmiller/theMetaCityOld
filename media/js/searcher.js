$(document).ready(function() {

	$('#noresults').hide();

	function filter(tag) {
		tag = tag.toLowerCase();

		$('#entries').fadeOut(500, function() {
			$('#noresults').hide();

			$('.workshopentry .left', this).each(function() {
				$(this).parent().hide();
				$('li', this).removeClass('searchMatch');
		
				var tags = [];
				$('li', this).each(function() {
					if ($(this).text().toLowerCase() === tag) {
						$(this).closest('.workshopentry').show();
						$(this).addClass('searchMatch');
					}
				});
			});

			if ($('.workshopentry[style*="block"]', this).length === 0) {
				$('#noresults').show();
			}

			$('#entries').fadeIn();
		});
	}

	$('.workshopentry .left ul li').click(function () {
		filter($(this).text());
	});

	var searchTimout = null;
	$('#searchinput').bind('keypress', function() {
		clearTimeout(searchTimout);
		if ($(this).val().length) {
			searchTimout = setTimeout(function() {
				filter($('#searchinput').val());
			}, 500);
		}
	});

	$('#reset').click(function() {
		$('#searchinput').val('');
		$('#entries').fadeOut(500, function() {
			$('.left ul li').removeClass('searchMatch');
			$('.workshopentry', this).show();
			$('#noresults').hide();
			$('#entries').fadeIn();
		});
	});
});
