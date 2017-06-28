FROM java:8

COPY /target/vertx-demo-1.0-SNAPSHOT-fat.jar /vertx-demo-1.0-SNAPSHOT-fat.jar

ENTRYPOINT ["java", "-jar", "/vertx-demo-1.0-SNAPSHOT-fat.jar"]
