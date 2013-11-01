$(document).ready(function () {
    'use strict';
    var videos = document.getElementsByTagName('video'), i, controlsTimeout;

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

    for (i = 0; i < videos.length; i += 1) {
        (function (vidIndex) {
            var vid = videos[vidIndex], vidBox = vid.parentNode, controlsBox, vidOffset, playPauseButton, muteButton, progressBar, heightsTogether;
            vid.controls = false;

            vidOffset = $(vidBox).offset();

            controlsBox = document.createElement('div');
            controlsBox.setAttribute("class", 'videoControls');
            //controlsBox.style.left = controlsOffset.left + (parseInt(vidBox.offsetWidth/2, 10)) - $(controlsBox).width()/2 + "px";
            //controlsBox.style.top = controlsOffset.top + vidBox.offsetHeight - 4 + "px";


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
            }(vid, playPauseButton)));

            controlsBox.appendChild(playPauseButton);

            progressBar = document.createElement("progress");
            progressBar.max = 1;  // Without these values the progress bar gets put into 'indeterminate mode
            progressBar.value = 0;  //And that breaks things so we put them in even though they are redundant

            progressBar.addEventListener('click', function() {
                vid.currentTime = progressBar.value;
            }, false);

            controlsBox.appendChild(progressBar);

            muteButton = document.createElement('img');
            muteButton.src = "/media/site-images/mute.svg";
            muteButton.setAttribute("class", 'muteButton');

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
            vid.addEventListener('click', (function (vid, playPauseButton) {
                return function () {
                    playPause(vid, playPauseButton);
                };
            }(vid, playPauseButton)));

            vid.addEventListener('timeupdate', function() {
                progressBar.value = vid.currentTime / vid.duration;
            }, false);

            // Add whe whole lot onto the page
            if (vid.nextSibling) {
                vid.parentNode.insertBefore(controlsBox, vid.nextSibling);
            } else {
                vid.parentNode.appendChild(controlsBox);
            }
        }(i));
    }
});
