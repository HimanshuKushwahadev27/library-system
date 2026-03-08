FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .

COPY Api-gateway/pom.xml Api-gateway/pom.xml

RUN mvn -B -q -e -DskipTests dependency:go-offline

COPY Api-gateway/src Api-gateway/src

RUN mvn -pl Api-gateway -am package -DskipTests


FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/Api-gateway/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]