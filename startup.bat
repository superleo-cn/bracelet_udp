@echo off
cd C:\bracelet\bracelet_udp
set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_25
set APP_HOME=C:\bracelet\bracelet_udp
set PATH = %PATH%;%JAVA_HOME%\bin
set CLASSPATH=.;%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\lib\tools.jar
set CLASSPATH=%CLASSPATH%;%APP_HOME%\lib\*
java com.qt.server.UdpServer
pause