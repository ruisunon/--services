# kafka-using-docker
Start Kafka and Zookeeper service locally using docker configuration.

### Start the containers using the following command:
docker-compose up -d

### Verify that Kafka is running:
docker logs kafka

### To start a shell session inside the kafka container
docker exec -it kafka /bin/bash

## Once you are inside the Kafka container you can run kafka commands
### To check kafka version
kafka-server-start --version

### To list all the topics
kafka-topics --bootstrap-server localhost:9092 --list

### To creates a new topic called demo-test-topic with one partition and one replica.
kafka-topics --bootstrap-server localhost:9092 --create --topic demo-test-topic --partitions 1 --replication-factor 1

### To verify that the topic is created
kafka-topics --bootstrap-server localhost:9092 --describe --topic demo-test-topic

kafka-topics --bootstrap-server localhost:9092 --list


### To publish a message to the 'demo-test-topic' using kafka-console-producer
echo "Hello, world!" | kafka-console-producer --broker-list localhost:9092 --topic demo-test-topic

### To consume a message from the 'demo-test-topic' using kafka-console-consumer
kafka-console-consumer --bootstrap-server localhost:9092 --topic demo-test-topic --from-beginning
