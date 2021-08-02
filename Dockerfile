FROM tomcat:8.5.69-jdk11-openjdk-buster

COPY ./target/instagram-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

ENTRYPOINT ["/usr/local/tomcat/bin/catalina.sh"]
CMD ["run"]