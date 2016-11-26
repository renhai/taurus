#!/bin/bash
cd /root/workspace/taurus/
git pull origin master
mvn clean package -DskipTests
nohup java -Xms712m -Xmx712m -Dspring.profiles.active=prod -Dphantomjs.binary.path=/usr/local/phantomjs-2.1.1-linux-x86_64/bin/phantomjs -jar target/taurus-0.0.1.jar >./log/nohup.log 2>&1 &