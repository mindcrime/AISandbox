#!/bin/bash

mvn clean compile package
sleep .5s
# ssh bosworthserver 'kill -9 $(ps -A | grep java | grep pts | cut -d " " -f 2)'
sleep .5s
ssh bosworthserver rm -rf  /home/prhodes/bosworth/felix-framework-4.0.2/felix-cache/
sleep .5s
ssh bosworthserver rm /home/prhodes/bosworth/felix-framework-4.0.2/bundle/xmpp-bot-0.0.1-SNAPSHOT.jar
sleep .5s
scp target/xmpp-bot-0.0.1-SNAPSHOT.jar bosworthserver:/home/prhodes/bosworth/felix-framework-4.0.2/bundle/xmpp-bot-0.0.1-SNAPSHOT.jar
sleep .5s
scp conf/logback.xml bosworthserver:/home/prhodes/bosworth/felix-framework-4.0.2/conf/
sleep .5s
ssh bosworthserver

