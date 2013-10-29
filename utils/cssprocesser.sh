#!/bin/bash

# This is  a LESS processer instructions file

# Awesomely LESS has a minifier built in, so we'll use that

cd ..
cd media/css

lessc screen-common.less
lessc screen-root.less
lessc screen-workshop.less
lessc screen-blog.less