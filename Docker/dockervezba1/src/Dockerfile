FROM eclipse-temurin
WORKDIR /usr/src/myapp
COPY ExecutionCounter.java .
RUN mkdir -p ./data && touch ./data/raf.out
RUN javac ExecutionCounter.java
CMD ["java", "ExecutionCounter"]