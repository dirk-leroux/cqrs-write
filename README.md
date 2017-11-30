# Spring Starter

The purpose of this branch is to add mongodb to the ground-zero branch.

## Install mongodb 

The easiest way to install mongodb is to use a helm chart, head over to the 
[SprintHive Charts](https://github.com/SprintHive/charts/tree/devcharts#mongodb) project.

## Overview

The easiest way to see what has changed between ground-zero is to do a git compare.


1. build.gradle file - added mongo dependencies 
2. AppEventConfig configures the AppEventService 
3. The event package is where most of the code was added
4. The sample/application.yaml changes the endpoint for mongodb from mongodb to mongodb.local.svc.cluster.local  

## Running the code
  
    # copy the sample/application.yaml to the project root
    cp sample/application.yaml .

    ./gradlew bootRun
    
    # run with debug
    ./gradlew bootRun --debug-jvm 

### Testing the AppEventController

This will create a test event

    curl http://localhost:7001/event/test    
    
    # This should output something like this
    {"eventId":"ca745273-a09a-4c56-a824-cd08a0957c91"}
    
This will lookup the event by the event id

    curl http://localhost:7001/event/find/ca745273-a09a-4c56-a824-cd08a0957c91
    
     