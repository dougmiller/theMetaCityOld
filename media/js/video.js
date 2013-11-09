$(document).ready(function () {
    'use strict';
    var videos = $("video");

    function isVideoPlaying(video) {
        return (video.paused || video.ended || video.seeking || video.readyState < video.HAVE_FUTURE_DATA);
    }

    function playPause(video, playPauseButton) {
        if (!isVideoPlaying(video)) {
            video.pause();
            playPauseButton.src = "/media/site-images/smallplay.svg";
        } else {
            video.play();
            playPauseButton.src = "/media/site-images/smallpause.svg";
        }
    }

    function detatchOnEnterListener(vidBox) {
        $(vidBox).off('mouseenter');
    }

    $(videos).each(function () {
        var videoContainer;
        var video = $(this);
        var controlsBox;
        var playPauseButton;
        var progressBar;
        var fullscreenButton;
        var customPoster;

        this.controls = false;

        // Setup the div container for the video
        videoContainer = document.createElement('div');
        videoContainer.setAttribute("class", 'videoContainer');
        $(this).wrap(videoContainer);

        // Setup controls box for the videos
        controlsBox = document.createElement('div');
        controlsBox.setAttribute("class", 'videoControls');
        $(controlsBox).css({opacity: 0});   // Start invisible, better here than CSS

        // Setup play/pause button
        playPauseButton = document.createElement('img');
        playPauseButton.src = "/media/site-images/smallplay.svg";
        playPauseButton.setAttribute("class", 'playPauseButton');

        $(playPauseButton).on('click', (function (vid, playPauseButton) {
            return function () {
                playPause(vid, playPauseButton);
            };
        }(this, playPauseButton)));

        controlsBox.appendChild(playPauseButton);

        // Setup the progress display
        progressBar = document.createElement("progress");
        progressBar.max = 1;  // Without these values the progress bar gets put into 'indeterminate mode
        progressBar.value = 0;  //And that breaks things so we put them in even though they are redundant

        $(progressBar).on('click', function () {
            return function () {
                this.currentTime = progressBar.value;
            };
        });

        controlsBox.appendChild(progressBar);

        // Setup full screen button
        fullscreenButton = document.createElement('img');
        fullscreenButton.src = "/media/site-images/fullscreen.svg";
        fullscreenButton.setAttribute("class", 'fullscreenButton');

        $(fullscreenButton).on('click', (function (video) {
            return function () {
                if (video.requestFullScreen) {
                    video.requestFullScreen();
                } else if (video.webkitRequestFullScreen) {
                    video.webkitRequestFullScreen();
                } else if (video.mozRequestFullScreen) {
                    video.mozRequestFullScreen();
                }
            };
        }(this)));

        controlsBox.appendChild(fullscreenButton);

        $(videoContainer).on('mouseenter', (function (controlsBox) {
            return function () {
                $(controlsBox).fadeTo(400, 1);
                $(controlsBox).clearQueue();
            };
        }(controlsBox)));

        // When the hover leaves, hide the controls
        $(videoContainer).on('mouseleave', (function (controlsBox) {
            return function () {
                $(controlsBox).fadeTo(400, 0);
                $(controlsBox).clearQueue();
            };
        }(controlsBox)));

        // If the video itself is clicked, play/pause the video
        $(this).on('click', (function (vid, playPauseButton) {
            return function () {
                playPause(vid, playPauseButton);
            };
        }(this, playPauseButton)));
        /*
        // Posters to show before the user plays the video
        customPoster = this.dataset.poster;
        if (!customPoster) {
            customPoster = "/media/site-images/genericposter.svg";  // If none supplied, use our own, generic one
        }

        // Get the poster and make it inline
        $.get(customPoster, function (svg) {
            var poster = document.importNode(svg.documentElement, true);

            $(poster).attr("class", "poster");
            $(poster).attr("height",  video.height());
            $(poster).attr("width",  video.width());

            $(poster).on('click', (function (video, playPauseButton, controlsBox) {
                return function () {
                    $(this).remove(); // done with poster forever
                    playPause(video, playPauseButton);
                };
            }(video, playPauseButton, controlsBox)));

            videoContainer.appendChild(poster);
        });
         */
        $(".videoContainer").on("reposition", (function () {
            return function () {
                var videoContainer = $(this);
                var videoContainerOffset = $(this).offset();
                var videoOffset = $("video", this).offset();
                var heightsTogether = Math.floor(videoContainerOffset.top + videoContainer.height - 4 - controlsBox.height);
                var poster = $("svg", this);

                $(controlsBox).offset({top: heightsTogether, left: videoContainerOffset.left});
                $(controlsBox).width(videoContainer.width - 2);
                $(poster).offset({top: videoOffset.top, left: videoOffset.left});
            };
        }()));

        $(this).on('timeupdate', (function (video) {
            return function () {
                progressBar.value = video.currentTime / video.duration;
            };
        }(this)));

        // Add whe whole lot onto the page
        if (this.nextSibling) {
            this.parentNode.insertBefore(controlsBox, this.nextSibling);
        } else {
            this.parentNode.appendChild(controlsBox);
        }
        $(".videoContainer").trigger("reposition"); // Set initial positions
    });

    $(window).on("resize", function () {
        $(".videoContainer").each(function () {
            $(this).trigger("reposition");  // Draw everything on for the first time
        });
    });
});
