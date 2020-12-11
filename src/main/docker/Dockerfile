FROM openjdk:8-jre-alpine

VOLUME /tmp

ARG JAR_FILE
ADD ${JAR_FILE} app.jar

COPY config/jira.cert jira.cert

# create a keystore and import certificate
RUN keytool -import -noprompt -trustcacerts \
    -alias jira -file jira.cert \
    -keystore ${JAVA_HOME}/lib/security/cacerts -storepass changeit

ENTRYPOINT ["java", \
  "-agentlib:jdwp=transport=dt_socket,address=50505,suspend=n,server=y", \
  "-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

MAINTAINER Daria Pydorenko <daria.pydorenko@globallogic.com>