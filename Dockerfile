FROM openjdk:8-jdk-alpine
EXPOSE 8080

CMD java -jar /data/rebridge-0.1.jar

ADD ./build/libs/rebridge-0.1.jar /data/rebridge-0.1.jar