FROM eclipse-temurin
WORKDIR /usr/src/myapp
COPY Example.java .
RUN javac Example.java
EXPOSE 4000
CMD ["java", "Example"]