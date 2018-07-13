ARG profile
FROM openjdk:8-jdk-alpine

RUN mkdir -p /usr/src/app

WORKDIR /usr/src/app

COPY . /usr/src/app/
RUN ./gradlew build

ENV HOST 0.0.0.0
EXPOSE 8080

CMD java -jar build/libs/rebridge-0.1.jar -Dspring.profiles.active=${profile}