FROM openjdk:8u141-jdk-slim
MAINTAINER jeremydeane.net
EXPOSE 8080
RUN mkdir /app/
COPY target/magic-supplies-1.0.1.jar /app/
ENTRYPOINT exec java $JAVA_OPTS -Dmagic.broker='tcp://magic-broker:61616' -jar /app/magic-supplies-1.0.1.jar