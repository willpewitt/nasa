FROM openjdk:11

VOLUME /tmp

ADD target/nasa-0.0.1.jar /app/

RUN bash -c 'touch /app/nasa-0.0.1.jar'

# Setup environment variables for initializing java runtime
ENV JAVA_OPTS ""

ENTRYPOINT ["bash","-c","java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app/nasa-0.0.1.jar"]

EXPOSE 8080
