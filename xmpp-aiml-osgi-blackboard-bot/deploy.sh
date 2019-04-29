#!/bin/bash

mvn clean compile package
sleep .5s
ssh fogbeam-dev 'kill -9 $(ps -A | grep java | grep pts | cut -d " " -f 2)'
sleep .5s
ssh fogbeam-dev rm -rf  /home/aimlbot/felix-framework-4.0.2/felix-cache/
sleep .5s
ssh fogbeam-dev rm /home/aimlbot/felix-framework-4.0.2/bundle/xmpp-bot-0.0.1-SNAPSHOT.jar
sleep .5s
scp target/xmpp-bot-0.0.1-SNAPSHOT.jar fogbeam-dev:/home/aimlbot/felix-framework-4.0.2/bundle/xmpp-bot-0.0.1-SNAPSHOT.jar
sleep .5s
scp conf/logback.xml fogbeam-dev:/home/aimlbot/felix-framework-4.0.2/conf/
sleep .5s
ssh fogbeam-dev

