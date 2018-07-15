FROM frolvlad/alpine-oraclejdk8:slim

ADD target/docker/block-fetcher.jar /app.jar

ENTRYPOINT java -jar /app.jar