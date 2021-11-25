Application to retrieve final Rover coordinates given some initial hints.

1. Compile the application.
        Run mvn command on pom project directory: mvn clean install
2. Build the docker the docker image.
        Run docker command: docker build --tag=message-server:latest
3. Run the application.
        Run docker command: docker run -8080:8080 message-server:latest

H2 database console is available at:
http://{host}/h2-console

Credentials to acceed h2 console:
JDBC URL: jdbc:h2:mem:mydb
User name: sa
Password: password

Swagger UI is available at (only deploying the application from IDE):
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