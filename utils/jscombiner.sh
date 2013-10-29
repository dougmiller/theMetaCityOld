#!/bin/bash

# Combine and minify all the js files into one file to save on requests and bytes

cd media/js

echo 'Combining all JavaScript files'
cat searcher.js > tmcscripts.js
#cat tmcvideo.js >> tmcscripts.js


echo 'Removing unessary characters'

sed -i 's/\/\/.*//g' tmcscripts.js                # Dont need comments (and they become greedy when everything is on a single line)
sed -i 's/ #\*.*$//'g tmcscripts.js               # Dont need comments (and they become greedy when everything is on a single line)
sed -i '/console.*/'d tmcscripts.js               # Debug statements
sed -i 's/^[ ^t]*//' tmcscripts.js                # Leading whitespace and tabs N.B in theory there shouldnt need to be tabs anywhere but I am sure there will be
sed -i 's/[ ^t]*$//' tmcscripts.js                # Trailing whitespace and tabs
sed -i 's/ () /()/g' tmcscripts.js                # Spaces in 'function () {'
sed -i 's/ + /+/g' tmcscripts.js                  # Spaces in ' + '
sed -i 's/ || /||/g' tmcscripts.js                # Spaces in ' || '
sed -i 's/ += /+=/g' tmcscripts.js                # Spaces in ' += '
sed -i 's/for (/for(/g' tmcscripts.js             # Spaces in 'for ( ' Usually in a for loop
sed -i 's/; /;/g' tmcscripts.js                   # Spaces in '; ' Usually in a for loop
sed -i 's/ \(<\|<=\|>\|>=\) /\1/g' tmcscripts.js  # Spaces in ' < | > | <= | >= ' Usually in a for loop
sed -i 's/ \(+=\|-=\) /\1/g' tmcscripts.js        # Spaces in ' += | -= '
sed -i 's/ \(=\+\) /\1/g' tmcscripts.js           # Spaces in 'x = y', 'x == y', 'x === y'
sed -i 's/) {/){/g' tmcscripts.js                 # End of parameter list and opening curly brace
sed -i 's/if (/if(/g' tmcscripts.js               # End of if and opening round bracket
sed -i 's/, \(.\)/,\1/g' tmcscripts.js            # Comma space anything
sed -i ':a;N;$!ba;s/\n//g' tmcscripts.js          # New lines N.B. Put this at the end otherwise other operation (like get rid of leading white space) get confused

# GS is already optimised and mucking with it furthur seems to break things so just append it at the end
cat ga.js >> tmcscripts.js

echo 'Finished combining all JavaScript files'
