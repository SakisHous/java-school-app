# java-school-app

A simplified graphical user interface (GUI) for school management written in Java with Swing. A teacher or student can register or log in the system and
monitor the meetings, courses and classes. In addition, an admin can log in and perform all CRUD operations in all the entities in the database.

<ins>Database Entities:</ins><br>
* Teacher
* Student
* User
* Speciality
* City
* Meeting


Each table in the database is presented as entity in the Model package of this application.


The application uses MySQL 8 database system for storing the information and in Java the JDBC driver for MySQL is the mysql-connector-j. In the pom.xml file they are listed all dependencies for this app where Maven manages dependencies.<br>
The connection with the database is performed with an utility class where it is used to open and close an connection.

The design pattern is a MVC-like pattern where the controller and view are not separated.
* First, it has an Data Access Object Layer where it has Public APIs and implementations of these APIs. This layer is communicate with the database.
* The model package is the package where the entities are declared.
* The Data Transfer Object (DTO) package contains objects for data transfer like insert, update and log in.
* The Service Layer contains the services, Public API where client are using.
* The Viewcontroller Layer directs the each event from Swing in the right service at Service Layer using the DTOs.

In Swing's applications, the view and controller is in the same package there is no separation between these two concepts, for example
in a web application.
In addition, in the Viewcontroller there are some simple validations for the forms where user adds data.

Lastly, for login and register passwords are hashed using the jBcrypt library. There is a utility class where utility methods has declared to ease this operation
of hashing passwords and checking hashed passwords.

<ins>Unit Tests:</ins><br>
In addition, there are [unit tests](src/test) for the most important classes in the application.
