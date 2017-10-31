# Spring Starter

Purpose of this project is to provide a good starting point for building a SprintHive service and
to cover some basic tasks.

## Running

    gradle bootRun
    
    # run with debug
    gradle bootRun --debug-jvm 
    
## Libraries 

[http://projects.spring.io/spring-boot/]()  
[https://projectlombok.org]() - Spice up your java: Automatic Resource Management, 
automatic generation of getters, setters, equals, hashCode and toString, and more!

## Profiles

dev - properties are loaded from application.yaml 
test - properties are loaded from application-test.yaml   
preprod - properties are loaded from application-preprod.yaml  
production - properties are loaded from application-production.yaml

Helper scripts to run the app with different profiles

/bin/runTest.sh - packages the app and runs with the test profile activated
/bin/runPrepod.sh - packages the app and runs with the preprod profile activated
/bin/runProd.sh - packages the app and runs with the prod profile activated

### Override props

You can override props by creating a <project-home>/application.yaml this file is .gitignored

## Logging

@Slf4j
lombrok
logback
fluent 
When to use what level?
Error 
Warning
Info
debug


## Testing

To run an individual test:

    $ gradle test -Dtest.single=PropTest

To debug a test:

    $ gradle test -Dtest.debug=true -Dtest.single=DemoSpec

## Deployment

    # create executable jar see <project-home>/bin/run*.sh for example 
    gradle bootRepackage

## JSON Basics

Take a look at the JsonController for some examples of how to GET and POST json.

    # Post some JSON to a echo service
    curl -X POST \
      http://localhost:7001/json/example2 \
      -H 'cache-control: no-cache' \
      -H 'content-type: application/json' \
      -d '{
        "id":1505140851471,
        "name":"Testing json mapping",
        "dateCreated":"2017-10-31T07:25:36.176",
        "dateOfBirth":"2017-09-11"
    }'

## Deploying to Kubernetes 

The section describes how to deploy the starter app into kubernetes so that it can be accessed from outside of the cluster.

Create a executable jar file

    gradle bootRepackage

Configure your docker to use your minikube's docker daemon.

    eval $(minikube docker-env)
    # to undo it run 
    eval $(minikube docker-env -u)

Create a docker image  

    docker build -t spring-starter:v1 .
    
    # Confirm that it has been created
    docker images
    
    REPOSITORY                                             TAG                 IMAGE ID            CREATED             SIZE
    spring-starter                                         v1                  e8efbf79ca06        10 seconds ago      223MB 
    
Create a deployment

There are 2 ways to create a deployment, using kubectl run or from a yaml file using kubectl create

Using kubectl run: 

    # This will create a pod and a deployment
    # Run uses a kubernetes default template to create a deployment
    kubectl run spring-starter --image=spring-starter:v1 --port=7001 

From a yaml file:   

    # This will create a pod and a deployment
    kubectl create -f config/kubernetes/deployment-basic.yaml
    
Confirm that the pod and deployment was created

    # Confirm that the pod has been created 
    kubectl get po
    
    NAME                              READY     STATUS    RESTARTS   AGE
    spring-starter-2028074326-pbzmm   1/1       Running   0          1m
    
    # Confirm that the deployment has been created 
    kubectl get deploy
        
    NAME             DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE
    spring-starter   1         1         1            0           1m

Test that you can connect to the pod by port forwarding to the pod.

    # Use kubectl to port forward to the pod
    kubectl port-forward spring-starter-2028074326-pbzmm 7001:7001
    
    # To confirm that you can connect to the app browse to http://localhost:7001/ping 
    # You should see something like: 
    OK 1509010487804    
      
Create a service

Now to expose the app to the out side world we can create a service.  
There are 2 ways to create a service, using kubectl expose or from a yaml file 

Using kubectl expose

    kubectl expose deployment spring-starter --type=NodePort

Using a yaml file

    kubectl create -f config/kubernetes/service-basic.yaml 
    
Confirm that the service was created    
    
    # List services
    kubectl get svc    

    NAME             CLUSTER-IP   EXTERNAL-IP   PORT(S)          AGE
    ...
    spring-starter   10.0.0.31    <nodes>     7001:32314/TCP   7s
    ...
    
    # Confirm that you can connect to the service via the node/node-port
    # Get the ip address of your minikube node
    minikube ip  
    > 192.168.99.100
        
    # Then using the node port connect to the service by browsing to
    http://192.168.99.100:32314/ping                                                          

    # You should see something like: 
    OK 1509011969860    
    
Create a route so that you can connect to services using the external ip address and the service port.

Warning: this OSX only
Warning: this route will have to be recreated when your machine is restarted because your minikue ip may change.

    # Create a route between my local dev machine and minikube
    # Remove any existing routes
    sudo route -n delete 10/24 > /dev/null 2>&1
    
    # Create a route 
    sudo route -n add 10.0.0.0/24 $(minikube ip)

    # To confirm that you can connect to the app through the service
    # browse to http://10.0.0.31:7001/ping 
    # You should see something like: 
    OK 1509011969860    

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
    gradle bootRun
    
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