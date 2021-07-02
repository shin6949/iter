FROM maven:3.8.1-jdk-11

MAINTAINER jaehyung.shin@slexn.com

USER root

COPY . /root/shin
RUN rm -rf /root/shin/.git
RUN rm -rf /root/shin/Dockerfile

WORKDIR /root/shin

ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

VOLUME ["/root/.m2"]
RUN mvn clean package -Dmaven.test.skip=true

EXPOSE 8080

CMD ["java", "-jar", "/root/shin/target/instagram-0.0.1-SNAPSHOT.war"]
