# #APPLICATION_NAME Spring Backend

Replace all values of
- #APPLICATION_NAME (ie pic in lowercase)
- #APPLICATION_FULL_NAME (ie TabletCertificate)
- #APP_ABBREVIATION (ie #UPPER_CASE_APP_ABBREVIATION)
- #UPPER_CASE_APP_ABBREVIATION (ie #UPPER_CASE_APP_ABBREVIATION)
- #DATABASE_NAME (ie db-#UPPER_CASE_APP_ABBREVIATION)

## Build

Use Java 13

Run `mvn clean install` to build the project. The build artifacts will be stored in the `target/` directory.

To skip tests run `mvn clean  install -DskipTests` _[ Always run all tests before deploying ]_

## Run the application

`mvn spring-boot:run`
