FROM openjdk:8-alpine
VOLUME /tmp
RUN mkdir /opt/app
RUN chmod 755 /opt/app
ADD ./target/*.jar /opt/app/app.jar
RUN sh -c 'touch /opt/app/app.jar'
EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -jar /opt/app/app.jar"]
