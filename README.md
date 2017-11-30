# Spring Starter

Purpose of this branch is to add rabbitmq integration to the ground-zero branch.

## Installing rabbitmq

The easiest way to install rabbitmq is to use a helm chart, head over to the 
[SprintHive Charts](https://github.com/SprintHive/charts/tree/devcharts#rabbitmq) project.

## Overview

The easiest way to see what has changed between ground-zero is to do a git compare.

1. build.gradle file - added rabbit dependencies 
2. HealthCheckConfig and MessagingConfig configures the HealthCheckService 
3. The HealthCheckService, HealthCheckProducer and HealthCheckConsumer was added
4. The sample/application.yaml changes the endpoint for the rabbit host and sets the password for your rabbit
 
## Running the code

    # copy the sample/application.yaml to the project root and change the password
    cp sample/application.yaml .
    
    ./gradlew bootRun
    
    # run with debug
    ./gradlew bootRun --debug-jvm 
    
### Sending and receiving a message

This will send the message 
    
    http://localhost:7001/health/rabbit 

    # You should see something like:    
    Rabbit test message sent
    
Check the logs, you should see something like this:

    Received ad43bd08-0809-4684-9364-d991dfc712b7 eventId: Testing 123        