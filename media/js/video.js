$(document).ready(function () {
    "use strict";
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
        var $video = $(this), $videoContainer, $controlsBox, $playPauseButton, $progressBar, $poster, customPoster, $endPoster, customEndPoster;

        if (this.controls) {
            this.controls = false;
        }

        $video.on("timeupdate", function () {
            var video = $video[0], progressBar = $progressBar[0];
            progressBar.value = video.currentTime / video.duration;
        }).on("click", function () {
            playPause($video, $playPauseButton);
        }).on("ended", function () {
            $controlsBox.css({'opacity': 0});

            // Poster to show at end of movie
            if ($video[0].dataset.endposter) {
                customEndPoster = $video[0].dataset.endposter;
            } else {
                customEndPoster = "/media/site-images/endofmovie.svg";  // If none supplied, use our own, generic one
            }
            // Get the poster and make it inline
            // File is SVG so usual jQuery rules may not apply
            // File needs to have at least one element with "playButton" as class
            $.get(customEndPoster, function (svg) {
                $endPoster = document.importNode(svg.documentElement, true);
                $endPoster = $($endPoster);

                $endPoster.attr("class", "poster endposter");
                $endPoster.attr("height", $video.height());
                $endPoster.attr("width", $video.width());

                $(".playButton", $endPoster).on("click", function () {
                    playPause($video, $playPauseButton);
                    $endPoster.remove(); // done with poster forever
                });
                $videoContainer.append($endPoster);
                $($videoContainer).trigger("reposition");
            });
        });

        // Setup the div container for the video, controls and poster
        $videoContainer = $video.wrap(
            $('<div></div>', {
                class: 'videoContainer'
            }).on("mouseenter", function () {
                $endPoster = $(".endposter", this); // This is NOT added to the whole script scope so have to rescope it here
                //   Not played yet              Finished playing
                if (!($poster.parent().length || $endPoster.parent().length)) {
                    $controlsBox.fadeTo(400, 1);
                    $controlsBox.clearQueue();
                }
            }).on("mouseleave", function () {
                $controlsBox.fadeTo(400, 0);
                $controlsBox.clearQueue();
            }).on("reposition", function () {
                // Move posters and controls back into position after video position updated
                var videoContainerOffset = $videoContainer.offset(), videoContainerWidth = $videoContainer.width(), heightsTogether = Math.floor(videoContainerOffset.top + $videoContainer.height() - $controlsBox.height());
                var $endPoster = $(".endposter", this);

                $($poster, this).offset({top: videoContainerOffset.top, left: videoContainerOffset.left});

                $endPoster.offset({top: videoContainerOffset.top, left: videoContainerOffset.left});
                $endPoster.attr("height", $video.height());
                $endPoster.attr("width", $video.width());

                $controlsBox.offset({top: heightsTogether, left: videoContainerOffset.left});
                $controlsBox.width(videoContainerWidth - 2); //2 is for borders
            })
        ).parent(); // Return the newly created wrapper div (brand new parent of the video)

        $controlsBox = $("<div></div>", {
            class: "videoControls",
            css: {
                opacity: 0
            }
        }).appendTo($videoContainer);

        // Setup play/pause button
        $playPauseButton = $("<img />", {
            class: "playPauseButton",
            src: "/media/site-images/smallplay.svg"
        }).on("click", function () {
            playPause($video, $playPauseButton);
        }).appendTo($controlsBox);


        // Setup play/pause button
        $progressBar = $("<progress />", {
            min: 0,
            max: 1,
            value: 0
        }).appendTo($controlsBox);

        // Full screen
        $("<img />", {
            class: "fullscreenButton",
            src: "/media/site-images/fullscreen.svg"
        }).on("click", function () {
                var video = $video[0];
                var videoTime = video.currentTime;
            if (video.requestFullScreen) {
                video.requestFullScreen();
            } else if (video.webkitRequestFullScreen) {
                video.webkitRequestFullScreen();
            } else if (video.mozRequestFullScreen) {
                video.mozRequestFullScreen();
            }

                $("source", $video).each(function (){
                    var $this0 = $(this)[0];

                    // .dataset.fullscreen is is treated a boolean, but it is just truthy string
                    // Thsi function uses a standard format of names of full screen appropriate vids as shown below:
                    // original: originalvid.xyz            full screen: originalvid.fullscreen.xyz
                    // N.B. Can not have period (".") in original file same except for filetype
                    if ($this0.dataset.fullscreen) {
                        var splitSrc = $this0.src.split(".");
                        console.log($this0.src);
                        $this0.src = splitSrc[0] + ".fullscreen." + splitSrc[1];
                        console.log($this0.src);
                    }
                    video.load();
                });

                $video.on("loadedmetadata", function() {
                    video.currentTime = videoTime;
                    playPause($video, $playPauseButton);
                });
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

            $(".playButton", $poster).on("click", function () {
                playPause($video, $playPauseButton);
                $poster.remove(); // done with poster forever
            });
            $videoContainer.append($poster);
            $($videoContainer).trigger("reposition");
        });

        // Add whe whole lot onto the page
        $videoContainer.append($controlsBox);

        $($videoContainer).trigger("reposition"); //Get its position right.
    });

    $(document).on("webkitfullscreenchange mozfullscreenchange fullscreenchange", function () {
        var isFullScreen = document.fullScreen || document.mozFullScreen || document.webkitIsFullScreen;
        if (!isFullScreen){
            $(videos).each(function () {
                $("source", this).each(function (){
                    var $this0 = $(this)[0];

                    // Remove the full screen and go back to the original file
                    if ($this0.dataset.fullscreen) {
                        var splitSrc = $this0.src.split(".");
                        $this0.src = splitSrc[0] + "." + splitSrc[splitSrc.length-1];
                        console.log($this0.src);
                    }
                });
                $(this)[0].load();
                $(this).parent().trigger("reposition");
            });
        }
    });

    $(window).on("resize", function () {
        $(videos).each(function () {
            $(this).parent().trigger("reposition");
        });
    });
});
