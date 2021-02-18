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

