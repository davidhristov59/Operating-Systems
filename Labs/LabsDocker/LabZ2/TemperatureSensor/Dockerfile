FROM openjdk:11-jdk
WORKDIR /usr/src
RUN javac TemperatureSensor.java
COPY TemperatureSensor.java .
CMD ["java", "TemperatureSensor"]