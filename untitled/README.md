Application to retrieve final Rover coordinates given some initial hints.

1. First of all we will need to compile the application.
        Run mvn command on pom project directory: mvn clean install
2. Second we will need to run the docker image.
        Run docker command: docker run -p8887:8888 message-server:RoverFinal

H2 database console is available at:
http://{host}/h2-console

Credentials to acceed h2 console:
JDBC URL: jdbc:h2:mem:mydb
User name: sa
Password: password

Swagger UI is available at (only deploying the application on IDE):
http://{host}/swagger-ui/

Endpoint available:
curl -X POST \
  http://{host}/coordinates/ \
  -H 'cache-control: no-cache' \
  -H 'postman-token: 3604fd54-6a8e-e07b-8127-3ebfe988ca16' \
  -d '3 3

Input example:
1 2 E
RFRFRFRF
1 2 e
RRRFLLFFRRFLL
3 3 W
LLFFFRFLFL