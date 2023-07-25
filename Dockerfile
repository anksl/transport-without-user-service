FROM ibm-semeru-runtimes:open-11-jre
COPY target/transportation-service.jar transportation-service.jar
ENTRYPOINT ["java","-jar","/transportation-service.jar"]