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

    $(videos).each(function () {
        var vidBox = this.parentNode, controlsBox, playPauseButton, progressBar, fullscreenButton, customPoster, poster, playedYet = false;
        this.controls = false;

        // Setup controls for the videos
        controlsBox = document.createElement('div');
        controlsBox.setAttribute("class", 'videoControls');
        $(controlsBox).css({opacity: 0});   // Start invisible, better here than CSS

        playPauseButton = document.createElement('img');
        playPauseButton.src = "/media/site-images/smallplay.svg";
        playPauseButton.setAttribute("class", 'playPauseButton');

        $(playPauseButton).on('click', (function (vid, playPauseButton) {
            return function () {
                if (!playedYet) {
                    playedYet = true;
                }
                playPause(vid, playPauseButton);
            };
        }(this, playPauseButton)));

        controlsBox.appendChild(playPauseButton);

        progressBar = document.createElement("progress");
        progressBar.max = 1;  // Without these values the progress bar gets put into 'indeterminate mode
        progressBar.value = 0;  //And that breaks things so we put them in even though they are redundant

        $(progressBar).on('click', function () {
            return function () {
                this.currentTime = progressBar.value;
            };
        });

        controlsBox.appendChild(progressBar);

        // Button that adds full screen functionality
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

        // When the video is hovered over, show the controls
        $(vidBox).on('mouseenter', (function (controlsBox) {
            return function () {
                if (playedYet){
                    $(controlsBox).fadeTo(400, 1);
                    $(controlsBox).clearQueue();
                }
            };
        }(controlsBox)));

        // When the hover leaves, hide the controls
        $(vidBox).on('mouseleave', (function (controlsBox) {
            return function () {
                $(controlsBox).fadeTo(400, 0);
                $(controlsBox).clearQueue();
            };
        }(controlsBox)));

        // If the video itself is clicked, play/pause the video
        $(this).on('click', (function (vid, playPauseButton) {
            return function () {
                if (!playedYet) {
                    playedYet = true;
                }
                playPause(vid, playPauseButton);
            };
        }(this, playPauseButton)));

        // Posters to show before the user plays the video
        customPoster = this.dataset.poster;
        if (customPoster) {
            var vidOffsettt = $(this).offset();
            poster = document.createElement('img');
            poster.setAttribute("src",customPoster);
            $(poster).addClass("poster");
            $(poster).height = $(this).height();
            $(poster).width = $(this).width();
            $(poster).offset({top: vidOffsettt.top, left: vidOffsettt.left});

            $(poster).on('click', (function (vid, playPauseButton) {
                return function () {
                    vid.parentNode.removeChild(poster);
                    playedYet = true;
                    playPause(vid, playPauseButton);
                };
            }(this, playPauseButton)));

            this.parentNode.appendChild(poster);
        }

        $(this).on("reposition", (function (vidBox, controlsBox, poster) {
            return function () {
                var vidBoxOffset = $(vidBox).offset(), vidOffset = $(this).offset(), heightsTogether = Math.floor(vidBoxOffset.top + vidBox.height() - 4 - controlsBox.height());
                controlsBox.offset({top: heightsTogether, left: vidBoxOffset.left});
                controlsBox.width(vidBox.width() - 2);
                poster.offset({top: vidOffset.top, left: vidOffset.left});
            };
        }($(vidBox), $(controlsBox), $(poster))));

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
        $(this).trigger("reposition"); //Get its position right.
    });

    $(window).on("resize", function () {
        $(videos).each(function () {
            $(this).trigger("reposition");  // Draw everything on for the first time
        });
    });
});
