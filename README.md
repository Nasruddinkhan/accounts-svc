# accounts-svc
Spring boot 3 with java 17 and spring security with in memory database

Tools:
- Java 17 : it should be installed before running script
- Spring boot 3.1.1
- Maven 3+ : it should be installed before running script
- Ms Access : Import database into MS ACCESS from this folder `database/accountsdb.accdb`
- Sonar for code quality -> Setup: docker run -d -p 9000:9000 --name sonarqube sonarqube
- Generate Sonar token add token inside <sonar.token> /<sonar.login> for sonar code quality check

# Clone and run the project.
- clone the project open into your idea and run it.
- add the postman collection `account-svc.postman_collection.json` and environment in your postman `account-env.postman_environment.json` and test the api.

# Auditing 
- I create custom annotation `LogRequestResponse` please check logging package. For enabling this change the log level debug by using actuator.

# For Metric
- Please check the observe package which are show the failure and calling count.
  http://localhost:8011/accounts-svc/actuator/metrics/{obsername} check the postman collection
