FROM openjdk:12-jdk

EXPOSE 8080

WORKDIR /app

COPY out/artifacts/twitter_jar/twitter.jar ./twitter.jar

CMD java -jar twitter.jar

