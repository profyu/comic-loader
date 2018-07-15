FROM frolvlad/alpine-oraclejdk8:slim

ADD target/docker/comic-loader.jar /app.jar

ENTRYPOINT java -jar /app.jar