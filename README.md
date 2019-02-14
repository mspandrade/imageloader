# imageloader
Micro service to upload images, resize them and read a uploaded image by id.

## Api documentation:
https://documenter.getpostman.com/view/1083622/RznCpeey

## To run

1. Set the MongoDB properties in 'application.properties'

2. Go to root path and in the command line execute the command 'mvn spring-boot:run'

3. Instance a container executing:
`docker run --name mongodb -p 27017:27017 -p 28017:28017 -e MONGODB_PASS="0000" mongo`

4. Build an image executing:
`mvn install dockerfile:build -Dmaven.test.skip=true`

5. Finally execute:
`docker run -t --name imageloader --link mongodb -p 80:8080 mspandrade/imageloader`