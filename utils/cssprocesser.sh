#!/bin/bash

# This is  a LESS processer instructions file

# Awesomely LESS has a minifier built in, so we'll use that

cd ..
cd media/css

lessc -x screen-common.less
lessc -x screen-root.less
lessc -x screen-workshop.less
lessc -x  screen-blog.less