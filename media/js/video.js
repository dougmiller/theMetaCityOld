$(document).ready(function () {
    "use strict";
    var videos = $("video"), doc = document, fsElement;

    Number.prototype.leftZeroPad = function (numZeros) {
        var n = Math.abs(this),
            zeros = Math.max(0, numZeros - Math.floor(n).toString().length),
            zeroString = Math.pow(10, zeros).toString().substr(1);
        if (this < 0) {
            zeroString = '-' + zeroString;
        }
        return zeroString + n;
    };

    function isVideoPlaying(video) {
        return !(video.paused || video.ended || video.seeking || video.readyState < video.HAVE_FUTURE_DATA);
    }

    // Pass in object of the video to play/pause and the control box associated with it
    function playPause(video) {
        var playPauseButton = $(".playPauseButton", video.parent)[0];
        if (isVideoPlaying(video)) {
            video.pause();
            playPauseButton.src = "/media/site-images/smallplay.svg";
        } else {
            video.play();
            playPauseButton.src = "/media/site-images/smallpause.svg";
        }
    }

    function rawTimeToFormattedTime(rawTime) {
        var chomped, seconds, minutes;
        chomped = Math.floor(rawTime);
        seconds = chomped % 60;
        minutes = Math.floor(chomped / 60);
        return minutes.leftZeroPad(2) + ":" + seconds.leftZeroPad(2);
    }

    $(videos).each(function () {
        var video = this, $videoContainer, $controlsBox, $playPauseButton, $progressBar, $poster, customPoster, $endPoster, customEndPoster, errorPoster, $currentTimeSpan, $durationTimeSpan;

        if (this.controls) {
            this.controls = false;
        }

        $(video).on("timeupdate",function () {
            $progressBar[0].value = (video.currentTime / video.duration) * 1000;
            $currentTimeSpan.text(rawTimeToFormattedTime(video.currentTime));
        }).on("click",function () {
                playPause(video);
            }).on("ended",function () {
                $controlsBox.css({'opacity': 0});

                // Poster to show at end of movie
                if (video.dataset.endposter) {
                    customEndPoster = video.dataset.endposter;
                } else {
                    customEndPoster = "/media/site-images/endofmovie.svg";  // If none supplied, use our own, generic one
                }
                // Get the poster and make it inline
                // File is SVG so usual jQuery rules may not apply
                // File needs to have at least one element with "playButton" as class
                $.get(customEndPoster, function (svg) {
                    $endPoster = doc.importNode(svg.documentElement, true);
                    $endPoster = $($endPoster);

                    $endPoster.attr("class", "poster endposter");
                    $endPoster.attr("height", $(video).height());
                    $endPoster.attr("width", $(video).width());

                    $(".playButton", $endPoster).on("click", function () {
                        playPause(video);
                        $endPoster.remove(); // done with poster forever
                    });
                    $videoContainer.append($endPoster);
                    $($videoContainer).trigger("reposition");
                });
            }).on("play", function () {
                var canPlayVid = false;
                $("source", $(video)).each(function () {
                    if (video.canPlayType($(this).attr("type"))) {
                        canPlayVid = true;
                    }
                });
                if (!canPlayVid) {
                    errorPoster = "/media/site-images/movieerror.svg";
                    $.get(errorPoster, function (svg) {
                        errorPoster = doc.importNode(svg.documentElement, true);

                        $(errorPoster).attr("class", "poster errorposter");
                        $(errorPoster).attr("height", $(video).height());
                        $(errorPoster).attr("width", $(video).width());

                        $("source", $(video)).each(function () {
                            var newText = doc.createElementNS("http://www.w3.org/2000/svg", "tspan");
                            var link = doc.createElementNS("http://www.w3.org/2000/svg", "a");
                            newText.setAttributeNS(null, "x", "50%");
                            newText.setAttributeNS(null, "dy", "1.2em");
                            link.setAttributeNS("http://www.w3.org/1999/xlink", "href", this.src);
                            link.appendChild(doc.createTextNode(this.src));
                            newText.appendChild(link);

                            $("#sorrytext", errorPoster).append(newText);
                        });

                        $videoContainer.append(errorPoster);
                        $($videoContainer).trigger("reposition");
                    });
                } else {
                    $($currentTimeSpan).text(rawTimeToFormattedTime(this.currentTime));
                    $($durationTimeSpan).text(rawTimeToFormattedTime(this.duration));
                }
            });

        // Setup the div container for the video, controls and poster
        $videoContainer = $(video).wrap(
            $('<div></div>', {
                class: 'videoContainer'
            }).on("mouseenter",function () {
                    $endPoster = $(".endposter", this); // This is NOT added to the whole script scope so have to rescope it here
                    errorPoster = $(".errorposter", this); // This is NOT added to the whole script scope so have to rescope it here
                    //   Not played yet              Finished playing              Cant play format
                    if ($poster.parent().length || $endPoster.parent().length || errorPoster.parent().length) {
                        $controlsBox.css({'opacity': 0});
                    } else {
                        $controlsBox.fadeTo(400, 1);
                        $controlsBox.clearQueue();
                    }
                }).on("mouseleave",function () {
                    $controlsBox.fadeTo(400, 0);
                    $controlsBox.clearQueue();
                }).on("reposition", function () {
                    // Move posters and controls back into position after video position updated
                    var videoContainerOffset = $videoContainer.offset(),
                        videoContainerWidth = $videoContainer.width(),
                        heightsTogether = Math.floor(videoContainerOffset.top + $videoContainer.height() - $controlsBox.height()),
                        $endPoster = $(".endposter", this),
                        $errorPoster = $(".errorposter", this);

                    $($poster, this).offset({top: videoContainerOffset.top, left: videoContainerOffset.left});

                    $endPoster.offset({top: videoContainerOffset.top, left: videoContainerOffset.left});
                    $endPoster.attr("height", $(video).height());
                    $endPoster.attr("width", $(video).width());

                    $errorPoster.offset({top: videoContainerOffset.top, left: videoContainerOffset.left});

                    $controlsBox.offset({top: heightsTogether, left: videoContainerOffset.left});
                    $controlsBox.width(videoContainerWidth - 2); // 2 is for borders
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
        }).on("click",function () {
                playPause(video);
            }).appendTo($controlsBox);


        $durationTimeSpan = $("<span></span>", {
            class: "timespan"
        }).appendTo($controlsBox);

        // Setup progress bar
        $progressBar = $("<input />", {
            type: "range",
            min: 0,
            max: 1000,
            value: 0
        }).on("change",function () {
                video.currentTime = video.duration * (this.value / 1000);
            }).on("mousedown",function () {
                video.pause();
            }).on("mouseup",function () {
                video.play();
            }).appendTo($controlsBox);

        $currentTimeSpan = $("<span></span>", {
            class: "timespan currenttimespan"
        }).appendTo($controlsBox);

        // Full screen
        $("<img />", {
            class: "fullscreenButton",
            src: "/media/site-images/fullscreen.svg"
        }).on("click",function () {
                fsElement = video;
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
            $poster = doc.importNode(svg.documentElement, true);
            $poster = $($poster);

            $poster.attr("class", "poster");
            $poster.attr("height", $(video).height());
            $poster.attr("width", $(video).width());

            $(".playButton", $poster).on("click", function () {
                playPause(video);
                $poster.remove(); // done with poster forever
            });
            $videoContainer.append($poster);
            $($videoContainer).trigger("reposition");
        });

        // Add whe whole lot onto the page
        $videoContainer.append($controlsBox);

        $($videoContainer).trigger("reposition"); //Get its position right.
    });

    // Handle coming out of fullscreen
    $(doc).on("webkitfullscreenchange mozfullscreenchange fullscreenchange", function () {
        var isFullScreen = doc.fullScreen || doc.mozFullScreen || doc.webkitIsFullScreen;

        $(fsElement).each(function () {  // set to script scope as fullScreenElement appears to not work (yet?)
            var video = this, videoTime = video.currentTime;
            if (isFullScreen) {
                $("source", video).each(function () {
                    // .dataset.fullscreen is is treated a boolean, but it is just truthy string
                    // This function uses a standard format of names of full screen appropriate vids as shown below:
                    // original: originalvid.xyz            full screen: originalvid.fullscreen.xyz
                    // N.B. Can not have period (".") in original file same except for filetype
                    if (this.dataset.fullscreen) {
                        var splitSrc = this.src.split(".");
                        this.src = splitSrc[0] + "." + splitSrc[1] + "." + splitSrc[2] + ".fullscreen." + splitSrc[3];
                    }
                    video.load();
                });
            } else {  // Have left fullscreen and need to return to lower res video
                $("source", video).each(function () {
                        // Remove the full screen and go back to the original file
                        if (this.dataset.fullscreen) {
                            var splitSrc = this.src.split(".");
                            console.log(splitSrc);
                            this.src = splitSrc[0] + "." + splitSrc[1] + "." + splitSrc[2] + "." + splitSrc[4];
                        } // Nothing was changed if data-fullscreen is false so no need to do anything

                    $(this).parent().load();  // The video
                    $(this).parent().trigger("reposition");  // The video container box
                });
            }
            $(video).on("loadedmetadata", function () {
                this.currentTime = videoTime;  // Skip to the time before we went full screen
                playPause(video);
            });
        });
    });
    $(window).on("resize", function () {
        $(videos).each(function () {
            $(this).parent().trigger("reposition");
        });
    });
});