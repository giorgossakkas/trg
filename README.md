# TRG
Fleet Management System

# User Story
Microservice a: Implements the crud functionality for the Fleet Management System

Microservice b: Simulates a Car that is driven around a city.

Microservice c: Apply the penalties if the driver not driving in a behave manner.

# Implementation
Three different microservices are implemented.

Microservice a provides RESTful APIs for all the crud functionality of the system. You can use the end points using the following url: 

http://localhost:8081/swagger-ui/.

The code of the microservice a can be found in the following link.

https://github.com/giorgossakkas/trg/tree/main/microservice_a

Microservice b simulates a car the is driver arount a city. A class (CarTripProcessing) run periodically, retrieves a random car from all the cars and creates an event with car id, the driver id the trip information and the speed.The code of the microservice a can be found in the following link.

https://github.com/giorgossakkas/trg/tree/main/microservice_b
 
Microservice c consumes the event, calculates the penalty points and write the results in a mongo db.The code of the microservice a can be found in the following link.

https://github.com/giorgossakkas/trg/tree/main/microservice_c

# Technology

In this project, Quarkus framework with Apache Kafka, PostgreSQL and MongoDB were used.

https://quarkus.io/

https://www.cloudkarafka.com/

https://www.elephantsql.com/

https://www.mongodb.com/cloud
