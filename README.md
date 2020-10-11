## Packet Gateway Service

1. This service is responsible for receving data packets continuously streaming from moving cars.
2. It will receive the data packets, generate the kafka payload and pump it on kafka topic to be processed by downstream services.
3. It needs to be horizontally scalable as it may need to handle very large amount of data packets coming per minute if no of cars registration spikes 

#### Tech Stack :

1. We can write reactive microservice in java which can handle very large number of requests per sec. We are using reactive stack as it does not work on thread per request model and work in non blocking mode and hence it can handle very large number of request concurrently
   
2. We can dockerize it and deploy them on orchestration engine either(Kubernetes or Docker swarm). We can scale in and scale out these containers as per requirement and depending on the current load in the cloud. 

## Load Balancer

1. LB will act as reverse proxy between Packet Gateway service and car endpoints. It will load balance the request coming from car endpoints. In this manner we can maintain high availability of the system and can tolerate outage of instances of packet gateway service

## Packet Handler Service

1. This service is responsible for handling actual data packets.
2. It will filter the valid data packets and then check for valid category by triggering rule based validation
3. Finally It will produce those messages on kafka to by further produced by downstream services
4. We will provide yml configuration file with the hlep of which we can simply plugin the new rule validation which will be automatically injected when the service will restart next time

#### Tech Stack :

1. We can write this microservice in java using springboot framework. 
2. We need to write kafka consumer within this microservice which will estabilish conneciton to kafka cluster and process messages and will execute all the rule based validations configuration in yml file.
3. We can dockerize it and deploy them on orchestration engine either(Kubernetes or Docker swarm). We can scale in and scale out
   these containers as per requirement and depending on the current load in the cloud.


## Kafka Message Broker

1. Messaging Broker helps us to achieve decoupling between producer and consumer. As Both of them does not have to be available at the same time hence system is not affected if any of them goes down and also they can scale indepedently with relying on each other processing power.

2. We can increase the no of kafka partitions depending on the workload

## Reporting

1. We need to contiuosly publish metrics from services to reporting server in order to get the latency and throghput of the kafka consumers.

2. We can use dropwizard metrics library by adding its maven dependency in project and we can use graphite server to to accumulate above metrics

## Monitoring

1. We can plot graffana dashhboard to get the throughput and latency of services in real time.We can also have dedeciated graph to get the consumer lag of kafka consumer of above microservices. Graffana can be configured to pull data from graphite server

2. We can set alert in graffana if specific metrics crosses specific threshold value
