FROM java:8

COPY *.jar /app.jar

CMD ["--server.port=9101"]

EXPOSE 9101

ENTRYPOINT ["java","-jar","/app.jar"]