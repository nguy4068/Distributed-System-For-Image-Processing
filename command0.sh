ssh -t -n -f nguy4068@kh4250-01.cselabs.umn.edu  "csh -c ' setenv THRIFT_LIB_PATH /project/nguy4068/thrift-0.15.0/compiler/cpp/thrift && setenv OPENCV_BIN /project/nguy4068/opencv/build/bin && setenv OPENCV_LIB /project/nguy4068/opencv/build/lib && setenv JAVA_LIBS /project/nguy4068/thrift-0.15.0/lib/java/build/libs && setenv JAVA_DEP /project/nguy4068/thrift-0.15.0/lib/java/build/deps && setenv PROJ_PATH /home/nguy4068/CSCI5105/P1 && setenv MACHINE_PATH config/machine.txt && setenv NODE_CONFIG_PATH config/computenode_config.txt &&  cd /home/nguy4068/CSCI5105/P1 &&  nohup ant -q -Dcomputenodeid=0 computenodeserver & '" > /dev/null
