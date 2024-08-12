FROM openjdk:11-jdk
WORKDIR /usr/src
RUN javac TemperatureMonitor.java
COPY TemperatureMonitor.java .
CMD ["java", "TemperatureMonitor"]