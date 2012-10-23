$(document).ready(function () {
    'use strict';
    var videos = document.getElementsByTagName('video'), i;

    function playPause(video, playPauseButton) {
        if (!(video.paused || video.ended || video.seeking || video.readyState < video.HAVE_FUTURE_DATA)) {
            video.pause();
            playPauseButton.innerHTML = "Play";
        } else {
            video.play();
            playPauseButton.innerHTML = "Pause";
        }
    }


    for (i = 0; i < videos.length; i += 1) {
        (function (vidIndex) {

            var vid = videos[vidIndex], controlsBox, playPauseButton, muteButton;
            vid.controls = false;

            controlsBox = document.createElement('div');
            controlsBox.setAttribute("class", 'videoControls');

            playPauseButton = document.createElement('button');
            playPauseButton.type = "Button";
            playPauseButton.setAttribute("class", 'playPauseButton');
            playPauseButton.innerHTML = "Play";

            playPauseButton.addEventListener('click', (function (vid, playPauseButton) {
                return function () {
                    playPause(vid, playPauseButton);
                }
            })(vid, playPauseButton));

            controlsBox.appendChild(playPauseButton);

            muteButton = document.createElement('button');
            muteButton.type = "Button";
            muteButton.setAttribute("class", 'muteButton')
            muteButton.innerHTML = "Mute";

            muteButton.addEventListener('click', (function (vid) {
                return function () {
                    vid.muted = !vid.muted;
                }
            })(vid));

            controlsBox.appendChild(muteButton);

            // If the video itself is clicked
            vid.addEventListener('click', (function (vid) {
                return function () {
                    playPause(vid);
                }
            })(vid));

            // Add whe whole lot onto the page
            if (vid.nextSibling) {
                vid.parentNode.insertBefore(controlsBox, vid.nextSibling);
            } else {
                vid.parentNode.appendChild(controlsBox);
            }

        })(i);
    }
});