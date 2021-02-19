# SRE Challenge

This README collects the questions

## How will you ensure the application is deployed properly?

We can make sure that our application is deployed correctly by checking the following:

- Jenkins Log Oputput
- Application is Running on Kubernetes 1/1.
- Application log on Kubernetes (kubectl logs pod_name -n namespace>)
- Check if we have EFK.

## How can you check the application logs once deployed?

We can check the application logs if we have installed a centralized logs system such as EFK or by launching the command (kubectl logs pod_name -n namespace).

## Can you be alerted when application is not ready?

Obviously, we can configure a check at application level and in Kubernetes through Liveness/Readiness probe. Once we have these checks configured, we can monitor and configure them through Prometheus, even send alerts via Telegram, Slack, Teams etc...

---

# Pipeline Documentation

This pipeline performs the deployment of the java application with maven in Kubernetes. To deploy in Kubernetes I have used a simple helm Chart.

- **Download Code Stage:**  

As improvements we can add both the repository URL and the branch as variables. 
To connect and download the code we need some credentials which we can store in Jenkins directly (not recommended) or download the Kubernetes plugin that synchronizes the secrets in Jenkins (victor-github-user-token).

- **Build App:**  

I have used the container template so we need to have created the agents to perform the tasks in the steps, in this case, we need to have previously created an agent with maven. (mvn clean install). This step execute the build of application.

- **Test app:** 

In the same way, we need to have the maven agent container to execute this step. This step execute the application tests.

- **Analyze Code:** 

This step is additional although if we think that this code has to be in production it has to go through a code analysis process with sonarqube. In the same way, we need to have the credentials created to be able to access and deploy the analysis in the sonarqube project.(sonar-token). I have added the project variable to distinguish it.

- **Build and Push Docker Image:**  

This step is responsible for building and pushing the image to a repository, in this case a private repository, which is located in AWS. Likewise, these credentials need to be stored in Kubernetes secrets, synchronized in Jenkins. 

We need to have downloaded the Jenkins docker plugin to be able to use this information.

I have added as an example the parameterization with registry variables (REGISTRY), the name of the application (APP) and the tag of the image (TAG).

- **Kubernetes Deployment:**  

This last step is in charge of deploying the image in Kubernetes, for this we need the yamls of the resources:

    - Deployment (Definition of Application)
    - Service (Internal Communication)
    - Ingress (Public Communication). If we have EKS or AKS It is recommended to use the service as LoadBalancer

I have chosen this technology (Helm) because it makes the deployment easier and the value update simpler, in this case we have a simple application but when we have a more complex application, changing the values in the yamls files is not easy.




