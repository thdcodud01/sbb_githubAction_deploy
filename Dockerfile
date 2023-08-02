FROM openjdk:17-jdk-alpine
ARG JAR_FILE=build/libs/sbb-0.0.3.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]