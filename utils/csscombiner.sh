#!/bin/bash

# Combine all the css files for each medium into one file to save on requests and redundancy
# The specific apps files have a @charset "utf-8"; at the top we dont want hence the tail call

cd media/css

echo 'Combining all screen css files'
cat screen-common.css > screen.css
tail screen-root.css -n +2 >> screen.css
tail screen-blog.css -n +2 >> screen.css
tail screen-workshop.css -n +2 >> screen.css

echo 'Removing unessary characters'
# Comments
# new lines, tabs and spaces
sed -i ':a;N;$!ba;s/\n//g' screen.css  # New lines
sed -i 's/\t//g' screen.css            # Tabs
sed -i 's/\(;\) */\1/g' screen.css     # Spacing matters!
sed -i 's/ *\({\) */\1/g' screen.css   # Spacing still matters!
sed -i 's/\(:\) */\1/g' screen.css     # Spacing never stopped mattering!

echo 'Finished combining all screen css files'

echo 'Combining all mobile css files'
cat mobile-common.css > mobile.css
tail mobile-root.css -n +2 >> mobile.css
tail mobile-blog.css -n +2 >> mobile.css
tail mobile-workshop.css -n +2 >> mobile.css

echo 'Removing unessary commands'
# Comments
# newlines, tabs and spaces
sed -i ':a;N;$!ba;s/\n//g' mobile.css
sed -i 's/\t//g' mobile.css
sed -i 's/ //g' mobile.css

echo 'Finished combining all mobile css files'
