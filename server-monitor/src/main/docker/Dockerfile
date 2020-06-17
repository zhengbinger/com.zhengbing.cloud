FROM openjdk:8-jdk
VOLUME /tmp
ADD server-monitor-0.0.1-SNAPSHOT.jar  /app.jar
EXPOSE 10110
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]