@ECHO OFF
%JAVA_HOME%\bin\java -Dfile.encoding=UTF-8 -jar "%~dp0..\consumer\target\consumer-1.0-SNAPSHOT.jar" --spring.config.additional-location="config\consumer.properties"