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

    $(videos).each(function (vidIndex) {
        var vidBox = this.parentNode, controlsBox, vidOffset, playPauseButton, progressBar, heightsTogether;
        this.controls = false;

        vidOffset = $(vidBox).offset();

        controlsBox = document.createElement('div');
        controlsBox.setAttribute("class", 'videoControls');
        $(controlsBox).css({opacity: 0});   // Start invisible, better here than CSS

        heightsTogether = Math.floor(vidOffset.top + $(vidBox).height() - 4 - $(controlsBox).height());
        $(controlsBox).offset({top: heightsTogether});
        $(controlsBox).width($(vidBox).width() - 2);

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

        progressBar.addEventListener('click', function() {
            this.currentTime = progressBar.value;
        }, false);

        controlsBox.appendChild(progressBar);

        // When the video is hovered over, show the controls
        vidBox.addEventListener('mouseenter', (function (controlsBox) {
            return function () {
                $(controlsBox).fadeTo(400, 1);
                $(controlsBox).clearQueue();
            };
        }(controlsBox)));

        // When the hover leaves, hide the controls
        vidBox.addEventListener('mouseleave', (function (controlsBox) {
            return function () {
                $(controlsBox).fadeTo(400, 0);
                $(controlsBox).clearQueue();
            };
        }(controlsBox)));

        // If the video itself is clicked
        this.addEventListener('click', (function (vid, playPauseButton) {
            return function () {
                playPause(vid, playPauseButton);
            };
        }(this, playPauseButton)));

        this.addEventListener('timeupdate', function() {
            progressBar.value = this.currentTime / this.duration;
        }, false);

        // Add whe whole lot onto the page
        if (this.nextSibling) {
            this.parentNode.insertBefore(controlsBox, this.nextSibling);
        } else {
            this.parentNode.appendChild(controlsBox);
        }
    });
});
