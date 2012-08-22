#!/bin/bash

# Combine all the css files for each medium into one file to save on requests and redundancy
# The specific apps files have a @charset "utf-8"; at the top we dont want hence the tail call

cd media/js

echo 'Combining all JavaScript files'
cat searcher.js > tmcscripts.js

echo 'Removing unessary characters'

sed -i 's/ *\/\/.*//g' screen.js      # Dont need comments (and they become greedy when everything is on a single line)
sed -i ':a;N;$!ba;s/\n//g' screen.js  # New lines
sed -i 's/\t//g' screen.js            # Tabs
sed -i 's/ () /()/g' screen.js        # Spaces in 'function () {'
sed -i 's/ \(=\+\) /\1/g' screen.js   # Spaces in 'x = y', 'x == y', 'x === y'
sed -i 's/) {/){/g' screen.js         # End of parameter list and opening curly brace
sed -i 's/if (/if(/g' screen.js       # End of if and opening round bracket
sed -i 's/, \(.\)/,\1/g' screen.js    # Comma space anything

echo 'Finished combining all JavaScript files'
