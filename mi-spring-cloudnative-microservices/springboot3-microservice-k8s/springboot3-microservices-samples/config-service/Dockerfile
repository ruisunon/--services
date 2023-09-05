FROM openjdk:11
ENV APP_FILE config-service-1.0-SNAPSHOT.jar
ENV APP_HOME /app
EXPOSE 8088
COPY target/$APP_FILE $APP_HOME/
WORKDIR $APP_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["exec java -jar $APP_FILE"]

# FROM openjdk:11
# ARG JAR_FILE=target/*.jar
# COPY ${JAR_FILE} cloud-config-server.jar
# ENTRYPOINT ["java","-jar","/cloud-config-server.jar"]
# EXPOSE 9296