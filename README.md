# restassured-api-project

Installation steps:
- Download and install JDK (also set system environments if needed, like JAVA_HOME and update PATH): https://www.oracle.com/java/technologies/downloads/
- Download and install IntelliJ Community Edition: https://www.jetbrains.com/idea/download/
- Download and install Maven (also add maven execution file to the PATH): https://maven.apache.org/download.cgi
- Open IntelliJ and create a new Maven project
- Select maven-archetype-quickstart as Maven project from Archetype, add "API-testing-example" to your project name
- Remove any generated Java code (if there are any)
- Replace your pom.xml file with the one in this repository
- Add Java test classes from this repository to your one (keep an eye on the folder/package structure)
- You should be ready to go, you can either run the test cases from IntelliJ or use Terminal in the IntelliJ and simply execute the "mvn clean test" project (recommended to start with this for the first time)
