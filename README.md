# Spring Starter

The purpose of this branch is to add redis to the ground-zero branch. 

## Adding Redis into the mix

The section will explain how we can deploy redis into the cluster and access it from the starter app to share state 
between different instances of the starter-app.

### Installing Redis

    # Install redis from a deployment descriptor 
    create -f config/kubernetes/redis_deployment.yaml    

    # Check that redis is up and running by connecting to the pod and running redis_cli
    kubectl exec -it <redis-pod-name> -- /bin/bash
    
    # Start the redis-cli
    root@redis-3060245838-3r7n7:/data# redis-cli

    # Create and get a dummy value
    127.0.0.1:6379> set testing 123
    OK
    127.0.0.1:6379> get testing
    "123"
        
## Running the basic redis example

Using the [redisson](https://github.com/redisson/redisson) java client 

    # Add the following line to the dependencies section of your build.gradle file
    compile('org.redisson:redisson:3.5.4')

### Locally 

The RedisConfig configures [redisson](https://github.com/redisson/redisson) to connect to redis://redis:6379
To run the app on your laptop and have it connect to redis in the cluster you need to point http://redis to 127.0.0.1  

You can do this by editing your hosts file.

    sudo vi /etc/hosts
    
    # Add a line like
    127.0.0.1       redis    

    # Start the app locally 
    ./gradlew bootRun
    
    # Browse to http://localhost:7001/redis/hello
    # You should see something like:
    Test Value Saved 1509345187482    
    
    # Now check in redis to see the value
    kubectl exec -it <redis-pod-name> -- /bin/bash
    
    # Start the redis-cli
    root@redis-3060245838-3r7n7:/data# redis-cli
    
    # List all the keys
    127.0.0.1:6379> keys spring-starter*
    1) "spring-starter::testValue"
        
    # Get the value 
    127.0.0.1:6379> get spring-starter::testValue
    "Sugar is yuck" 
    
### Testing the app running in the cluster       

    After completing the "Deploying to Kubernetes" section you should have the app running in the cluster.  
         
    # Find the service
    kubectl get svc
    
    # Run the example 
    http://192.168.99.100:32314/redis/hello
         
    # Check the values exist in redis, the steps for this is the same as locally
    