#!/bin/bash

cp target/normalized-properties-0.1.0-SNAPSHOT.jar target/dependency/normalized-properties.jar
PROPS="-Dlog4j2.contextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector -Dlog4j2.configurationFile=log4j2-debug.xml -Dlog4j2.asyncLoggerWaitStrategy=sleep"

#ATTACH=""
ATTACH="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"

if [[ $1 = "classpath" ]] ; then
  echo "Running on the classpath"
  java $ATTACH $PROPS -cp "target/dependency/*" reproduce.reproduce.Main
elif [[ $1 = "modulepath" ]] ; then
  echo "Running on the module path"
  java $ATTACH $PROPS --add-modules=jdk.unsupported --module-path target/dependency --module reproduce.reproduce
else
  echo "Use ./run.sh classpath or ./run.sh modulepath"
fi
