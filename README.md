AUTO CATALOG APPLICATION
-------------------
INTRODUCTION

Auto Catalog is a web application for managing car listings. It provides users with functionalities to create, update, delete, and view listings. The application also includes an admin interface for managing users and their roles.
-------------------
TECHNOLOGIES USED

-Java
-Spring Boot
-Spring Security
-JWT (JSON Web Token) for Authentication
-JPA/Hibernate for ORM
-PostgreSQL for Database
-Swagger for API Documentation
-Maven for Build and Dependency Management
-------------------
PREREQUISITES

-Java 17 or higher
-Maven 3.6.0 or higher
-PostgreSQL
-------------------
INSTALLATION

1.Clone the repository:
git clone https://github.com/AleinikauArtsiom/auto_catalog.git
cd auto_catalog
-------------------
2.Create a PostgreSQL database:
CREATE DATABASE auto_catalog_db;
-------------------
3.Update the application.properties file in the src/main/resources directory with your PostgreSQL credentials:
spring.datasource.url=jdbc:postgresql://localhost:5432/auto_catalog_db
spring.datasource.username=your_username
spring.datasource.password=your_password
-------------------
4.Build the project using Maven:
mvn clean install
-------------------
5.Run the application:
mvn spring-boot:run
-------------------
API DOCUMENTATION

API documentation is available through Swagger. After starting the application, you can access it at:
http://localhost:8080/swagger-ui/index.html




  









