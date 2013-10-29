#!/bin/bash

# Combine all the css files for each medium into one file to save on requests and redundancy
# The specific apps files have a @charset "utf-8"; at the top we don't want, hence the tail call

cd media/css

echo 'Combining all screen css files'
cat common.css > screen.css
tail root.css -n +2 >> screen.css
tail blog.css -n +2 >> screen.css
tail workshop.css -n +2 >> screen.css