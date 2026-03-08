FROM bellsoft/liberica-openjre-alpine
WORKDIR /usr/share/app
COPY build/libs/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar" ,"app.jar"]