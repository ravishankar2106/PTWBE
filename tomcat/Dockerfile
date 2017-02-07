FROM tomcat:7-jre8

ADD tomcat-users.xml /usr/local/tomcat/conf/

RUN rm -rf /usr/local/tomcat/webapps/ROOT

ENV CATALINA_OPTS="-Dprop.path=/usr/local/tomcat/webapps/PTWBE-0.1/WEB-INF/classes"

COPY ./PTWBE-0.1.war /usr/local/tomcat/webapps/PTWBE-0.1.war


CMD ["catalina.sh", "run"]