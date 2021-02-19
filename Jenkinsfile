pipeline {

    agent {
        label 'maven'
    }

    environment {
       PROJECT = sre
       APP = sre-challenge
       TAG = latest
       REGISTRY = dkr.ecr.eu-west-1.amazonaws.com
       ECR_CREDENTIALS = ecr-credentials
    }

	stage("Download Code") {
		git([url: 'https://https://github.com/VictorRayfer/SRE-challenge.git', branch: 'master', credentialsId: 'victor-github-user-token'])
	}	

    stage('Build App') {
        container("maven") {
            steps {
                echo "==== Build App Stage ===="
                sh "mvn clean install -DskipTests=true"
            }            
		}
	}

    stage('Test app') {
        container("maven") {
            steps {
                echo "==== Test App Stage ===="
                sh "mvn test"
            }
        }
    }
	
    stage("Analyze Code"){
        container("maven") {
            withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')]) {
                println "==== Sonarqube Analysis ===="
                    withSonarQubeEnv('sonar-server'){
                        sh "mvn sonar:sonar -Dsonar.host.url=http://sonar-sonar.cicd.svc:9000 -Dsonar.login=${SONAR_TOKEN} -Dsonar.projectName=${PROJECT}"
                    }
            }
        }
    }
	

	stage("Build/Push Docker Image") {
		container("docker") {
            script {
                docker.withRegistry("${REGISTRY}", "${ECR_CREDENTIALS}") {
                
                    def dockerImage = docker.build("${REGISTRY}/${APP}:${TAG}"))
                    dockerImage.push()

                }
            }
        }
	}

	stage("Kubernetes Deployment") {
        container("helm") {
            steps {
                echo "==== Kubernetes Deployment ===="
                sh "helm upgrade -i ${PROJECT} helm/ -f helm/values.yaml --set deployment.tag=${TAG},deployment.app=${APP} -n ${PROJECT}"
            }
        }
    }
}
