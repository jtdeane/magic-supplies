magic-supplies
=======================

Built with Java 8+, and Spring Boot (1.5.6.RELEASE)

Tested with JUnit (4.12)

### Docker Execution

Build Magic-Supplies

`mvn clean install`

Start Docker

`docker-compose up`

View Application

`http://localhost:8080/info`

View ActiveMQ (_admin/admin_)

`http://localhost:8161/admin/`

View Hawtio

`http://localhost:8090/hawtio/welcome`

### Spring-Boot Execution

Build Magic Supplies

`mvn clean install`

Start Spring Boot - _assumes ActiveMQ is running_

`mvn spring-boot:run -Drun.arguments="-Xmx256m,-Xms128m"`

OR

`java -jar ./target/magic-supplies-1.0.0.jar`

View Application

`http://localhost:8080/info`

View ActiveMQ (_admin/admin_)

`http://localhost:8161/admin/`

### Testing via JMeter

JMeter (2.13) - requires activemq-all-5.14.0.jar in JMeter lib directory

Open JMeter file and execute the tests

`../magic-supplies/test/jmeter/Mock Message Publisher.jmx`
