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
            var vid = videos[vidIndex], vidBox = vid.parentNode, controlsBox, controlsOffset, playPauseButton, muteButton;
            vid.controls = false;

            controlsOffset = $(vidBox).offset();

            controlsBox = document.createElement('div');
            controlsBox.setAttribute("class", 'videoControls');
            //controlsBox.style.left = controlsOffset.left + (parseInt(vidBox.offsetWidth/2)) - $(controlsBox).width()/2 + "px";
            //controlsBox.style.top = controlsOffset.top + vidBox.offsetHeight - 45 + "px";

            $(controlsBox).offset({ top: controlsOffset.top + $(vidBox).height - $(controlsBox).height, left: controlsOffset.left + $(vidBox).width/2});

            playPauseButton = document.createElement('img');
            playPauseButton.src = "/media/site-images/smallplay.svg";
            playPauseButton.setAttribute("class", 'playPauseButton');

            playPauseButton.addEventListener('click', (function (vid, playPauseButton) {
                return function () {
                    playPause(vid, playPauseButton);
                }
            })(vid, playPauseButton));

            controlsBox.appendChild(playPauseButton);

            muteButton = document.createElement('img');
            muteButton.src = "/media/site-images/mute.svg";
            muteButton.setAttribute("class", 'muteButton');

            muteButton.addEventListener('click', (function (vid) {
                return function () {
                    if (vid.muted === true) {
                        vid.muted = false;
                        muteButton  .src = "/media/site-images/mute.svg";
                    } else {
                        vid.muted = true;
                        muteButton.src = "/media/site-images/unmute.svg";
                    }
                }
            })(vid));

            controlsBox.appendChild(muteButton);

            // When hte video is hovered over, show the controls
            vidBox.addEventListener('mouseover', (function (controlsBox) {
                return function () {
                    $(controlsBox).fadeIn();
                }
            })(controlsBox));

            // When the hover leaves, hide the controls
            vidBox.addEventListener('mouseout', (function (controlsBox) {
                return function () {
                    $(controlsBox).fadeOut();
                }
            })(controlsBox));

            // If the video itself is clicked
            vid.addEventListener('click', (function (vid, playPauseButton) {
                return function () {
                    if (isVideoPlaying(vid))
                    playPause(vid, playPauseButton);
                }
            })(vid, playPauseButton));

            // Add whe whole lot onto the page
            if (vid.nextSibling) {
                vid.parentNode.insertBefore(controlsBox, vid.nextSibling);
            } else {
                vid.parentNode.appendChild(controlsBox);
            }
        })(i);
    }
});