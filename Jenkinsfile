#!/usr/bin/groovy
@Library('github.com/SprintHive/sprinthive-pipeline-library@nonf8')

def componentName = 'spring-starter'
def versionTag = ''
def dockerImage = ''
def resourcesDir = 'config/kubernetes'

mavenNode(label: 'maven-and-docker') {
    stage('Compile source') {
        checkout scm
        versionTag = getNewVersion{}
        dockerImage = "spring-starter:${versionTag}"

        container(name: 'maven') {
            sh './gradlew bootRepackage'
        }
	}

	stage('Build and publish docker image') {
		container('docker') {
			sh "docker build -t ${dockerImage} ."
//			def img = docker.image(dockerImage)
//			docker.withRegistry("https://378685577721.dkr.ecr.eu-west-2.amazonaws.com/", "ecr:eu-west-2:ecr-robot") {
//				img.push()
//			}
		}
	}

    stage('Rollout to Local') {
        def namespace = 'local'
        def deployStage = 'development'

        def kubeResources = kubeResourcesFromTemplates{
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
