FROM bellsoft/liberica-openjdk-alpine:latest

RUN mkdir /src

COPY *.jar /src/app.jar

WORKDIR /src

CMD ["java","-jar","app.jar"]
