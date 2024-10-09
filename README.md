# HW-3 Solutions

##
This solution was implemented by Eleanor Shao. 

## Video walkthrough
[Video for demo](https://youtu.be/Zf_dq_eQOCo)

[Code walkthrough](https://youtu.be/skuqZyIoCDA)

## Container URLs
1. [sa-frontend](https://hub.docker.com/repository/docker/eleanorshao/sa-frontend/general)

2. [sa-webapp](https://hub.docker.com/repository/docker/eleanorshao/sa-webapp/general)

3. [sa-logic](https://hub.docker.com/repository/docker/eleanorshao/sa-logic/general)
 
## Reproduction steps
Below are the steps to get my application running on GCP. Before you start, please make sure you have Docker engine, Node, and npm installed and download [this repository](https://github.com/rinormaloku/k8s-mastery) :
 
 1. Preq: Docker, Node, Npm:  
       `npm install`  
       `npm run build`  
 2. Download code from [here](https://github.com/rinormaloku/k8s-mastery)
 3. Use following command to create/build container images and push to docker hub:  
    Image Building:   
        `docker build --platform linux/amd64 -t eleanorshao/sa-frontend:latest .`  
        `docker build --platform linux/amd64 -t eleanorshao/sa-webapp:latest .`  
        `docker build --platform linux/amd64 -t eleanorshao/sa-logic:latest .`  
    Push to Docker hub:  
        `docker push eleanorshao/sa-frontend:latest`  
        `docker push eleanorshao/sa-webapp:latest`  
        `docker push eleanorshao/sa-logic:latest`  
 4. Change the image address in the following .yaml files for future orchestration:  
        `sa-frontend-deployment.yaml`  
        `sa-logic-deployment.yaml`  
        `sa-web-app-deployment.yaml`  
 5. Go to GCP, enable the billing
 6. Create a specific cluster(eg.saclusters) by the command:  
         ```gcloud container clusters create     --machine-type e2-standard-2     --num-nodes 2     --zone us-central1-a     --cluster-version latest     saclusters```
 7. Connect to the cluser:  
        ```gcloud container clusters get-credentials saclusters --zone us-central1-a --project cmu-class-435417```
 8. Do the orchestration by the following command:  
        `kubectl apply -f sa-frontend-deployment.yaml`  
        `kubectl apply -f sa-logic-deployment.yaml`  
        `kubectl apply -f sa-web-app-deployment.yaml`  
        `kubectl apply -f service-sa-frontend-lb.yaml`  
        `kubectl apply -f service-sa-logic.yaml`  
        `kubectl apply -f service-sa-web-app-lb.yaml`  
 9. Use the commands below to track the info about pods and services:  
        `kubectl get pods`  
        `kubectl get servcies`  
 10. Change the server address in the App.js file to be the external IP address of the web-app service.
 11. rerun the command: npm run build
 12. Build the docker image for frontend again and push to the docker hub, I change the tag to be "kube",and also the image address's tag in the .yaml file.
 13. Use the command:   
        `kubectl apply -f sa-frontend-deployment.yaml`  
 14. Visit the frontend's external IP address to run the application
 15. Disabel the billing on GCP


References:
* [Code provided from k8s-mastery](https://github.com/rinormaloku/k8s-mastery)
* [Kubernetes and everything else](https://rinormaloku.com/series/kubernetes-and-everything-else/)
* [Learn Kubernetes in Under 3 Hours: A Detailed Guide to Orchestrating Containers](https://medium.freecodecamp.org/learn-kubernetes-in-under-3-hours-a-detailed-guide-to-orchestrating-containers-114ff420e882)




