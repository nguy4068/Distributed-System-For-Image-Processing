#!/usr/bin/bash

echo "Setting some environment variables for user '$USER'..."

export JAVA_LIBS="/project/$USER/thrift-0.15.0/lib/java/build/libs"
export JAVA_DEP="/project/$USER/thrift-0.15.0/lib/java/build/deps"
export THRIFT_LIB_PATH="/project/$USER/thrift-0.15.0/compiler/cpp/thrift"
export OPENCV_BIN="/project/$USER/opencv/build/bin"
export OPENCV_LIB="/project/$USER/opencv/build/lib"

echo "Checking the environment variables..."
env | grep -i "/project/$USER" | 
while read line; do echo "Path '$line' is set..."; done
echo "Finished!"

echo "Setting the autograder text files..."

user1="nguy4068"
user2="kim00967"


if [ $USER == $user1 ]; then
  echo "The current user is $user1"
  proj_path="/home/$USER/CSCI5105/P1"
  echo "setting the project path as $proj_path"
  echo "PROJ_PATH $proj_path" > "$proj_path/autograder/env.txt"
else
  echo "The current user is $user2"
  proj_path="/home/$USER/csci5105/assignments/P1"
  echo "setting the project path as $proj_path"
  echo "PROJ_PATH $proj_path" > "$proj_path/autograder/env.txt"
fi

autograder_path="$proj_path/autograder"

echo "THRIFT_LIB_PATH $THRIFT_LIB_PATH" >> "$autograder_path/env.txt"
echo "OPENCV_BIN $OPENCV_BIN" >> "$autograder_path/env.txt"
echo "OPENCV_LIB $OPENCV_LIB" >> "$autograder_path/env.txt"
echo "JAVA_LIBS $JAVA_LIBS" >> "$autograder_path/env.txt"
echo "JAVA_DEP $JAVA_DEP" >> "$autograder_path/env.txt"
echo "PROJ_PATH $proj_path" >> "$autograder_path/env.txt"

echo "Check the contents of env.txt..."
echo "~~~ env.txt ~~~"
cat $autograder_path/env.txt
echo "~~~~~~~~~~~~~~~"
