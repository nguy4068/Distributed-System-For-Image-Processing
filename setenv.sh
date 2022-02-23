#!/usr/bin/bash

echo "Setting some environment variables for user '$USER'..."

export JAVA_LIB="/project/$USER/thrift-0.15.0/lib/java/build/libs"
export JAVA_DEP="/project/$USER/thrift-0.15.0/lib/java/build/deps"
export THRIFT_LIB_PATH="/project/$USER/thrift-0.15.0/compiler/cpp/thrift"
export OPENCV_BIN="/project/$USER/opencv/build/bin"
export OPENCV_LIB="/project/$USER/opencv/build/lib"

echo "Checking the environment variables..."
env | grep -i "/project/$USER" | 
while read line; do echo "Path '$line' is set..."; done
echo "Finished!"
