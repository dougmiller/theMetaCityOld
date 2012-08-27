#!/bin/bash

# Combine all the css files for each medium into one file to save on requests and redundancy
# The specific apps files have a @charset "utf-8"; at the top we dont want hence the tail call

cd media/js

echo 'Combining all JavaScript files'
cat searcher.js > tmcscripts.js

echo 'Removing unessary characters'

sed -i 's/ *\/\/.*//g' tmcscripts.js      # Dont need comments (and they become greedy when everything is on a single line)
sed -i ':a;N;$!ba;s/\n//g' tmcscripts.js  # New lines
sed -i 's/\t//g' tmcscripts.js            # Tabs
sed -i 's/ () /()/g' tmcscripts.js        # Spaces in 'function () {'
sed -i 's/ \(=\+\) /\1/g' tmcscripts.js   # Spaces in 'x = y', 'x == y', 'x === y'
sed -i 's/) {/){/g' tmcscripts.js         # End of parameter list and opening curly brace
sed -i 's/if (/if(/g' tmcscripts.js       # End of if and opening round bracket
sed -i 's/, \(.\)/,\1/g' tmcscripts.js    # Comma space anything

echo 'Finished combining all JavaScript files'
