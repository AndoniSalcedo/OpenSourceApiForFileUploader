FROM bellsoft/liberica-openjdk-alpine:latest
RUN mkdir /app &&  mkdir /app/libs

COPY *.jar /app/app.jar
COPY /libs/*.jar /app/libs/

WORKDIR /app

CMD ["java","-XX:+UseContainerSupport","-classpath","/app/libs/*:/app/app.jar:.", "service.Service"]
