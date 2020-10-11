Packet Gateway Service :-

1. This service is responsible for receving data packets continuously streaming from moving cars.
2. It will receive the data packets, generate the kafka payload and pump it on kafka topic to by processed by
   downstream services.
3. It needs to be horizontally scalable as it may need to handle very large amount of data packets coming per minute if no of cars registration
   spikes 
   
     
Tech Stack :

1. We can write reactive microservice in java which can handle very large number of requests per sec. We are using reactive stack
   as it does not work on thread per request model and work in non blocking mode and hence it can handle very large number of request
   concurrently
   
2. We can dockerize it and deploy them on orchestration engine either(Kubernetes or Docker swarm). We can scale in and scale out
   these containers as per requirement and depending on the current load in the cloud.  
   
Packet Handler Service :-

1. This service is responsible for handling data packets
2. It will filter the valid data packets and then check for valid category by triggering rule based validation
3. Finally It will produce those messages on kafka to by further produced by downstream services

Tech Stack :

1. We can write this microservice in java using springboot framework. 
2. We need to write kafka consumer within this microservice which will estabilish conneciton to kafka cluster 
   and process messages and run the business logic
3. We can dockerize it and deploy them on orchestration engine either(Kubernetes or Docker swarm). We can scale in and scale out
   these containers as per requirement and depending on the current load in the cloud.


   
Kafka Broker as Messaging Queue

1. We can increase the no of kafka partitions depending on the workload
2. It helps us to achieve decoupling between producer and consumer. As producer and consumer does not have to available at the same time
   and they can scale indepedently with relying on each other processing power.



   
