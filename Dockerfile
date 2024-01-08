FROM openjdk:17-jdk-slim

EXPOSE 5500

COPY target/money-transfer-0.0.1-SNAPSHOT.jar app.jar

CMD ["java","-jar", "app.jar"]