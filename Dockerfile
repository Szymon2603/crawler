FROM tomcat:8.5.15-alpine

COPY docker/tomcat/tomcat-users.xml /usr/local/tomcat/conf/
COPY docker/tomcat/context.xml /usr/local/tomcat/webapps/manager/META-INF/
COPY crawler-app/target/crawler-app-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/crawler-app.war
