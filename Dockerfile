FROM jeanblanchard/java:8

COPY ./build/libs/echo-0.1.0.jar /echo.jar

EXPOSE 8080

CMD ["java", "-jar", "/echo.jar"]