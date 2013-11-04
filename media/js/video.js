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
        var vidBox = this.parentNode, controlsBox, playPauseButton, progressBar, heightsTogether;
        this.controls = false;

        controlsBox = document.createElement('div');
        controlsBox.setAttribute("class", 'videoControls');
        $(controlsBox).css({opacity: 0});   // Start invisible, better here than CSS

        playPauseButton = document.createElement('img');
        playPauseButton.src = "/media/site-images/smallplay.svg";
        playPauseButton.setAttribute("class", 'playPauseButton');

        playPauseButton.addEventListener('click', (function (vid, playPauseButton) {
            return function () {
                playPause(vid, playPauseButton);
            };
        }(this, playPauseButton)));

        controlsBox.appendChild(playPauseButton);

        progressBar = document.createElement("progress");
        progressBar.max = 1;  // Without these values the progress bar gets put into 'indeterminate mode
        progressBar.value = 0;  //And that breaks things so we put them in even though they are redundant

        $(progressBar).on('click', function () {
            this.currentTime = progressBar.value;
        }, false);

        controlsBox.appendChild(progressBar);

        // When the video is hovered over, show the controls
        $(vidBox).on('mouseenter', (function (controlsBox) {
            return function () {
                $(controlsBox).fadeTo(400, 1);
                $(controlsBox).clearQueue();
            };
        }(controlsBox)));

        // When the hover leaves, hide the controls
        $(vidBox).on('mouseleave', (function (controlsBox) {
            return function () {
                $(controlsBox).fadeTo(400, 0);
                $(controlsBox).clearQueue();
            };
        }(controlsBox)));

        // If the video itself is clicked
        $(this).on('click', (function (vid, playPauseButton) {
            return function () {
                playPause(vid, playPauseButton);
            };
        }(this, playPauseButton)));

        $(this).on("reposition", (function (vidBox, controlsBox) {
            return function () {
                var vidOffset = $(vidBox).offset();
                heightsTogether = Math.floor(vidOffset.top + vidBox.height() - 4 - controlsBox.height());
                controlsBox.offset({top: heightsTogether, left: vidOffset.left});
                controlsBox.width(vidBox.width() - 2);
            };
        }($(vidBox), $(controlsBox))));

        $(this).on('timeupdate', function () {
            progressBar.value = this.currentTime / this.duration;
        }, false);

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
            $(this).trigger("reposition");
        });
    });
});
