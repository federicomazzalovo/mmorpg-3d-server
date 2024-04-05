FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY server/target/*.jar app.jar
EXPOSE 80
ENTRYPOINT ["java","-jar","/app.jar"]