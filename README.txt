Reference:
https://learning.oreilly.com/videos/apache-kafka-series/9781789342604/
https://github.com/packtpublishing/apache-kafka-series---learn-apache-kafka-for-beginners

Twitter keys:
C:\Users\jomesa\kafka_2.13-2.8.1\Twitter

Elasticsearch:
https://app.bonsai.io/clusters/kafka-course-4006345810?hsmd=tail&hswd=900000&hstw=86400000

##########################################################################################
INITIAL CONFIGURATION

# Create the following directories within your kafka directory:
	data
		zookeeper
		kafka

# Edit the dataDir inside the file C:\Users\jomesa\kafka_2.13-2.8.1\config\zookeeper.properties. For windows you should use / isnstead of \

	dataDir=C:/Users/jomesa/kafka_2.13-2.8.1/data/zookeeper


# Start zookeeper:
	
	zookeeper-server-start.bat config/zookeeper.properties

# Edit the log.dirs inside the file C:\Users\jomesa\kafka_2.13-2.8.1\config\server.properties. For windows you should use / isnstead of \
	log.dirs=C:/Users/jomesa/kafka_2.13-2.8.1/data/kafka

# Start kafka
	kafka-server-start.bat config/server.properties

##########################################################################################
TOPICS

# Reference zookeeper
	kafka-topics.bat --zookeeper 127.0.0.1:2181

# Create a topic
	kafka-topics.bat --zookeeper 127.0.0.1:2181 --topic first_topic --create --partitions 3 --replication-factor 1	

# List topic
	kafka-topics.bat --zookeeper 127.0.0.1:2181 --list

# Describe a topic
	kafka-topics.bat --zookeeper 127.0.0.1:2181 --topic first_topic --describe

	####################
	# Exersice
	####################
	# Create a second topic
		kafka-topics.bat --zookeeper 127.0.0.1:2181 --topic second_topic --create --partitions 6 --replication-factor 1

	# List topic
		kafka-topics.bat --zookeeper 127.0.0.1:2181 --list

	# Warning
	Windows users should not delete topics because kafka will crash

##########################################################################################
CONSOLE PRODUCER

# Create a producter and add messages to the topic
	kafka-console-producer.bat --bootstrap-server 127.0.0.1:9092 --topic first_topic

# Type the messages and once its done prece CTRL + C
	hello Jose Luis
	awesome course!
	learning kafka
	just another message :)
	Terminate batch job (Y/N)? Y
	Y

# Set up acknoledge level (all - all the leader and replicas need to acknowledge the messasge
	kafka-console-producer.bat --bootstrap-server 127.0.0.1:9092 --topic first_topic --producer-property acks=all

# If you try to create a producer to an unexisting topic the producer will warn you and will create the new topic. The producer will recover it will wait until the topic's leader is created and then will send the message
	kafka-console-producer.bat --bootstrap-server 127.0.0.1:9092 --topic new_topic


##########################################################################################
CONSOLE CONSUMER

# Create consumer and start reading messages inserted to the topic after its creation
	kafka-console-consumer.bat --bootstrap-server 127.0.0.1:9092 --topic first_topic

# From beginning to read all the messages since the creation of the topic
	kafka-console-consumer.bat --bootstrap-server 127.0.0.1:9092 --topic first_topic --from-beginning


##########################################################################################
CONSOLE CONSUMER WITH GROUPS

# Create a consumer with a consumer group
	 kafka-console-consumer.bat --bootstrap-server 127.0.0.1:9092 --topic first_topic --group my-first-application

# Add another consumer to the consumer group, type the same previous command
	 kafka-console-consumer.bat --bootstrap-server 127.0.0.1:9092 --topic first_topic --group my-first-application


##########################################################################################
CONSUMER GROUPS
list all consumer groups, describe a consumer group, delete consumer group info, or reset consumer group offsets.

# List all the consumer groups
	kafka-consumer-groups.bat --bootstrap-server 127.0.0.1:9092 --list

# Describe a consumer group
	kafka-consumer-groups.bat --bootstrap-server 127.0.0.1:9092 --describe --group my-first-application


		kafka-consumer-groups.bat --bootstrap-server 127.0.0.1:9092 --group kafka-demo-elasticsearch --describe

# Reset the offset to a consumer group for a topic
	kafka-consumer-groups.bat --bootstrap-server localhost:9092 --group my-first-application --reset-offsets --to-earliest --execute --topic first_topic

# Reset the offset to n previous offsets
	kafka-consumer-groups.bat --bootstrap-server localhost:9092 --group my-first-application --reset-offsets --shift-by -2 --execute --topic first_topic

##########################################################################################
TESTING FOR JAVA

# For running successfully my java application I needed to follow the next steps

zookeeper-server-start.bat config/zookeeper.properties

kafka-server-start.bat config/server.properties

kafka-topics.bat --zookeeper 127.0.0.1:2181 --topic first_topic --create --partitions 3 --replication-factor 1

kafka-console-consumer.bat --bootstrap-server 127.0.0.1:9092 --topic first_topic --group my-third-application

##########################################################################################
TWITTER SPECIFIC

# Create topi for Twitter
kafka-topics.bat --zookeeper 127.0.0.1:2181 --topic twitter_tweets --create --partitions 6 --replication-factor 1

# Create twitter console consumer
kafka-console-consumer.bat --bootstrap-server 127.0.0.1:9092 --topic twitter_tweets


##########################################################################################

VOCABULARY

----------
BASIC
----------

Topic
A user-defined category to which messages are published. Topics consist of one or more partitions, ordered, immutable sequences of messages to which Kafka appends new messages. Each message in a topic is assigned a unique, sequential ID called an offset.

Producer
A process that publishes messages to one or more Kafka topics.

Consumer
A process that consumes messages from one or more Kafka topics.

Consumer Groups
When you create several consumers specifying the same groupId, you create a consumer group. Every consumer on the group will be assigned one or more partitions, and will only consume messages from the assigned partitions.
At the creation of each consumer you can see the assigned partitions in the logs. I did the test running several times ConsumerDemoGroups.java
	"[Consumer clientId=consumer-my-fifth-application-1, groupId=my-fifth-application] Adding newly assigned partitions: first_topic-0, first_topic-1"

Broker
A Kafka server.

Kafka Cluster
One or more Kafka Brokers.

Partition
(Partitions are for scalability, while Replication is for availability)
Kafka topics are divided into a number of partitions. Any record written to a particular topic goes to particular partition. Each record is assigned and identified 
by an unique offset. Replication is implemented at partition level. The redundant unit of topic partition is called replica. The logic that decides partition for 
a message is configurable. Partition helps in reading/writing data in parallel by splitting in different partitions spread over multiple brokers. Each replica has 
one server acting as leader and others as followers. Leader handles the read/write while followers replicate the data. In case leader fails, any one of the 
followers is elected as the leader.

Offset
Current pivot element on a partition

Replication
Apache Kafka is a distributed software system in the Big Data world. Thus, for such a system, there is a requirement to have copies of the stored data. 
In Kafka, each broker contains some sort of data. But, what if the broker or the machine fails down? The data will be lost. Precautionary, Apache Kafka enables 
a feature of replication to secure data loss even when a broker fails down. To do so, a replication factor is created for the topics contained in any particular 
broker. A replication factor is the number of copies of data over multiple brokers. The replication factor value should be greater than 1 always (between 2 or 3). 
This helps to store a replica of the data in another broker from where the user can access it.

Keys
Messages for a topic with the same key goes to the same partition.

Close
"producer.close" -> It sends all the messages that have not been processed by the producer to the Kafka server before the producer shuts down.

---------------------
PRODUCER FINE TUNE
---------------------

ACK
Acknowlege from the consumer once they recevie a message
	ack = 0 (no acks)
		No response is requested
		If the broker goes offline or an exception happens, we won't know and will lose data
			Useful for data where it's okay to potentially lose messages:
				Metrics collection
				Log collection
	ack = 1
		Only leader acknowledge
		Can present data los

	ack = all
		All the leader and replicas should ackowledge
		No data lost
		More latency

		Goes in clunjuction with "min.insync.replicas"
			min.insync.replicas = 2   => at least 2 brokers that are ISR must acknowledge

		Example:
			replication.factor=3; min.insync=2; acks=all
				=> only tolerate 1 broker going down, otherwise the producer will receive an exception on send.
	
----------------
Producer “retries”
NOTE * If you are using Kafka >= 1.0.0 there is a better solution with indempotent producers!
	Retries when a failure while producing a message
	Default settins:
		0 for kafka <= 2.0
		2147483647 for kafka > 2.1

  Goes in conjunction with “retry.backoff.ms” 
	Each retry will be sent every “retry.backoff.ms” milliseconds
	Default setting:
		100 ms

  And “delivery.timeout.ms” 
	Preferable way for controlling retry behaviour, the producer will retry until the “delivery.timeout.ms” is met

----------------


Indempotent Producer (BETTER APROACH THAN retries)

producerProps.pup(“enable.indempotence”, true);

The default configuration includes (Advice use de defaults or study this more carefully):

	retries = 2174483647
	max.in.flight.request = 5
	acks=all

* max.in.flight.request. The maximum number of unacknowledged requests the client will send on a single connection before blocking. Note that if this config is set to be greater than 1 and enable.idempotence is set to false, there is a risk of message re-ordering after a failed send due to retries (i.e., if retries are enabled).

	Example explicit properties:
	        // create safe producer
        	properties.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        	properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");
        	properties.setProperty(ProducerConfig.RETRIES_CONFIG, Integer.toString(Integer.MAX_VALUE));
        	properties.setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "5");

Message Compression
	compression.type
		none
		gzip
		lz4
		snappy
	Recommended:
		snappy or lz4 for optimal speed / compression ration

	linger.ms: number of milliseconds a producer is willing to wait before sending a batch out. (default 0). If you increase it you can get a better troughtput, see slides

	batch.size: maximum number of bytes that will be included in a batch. The default is 16KB
---------------------
CONSUMER FINE TUNE
---------------------

Delifery Semantics 

	At Most One
		offsets are committed as soon as the message batch is received. If the processing goes wrong, the message will be lost (it won't be read again).

	At Least One (Default behavior, recommended with indempotent)
		offsets are commited after the message is processed. If processing goes wrong, the message will be read again. This can result in duplicate processing of messages.
		Make sure your processing is indempotent (i.e. processing again the messages won't impact your systems)


		In decode you need to define an Id for inserting, in order to get indempotency:

		        // Two strategies to generate the id:
        	        // 1. kafka generic ID
                	//String id = record.topic() + "_" + record.partition() + "_" + record.offset();

	                // 2. Twitter feed specific
        	        /// String id = extractIdFromTweet(record.value());

		And then use the defined ID:

	                // where we insert data into ElasticSearch
        	        IndexRequest indexRequest = new IndexRequest(
                	        "twitter",
                        	"tweets",
	                        id // this is to make our consumer idempotent
        	        ).source(jsonString, XContentType.JSON);


Consumer Poll Behavior 

	(It is not recommended to change them at least you have a good optimization reason)

		fetch.min.bytes (default 1)
		max.poll.records (default 500)
		max.partitions.fetch.bytes (default 1MB)
		fetch.max.bytes (default 50 MB)

Consumer Offset Commits Strategies

	enable.auto.commit = true & synchronous processing of batches
		Could be risky because you Will be in “at-most-once” behavior because offsets Will be committed before your data is processed

	enable.auto.commit = false & synchronous processing of batches
		It is better because you only commit after your data is proccessed

		In the code you should:
			// disable auto commit of offsets
			properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false"); 

			// Commit once processed all the poll lote	
			consumer.commitSync();

BulkRequest
	Helps you to read from kafka multiple records at once helping to improve throughput. See in the code the example:
		BulkRequest bulkRequest = new BulkRequest();

Replaying data for consumeros

	Take all the consumers from a specific group down		
		kafka-consumer-groups.bat --bootstrap-server 127.0.0.1:9092 --group kafka-demo-elasticsearch --describe		
	Use Kafka-consumer-groups command to set offset to what you want		
	Restart Consumers
		kafka-consumer-groups.bat --bootstrap-server 127.0.0.1:9092 --group kafka-demo-elasticsearch --reset-offsets --execute --to-earliest --topic twitter_tweets

	Bottom line
		Set proper data retention period & offset retention period
		Ensure auto offset reset gehavior is hte one you expect /want
			erlieast
			latest
			none
		Use replay capability in case of unexpected behavior
			

