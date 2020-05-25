# Living as one coding challenge

## How to run:
First you will need to build the jar
`mvn clean install`


Then build the docker image.\
`docker build -t pewitt/nasa .`

Then run the container \
`docker run -p 8080:8080 pewitt/nasa`

Then open a browser to `http://localhost:8080
`

    
