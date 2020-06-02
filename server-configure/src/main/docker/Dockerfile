FROM openjdk:8-jdk
VOLUME /tmp
ADD server-configure-0.0.1-SNAPSHOT.jar  /app.jar
EXPOSE 11000
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]