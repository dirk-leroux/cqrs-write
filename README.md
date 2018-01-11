# CQRS writer

Purpose of this service is to write to cqrs events to the event stream

## Overview

 
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