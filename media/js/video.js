$(document).ready(function () {
    "use strict";
    var c = console.log;
    var videos = $("video");

    function isVideoPlaying(video) {
        return !(video.paused || video.ended || video.seeking || video.readyState < video.HAVE_FUTURE_DATA);
    }

    // Pass in Jquery objects of the video to play/pause and the control box associated with it
    function playPause(video, playPauseButton) {
        var vid = video[0];
        if (isVideoPlaying(vid)) {
            vid.pause();
            playPauseButton[0].src = "/media/site-images/smallplay.svg";
        } else {
            vid.play();
            playPauseButton[0].src = "/media/site-images/smallpause.svg";
        }
    }

    $(videos).each(function () {
        var $video = $(this);
        var $videoContainer;
        var $controlsBox;
        var $playPauseButton;
        var $progressBar;
        var $poster;
        var customPoster;

        this.controls = false;

        $video.on("timeupdate",function () {
            var video = $video[0], progressBar = $progressBar[0];
            progressBar.value = video.currentTime / video.duration;
        }).on("click", function () {
                playPause($video, $playPauseButton);
            });

        // Setup the div container for the video, controls and poster
        $videoContainer = $video.wrap(
            $('<div></div>', {
                class: 'videoContainer'
            }).on("mouseenter",function () {
                    c($poster.parent().length);
                    $controlsBox.fadeTo(400, 1);
                    $controlsBox.clearQueue();
                }).on("mouseleave",function () {
                    $controlsBox.fadeTo(400, 0);
                    $controlsBox.clearQueue();
                }).on("reposition", function () {
                    c("triggered");
                    // Move posters and controls back into position after video position updated
                    var videoContainerOffset = $videoContainer.offset();
                    var heightsTogether = Math.floor($controlsBox.offset().top + $videoContainer.height - 4 - $controlsBox.height);
                    var videoOffset = $video.offset();

                    $controlsBox.offset({top: heightsTogether, left: videoContainerOffset.left});
                    $controlsBox.width($videoContainer.width - 2); //2 is for borders
                    $poster.offset({top: videoOffset.top, left: videoOffset.left});
                })).parent(); // Return the newly created wrapper div (brand new parent of the video)

        $controlsBox = $("<div></div>", {
                class: "videoControls",
                css: {
                    opacity: 0
                }
            }
        ).appendTo($videoContainer);

        // Setup play/pause button
        $playPauseButton = $("<img />", {
            class: "playPauseButton",
            src: "/media/site-images/smallplay.svg"
        }).on("click",function () {
                playPause($video, $playPauseButton);
            }).appendTo($controlsBox);


        // Setup play/pause button
        $progressBar = $("<progress />", {
                min: 0,
                max: 1,
                value: 0
            }
        ).appendTo($controlsBox);

        // Full screen
        $("<img />", {
            class: "fullscreenButton",
            src: "/media/site-images/fullscreen.svg"
        }).on("click",function () {
                var video = $video.get(0);
                if (video.requestFullScreen) {
                    video.requestFullScreen();
                } else if (video.webkitRequestFullScreen) {
                    video.webkitRequestFullScreen();
                } else if (video.mozRequestFullScreen) {
                    video.mozRequestFullScreen();
                }
            }).appendTo($controlsBox);


        // Posters to show before the user plays the video
        customPoster = this.dataset.poster;
        if (!customPoster) {
            customPoster = "/media/site-images/genericposter.svg";  // If none supplied, use our own, generic one
        }
        // Get the poster and make it inline
        // File is SVG so usual jQuery rules may not apply
        // File needs to have at least one element with "playButton" as class
        $.get(customPoster, function (svg) {
            $poster = document.importNode(svg.documentElement, true);
            $poster = $($poster);

            $poster.attr("class", "poster");
            $poster.attr("height", $video.height());
            $poster.attr("width", $video.width());

            $poster.offset({top: $video.offset().top, left: $video.offset().left});

            $(".playButton", $poster).on("click", function () {
                        playPause($video, $playPauseButton);
                        $poster.remove(); // done with poster forever
            });
            $videoContainer.append($poster);
        });

        // Add whe whole lot onto the page
        $videoContainer.append($controlsBox);

    });

    $(window).on("resize", function () {
        c("trig");
        $().trigger("reposition");  // Draw everything on for the first time
    });

    $().trigger("reposition"); // Set initial positions
})
;
