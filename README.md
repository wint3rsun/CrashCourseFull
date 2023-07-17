Instructions for installing this project.
1. Download the ZIP file with the project.
2. Create a MySQL server. Make sure the username & password is the same as listed in the application.properties files in the project.
3. Create databases named AccountData and SongData.
4. Open the project in your IDE. Ensure that JDK 17 is installed.
5. Navigate to the project folder in a command line.
6. To run the AccountService project, run ./mvnw spring-boot:run
7. Navigate to http://localhost:8081/swagger-ui/index.html in your browser.
8. To run the MovieService project, run ./gradlew bootRun
9. Navigate to http://localhost:8080/swagger-ui/index.html in your browser.
10. To run the EurekaServer project, run ./gradlew bootRun
11. Navigate to http://localhost:8761/ in your browser.