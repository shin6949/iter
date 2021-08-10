FROM maven:3.8.1-openjdk-11

RUN mvn package
COPY ./target/iter-*-SNAPSHOT.jar /usr/src/myapp/iter.jar

EXPOSE 8080

WORKDIR /usr/src/myapp
CMD ["java", "-jar", "./iter.jar"]