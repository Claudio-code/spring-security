FROM maven:3-openjdk-17-slim as builder

WORKDIR /builder

COPY . .

RUN mvn clean package -DskipTests --batch-mode

FROM openjdk:17-jdk-alpine

COPY --from=builder /builder/target/*.jar /app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
