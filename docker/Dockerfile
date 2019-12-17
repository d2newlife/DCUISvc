FROM openjdk:13
VOLUME /tmp
WORKDIR /opt/app
ADD ./target/dcuisvc-1.0.jar dcuisvc.jar
RUN bash -c 'touch dcuisvc.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/app/dcuisvc.jar"]