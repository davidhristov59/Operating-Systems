FROM eclipse-temurin
WORKDIR /usr/src/myapp
COPY ExecutionsCounter.java .
RUN mkdir data
RUN javac ExecutionsCounter.java
CMD ["java", "ExecutionsCounter"]