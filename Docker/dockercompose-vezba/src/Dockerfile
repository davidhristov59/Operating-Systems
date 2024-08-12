FROM openjdk:11-jdk
WORKDIR /app
COPY SimpleHttpServer.java .
RUN javac SimpleHttpServer.java
CMD ["java", "SimpleHttpServer"]