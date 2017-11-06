#!/usr/bin/groovy
@Library('github.com/SprintHive/sprinthive-pipeline-library@nonf8')

def componentName = 'spring-starter'
def versionTag = ''
def resourcesDir = 'config/kubernetes'
def dockerImage

mavenNode(label: 'maven-and-docker') {
    stage('Compile source') {
        checkout scm
        versionTag = getNewVersion {}
        dockerImage = "${componentName}:${versionTag}"

        container(name: 'maven') {
            sh "./gradlew bootRepackage buildImage -PdockerImageTag=${dockerImage}"
        }
    }

//    stage('Build and publish docker image') {
//        container('docker') {
//            sh "docker build -t ${dockerImage} ."
//        }
//    }

    stage('Rollout to Local') {
        def namespace = 'local'
        def deployStage = 'development'

        def kubeResources = kubeResourcesFromTemplates {
            templates = [
                    readFile(resourcesDir + '/deployment.yaml'),
                    readFile(resourcesDir + '/service.yaml')
            ]
            stage = deployStage
            version = versionTag
            image = dockerImage
            name = componentName
        }

        for (String kubeResource : kubeResources) {
            kubernetesApply(file: kubeResource, environment: namespace)
        }
    }
}
