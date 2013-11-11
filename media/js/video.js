$(document).ready(function () {
    "use strict";
    var c = console;
    var videos = $("video");

    function isVideoPlaying(video) {
        return (video.paused || video.ended || video.seeking || video.readyState < video.HAVE_FUTURE_DATA);
    }

    function playPause(video, playPauseButton) {
        if (!isVideoPlaying(video)) {
            video.pause();
            playPauseButton[0].src = "/media/site-images/smallplay.svg";
        } else {
            video.play();
            playPauseButton[0].src = "/media/site-images/smallpause.svg";
        }
    }

    function detatchOnEnterListener(vidBox) {
        $(vidBox).off("mouseenter");
    }

    $(videos).each(function () {
        var $video = this;
        var $videoContainer;
        var $controlsBox;
        var $playPauseButton;
        var $progressBar;
        var customPoster;

        this.controls = false;

        $($video, {
            on: {
                timeupdate: function () {
                    $progressBar[0].value = $video.currentTime / $video.duration;
                },
                click: function () {
                    playPause($video, $playPauseButton);
                }
            }
        });

        // Setup the div container for the video, controls and poster
        $videoContainer = $($video).wrap(
            $('<div></div>', {
                class: 'videoContainer',
                on: {
                    click: function () {
                        c.log(this);
                        c.log($progressBar[0].value);
                        c.log($video.currentTime);
                        c.log($video.duration);
                        c.log($video.currentTime / $video.duration);
                        playPause($video, $playPauseButton);
                    },
                    mouseenter: function () {
                        $controlsBox.fadeTo(400, 1);
                        $controlsBox.clearQueue();
                    },
                    mouseleave: function () {
                        $controlsBox.fadeTo(400, 0);
                        $controlsBox.clearQueue();
                    },
                    reposition: function () {
                        var $videoContainer = $(this);
                        var videoContainerOffset = $(this).offset();
                        console.log($(this).offset());
                        //var videoOffset = $("video", this).offset();
                        var heightsTogether = Math.floor($controlsBox.offset().top + $videoContainer.height - 4 - $controlsBox.height);
                        //var poster = $("svg", this);

                        $controlsBox.offset({top: heightsTogether, left: videoContainerOffset.left});
                        $controlsBox.width(videoContainer.width - 2);
                        //$(poster).offset({top: videoOffset.top, left: videoOffset.left});
                    }
                }
            })
        ).parent(); // Return the newly created wrapper div (brand new parent of the video)

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
            src: "/media/site-images/smallplay.svg",
            on: {
                click: function () {
                    playPause($video, $playPauseButton);
                }
            }
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
            src: "/media/site-images/fullscreen.svg",
            on: {
                click: function () {
                    if ($video.requestFullScreen) {
                        $video.requestFullScreen();
                    } else if ($video.webkitRequestFullScreen) {
                        $video.webkitRequestFullScreen();
                    } else if ($video.mozRequestFullScreen) {
                        $video.mozRequestFullScreen();
                    }
                }
            }
        }).appendTo($controlsBox);


         // Posters to show before the user plays the video
         customPoster = this.dataset.poster;
         if (!customPoster) {
            customPoster = "/media/site-images/genericposter.svg";  // If none supplied, use our own, generic one
         }

         // Get the poster and make it inline
        // File is SVG so usual jQuery rules may not apply
         $.get(customPoster, function (svg) {
             var poster = document.importNode(svg.documentElement, true);

             $(poster).attr("class", "poster");
             $(poster).attr("height",  $video.height);
             $(poster).attr("width",  $video.width);

             $(poster).on("click", (function (video, playPauseButton, controlsBox) {
             return function () {
             $(this).remove(); // done with poster forever
             playPause(video, playPauseButton);
             };
             }($video, $playPauseButton, $controlsBox)));

             $videoContainer.append(poster);
         });
        /**/

        /*
        $(this).on("timeupdate", (function (video) {
            return function () {
                progressBar.value = video.currentTime / video.duration;
            };
        }(this)));

        */
        // Add whe whole lot onto the page
        $videoContainer.append($controlsBox);

    });

    $(window, {
        on: {
            resize: function () {
                $().trigger("reposition");  // Draw everything on for the first time
            }
        }
    });

    $().trigger("reposition"); // Set initial positions
});
