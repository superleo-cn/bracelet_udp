#!/bin/bash
cd ~/bracelet_udp
export APP_HOME=~/bracelet_udp
export CLASSPATH=$CLASSPATH:$APP_HOME/lib/*
java com.qt.server.UdpServer & 
