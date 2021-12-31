Reference:
https://learning.oreilly.com/videos/apache-kafka-series/9781789342604/
https://github.com/packtpublishing/apache-kafka-series---learn-apache-kafka-for-beginners

Twitter keys:
C:\Users\jomesa\kafka_2.13-2.8.1\Twitter

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




VOCABULARY

Partitions
Replication
Keys

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

