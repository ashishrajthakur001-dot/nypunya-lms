FROM maven:3.8.8-eclipse-temurin-8 AS build
WORKDIR /workspace
COPY pom.xml ./pom.xml
COPY services ./services
RUN mvn -B -DskipTests package

FROM eclipse-temurin:8-jre
WORKDIR /app
ARG SERVICE_JAR
COPY --from=build ${SERVICE_JAR} /app/app.jar
ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "exec java -Dserver.port=${PORT} -jar /app/app.jar"]
