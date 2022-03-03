#!/bin/bash


count=0
while read p; do
   echo $p
   if [ $count == 0  ]; then
      NODE_PORT_0=`echo $p | cut -f 2 -d ' '`
   elif [ $count == 1  ]; then
      NODE_PORT_1=`echo $p | cut -f 2 -d ' '`
   elif [ $count == 2  ]; then
      NODE_PORT_2=`echo $p | cut -f 2 -d ' '`
   elif [ $count == 3  ]; then
      NODE_PORT_3=`echo $p | cut -f 2 -d ' '`
   fi 
   count=`echo "scale=0;$count + 1" | bc`
done < config/computenode_config.txt
echo $NODE_PORT_0
echo $NODE_PORT_1
for i in 0 1 2 3 
do
    if [ $i -eq 0 ]; then 
      NODE_PID_0=`lsof -i:$NODE_PORT_0 | grep "java" | awk '{print $2}'`
      echo $NODE_PID_0
    elif [ $i -eq 1 ]; then
      NODE_PID_1=`lsof -i:$NODE_PORT_1 | grep "java" | awk '{print $2}'`
    elif [ $i -eq 2 ]; then
      NODE_PID_2=`lsof -i:$NODE_PORT_2 | grep "java" | awk '{print $2}'`
    elif [ $i -eq 3 ]; then
      NODE_PID_3=`lsof -i:$NODE_PORT_3 | grep "java" | awk '{print $2}'`
    fi
done 
kill -9 $NODE_PID_0
kill -9 $NODE_PID_1
kill -9 $NODE_PID_2
kill -9 $NODE_PID_3



