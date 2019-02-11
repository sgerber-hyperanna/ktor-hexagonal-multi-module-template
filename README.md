# Ktor Ports and Adapters Multi Module Template

A dummy services that illustrates:

* Ports and Adapters
* Multi-module gradle kotlin dsl
* Guice injection with Ktor

## Testing
Run `./gradlew clean test` to run all the tests

## Running locally
Create the directory `/app/lib/conf` and add a file called 'jwt-secret.conf'
```hocon
jwt.secret = 1234-1234-1234-1234
```

Run `./gradlew :app:run` to run the service

## Project Structure

This project follows a 'ports and adapters' (a.k.a. hexagonal) architecture and is divided into modules

### Domain

The centre of the hexagon, contains core business logic.

#### Depends on
 * Ports

#### What goes here?
* Classes and functions related to business logic.

#### What does not go here?
* Transport layer concerns, e.g. JSON transformation,
* Frameworks (arguable - but try to avoid)
* Databases (arguable - but lets try to be pure)

### Adapters

Everything outside the hexagon. Nothing related to business logic.

#### Depends on
 * Ports

#### What goes here?
* JSON serializers/deserializers
* REST clients
* RabbitMQ senders/receivers (if applicable)
* Framework code, e.g. ktor routes
* DAOs, data layer

#### What does not go here?
* Business logic

### Ports
The 'ports' layer is the only way 'domain' and 'adapters' interact. It is divided into:
* Provided (by domain)
* Required (by domain

#### Depends on
 * Nothing

#### Provided ports

Interfaces (and data classes) implemented by the domain sub-project. The adapter layer uses this to communicate with domain code.

##### What goes here?

* Interfaces for domain services

#### What does not go here?

* Anything else


#### Required ports

Interfaces (and data classes) implemented by the adapters sub-project. The domain layer uses this to communicate with 
adapter layer.

##### What goes here?

* Interfaces for remote services, message brokers, repositories, etc.

##### What does not go here?

* Anything else

### App

This layer can see all other layers and is responsible for bootstrapping the running application

#### Depends on
 * Ports
 * Adapters
 * Domain

#### What goes here?

* The 'main method'
* Framework configuration needed to glue the other layers together, e.g. initializing Guice modules
* Build configuration needed to create a deployable service. 

#### What does not go here?

* Anything that otherwise fits into the 'ports', 'adapters' or 'domain' buckets

