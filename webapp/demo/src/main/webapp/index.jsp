<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>My Demo Application</title>
    </head>
<body>

<h2>Hello World!</h2>
<h3>brew install dos2unix</h3>
<h3>dos2unix mvnw</h3>
<h3>Code->Settings->Settings->Extensions->Maven for Java->maven.executable.path: /Users/nrazdhan/.m2/wrapper/dists/apache-maven-3.6.3-bin/1iopthnavndlasol9gbrbg6bf2/apache-maven-3.6.3/bin/mvn</h3>
<h4>/Users/nrazdhan/myDownloadedSoftwares/apache-tomcat-11.0.15/bin chmod +x *.sh</h4>
<h4>From Maven Side Window click Package</h4>
<h4>Run tomcat from /Users/nrazdhan/myDownloadedSoftwares/apache-tomcat-11.0.15/bin/startup.sh</h4>
<h4>Copy either demo folder from target into webapps directory of tomcat or just copy demo.war file into webapps directory of tomcat</h4>
<br/>
<h1>Static Image Display</h1>
<img src="<%=request.getContextPath()%>/Images/JediTeam.png" alt="Jedi Team"/>

</body>
</html>
