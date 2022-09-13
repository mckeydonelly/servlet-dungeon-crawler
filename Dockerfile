FROM tomcat:10.0

EXPOSE 8080
COPY ./target/servlet-dungeon-crawler.war /usr/local/tomcat/webapps/ROOT.war