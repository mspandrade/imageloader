# imageloader
Micro service to upload images, resize them and read a uploaded image by id.

## Api documentation:
https://documenter.getpostman.com/view/1083622/RznCpeey

## To run in Docker

1. Download the admin sdk json file from your project Firebase's
 and put the file in the 'resource' path

2. Set the database in 'docker-compose.yml'

3. Finally run:    `docker-compose up`

## To run with spring boot tools

1. Download the admin sdk json file from your project Firebase's
 and put the file in the 'resource' path

2. Set the database in 'application.properties'

3. Finally run:    `mvn spring-boot:run`