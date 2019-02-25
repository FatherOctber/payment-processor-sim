# payment-processor-simulator
:fish: Simulator of payment processor`s behaviour.

## Features:
	- Producing tokenized data of customer card info to consumer via Apache Kafka
	
## Workflow:
	- Customer API endpoint receive	REST request with customer card data via HTTP;
	- Customer service generate unique id for transaction via Redis and sent to Kafka input topic;
	- Tokenizer Streams listen Kafka input topic and tokenize incoming data by symmetric encription,
	  save decryption key to Redis and push tokenization data to Kafka output topic;
	- Consumer poll Kafka output topic and decrypt tokenization data by decryption key from Redis.  

## Technologies:
:point_right: Spring-Boot

:point_right: Spock Testing

:point_right: Redis

:point_right: Kafka

:point_right: Docker integration

## Run:
Use command "docker-compose build" to build docker images and "docker-compose up" for starting containers.
To get information about Customer API see customer-service-api/src/main/swagger/api.yaml.

Also you can build it and run microservices ('source' aka Customer-API, 'flow' aka Tokenizer, 'proof' aka Customer)
by using script commands in setup dir.
