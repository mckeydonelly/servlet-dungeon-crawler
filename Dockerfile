FROM maven:latest as maven_builder
RUN mkdir -p /app
WORKDIR /app
COPY pom.xml /app
RUN mvn -B dependency:resolve dependency:resolve-plugins
COPY src /app/src
RUN mvn package

FROM tomcat:10.0

EXPOSE 8080
WORKDIR /app
COPY --from=maven_builder /app/target/servlet-dungeon-crawler.war /usr/local/tomcat/webapps/ROOT.war