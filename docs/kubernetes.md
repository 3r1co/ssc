# Kubernetes Lab (1 hour)

In this lab, you will deploy your previously created Docker image in Kubernetes.  
The goal of this lab is to

* show you the usage of Kubernetes deployments and services
* how to use Kubernetes scaling capabilites
* how to access a service deployed in Kubernetes

## Application Deployment

1. Verify that you can access Kubernetes:  

		kubectl version

	If you see a `Server Version` like below, it means your Kubernetes CLI can connect to your Kubernetes VM:

		$ kubectl version
		Client Version: version.Info{Major:"1", Minor:"13", GitVersion:"v1.13.10", GitCommit:"37d169313237cb4ceb2cc4bef300f2ae3053c1a2", GitTreeState:"clean", BuildDate:"2019-08-19T10:52:43Z", GoVersion:"go1.11.13", Compiler:"gc", Platform:"linux/amd64"}
		Server Version: version.Info{Major:"1", Minor:"13", GitVersion:"v1.13.10", GitCommit:"37d169313237cb4ceb2cc4bef300f2ae3053c1a2", GitTreeState:"clean", BuildDate:"2019-08-19T10:44:49Z", GoVersion:"go1.11.13", Compiler:"gc", Platform:"linux/amd64"}


1. Download the following file to your "CloudAwarenessLab" folder:  

    -  [deployment.yaml](./files/kubernetes/deployment.yaml)  

1. Open the file in an editor and verify that the `image:` key is referencing your previously built image

1. Deploy your application with the following command:  

		kubectl apply -f deployment.yaml

1. Verify that your application is running properly:  

		kubectl get deployment

   	You shoud now see one running **Pod**, which is scheduled by the **Deployment** that you just created.

1. You can also check the running Pods in your Kubernetes cluster by typing:  

		kubectl get pods

	This will give a list of running instances (a.k.a. Pods) of your application.  
	Write down the name of the Pod, you'll need it later for reference.

1. In order to access your application, you have to deploy a Kubernetes service.   
Download the following file your "CloudAwarenessLab" folder: 

    - [service.yaml](./files/kubernetes/service.yaml) 

    and apply the following command:    

		kubectl apply -f service.yaml

1. You have now deployed a so called **NodePort** Kubernetes **Service**. It opens a dedicated port on your Minikube VM, through which you can access the according service.  
You can find the associated port number by typing:  

		kubectl get svc

    In the example below, the port number would be **31478**:

		$ kubectl get svc  
		NAME                TYPE        CLUSTER-IP       EXTERNAL-IP   PORT(S)        AGE  
		kubernetes          ClusterIP   10.96.0.1        <none>        443/TCP        126d
		webserver-service   NodePort    10.98.147.142    <none>        80:31478/TCP   4s

1. In your browser, open the IP of your Minikube VM (which you retrieved in the previous lab) and add the port that you retrieved from the last command, e.g.: 	[http://minikube-ip:31478](http://minikube-ip:31478).  
   You should see "Hello World" example from before, but it's hosted in Kubernetes.  
   You should also see that the hostname is equal to the Pod name that you wrote down earlier.

## Application Scaling

1. Now you'll see the scaling capabilities of Kubernetes. Enter the following command:  

		kubectl scale deployment/webserver-deployment --replicas=3

	With this command, you update the Kubernetes **Deployment** and instruct it to have a total of *three* replicas. Kubernetes will handle that by instantiating *two* additional **Pods**. 

1. Refresh your browser serveral times and monitor how the hostname of your microservice changes.  
   Congratulations, you just learned how to scale a service in Kubernetes.

## Application Configuration

1. For the next step, we we'll see how to configure an application in Kubernetes.  
   You might have noticed that in the `app.js` file, we are defining an environment variable _GREETING_ with the default value _Hello World_.  
   In a first step, we will change the Kubernetes the Kubernetes deployment and add environment variable section the Pod template:  

		kubectl edit deployment webserver-deployment

	and add the _env_ section like described below:

		spec:
		containers:
		- image: mywebserver:1.0
			imagePullPolicy: IfNotPresent
			name: webserver
			env:
			- name: GREETING
			  value: "I'm configured now"

1. Refresh your browser, and see how to greeting changed.

1. Now let's use another mean to configure our application: the Kubernetes **ConfigMap**. Download the sample ConfigMap to your "CloudAwarenessLab" folder:  

    - [configmap.yaml](./files/kubernetes/configmap.yaml)

    This way, you can decouple the application from the deployment configuration and therefore ease the reusability of your application. You can deploy the ConfigMap with the following command:  

		kubectl apply -f configmap.yaml

1. Now, you'll have to modify your deployment in order to consume the ConfigMap:

		kubectl edit deployment webserver-deployment

	And edit the file in the following way:

		spec:
		containers:
		- image: mywebserver:1.0
			imagePullPolicy: IfNotPresent
			name: webserver
			env:
			- name: GREETING
			valueFrom:
				configMapKeyRef:
				name: webserver-configmap
				key: greeting

## Application Secrets

Kubernetes also supports objects of the type **Secret**, that are meant to store sensitive data. Secrets can either be injected as environment variables or mounted in the Pods filesystem.  
As you already learned how to inject environment variables, let's now inject the Kubernetes secret as a file into our pod.

1. Deploy a secret in our Kubernetes cluster: 

		kubectl create secret generic webserver-secret --from-literal=secret.txt="Well done!"

1. Update your Pod definiton to mount the _webserver-secret_ secret in _/var/secret/_:

		kubectl edit deployment webserver-deployment

	And edit the file in the following way:

		spec:
		containers:
		- image: mywebserver:1.0
			imagePullPolicy: IfNotPresent
			name: webserver
			volumeMounts:
			- name: webserver-secret
			mountPath: "/var/secret"
			readOnly: true
		volumes:
		- name: webserver-secret
			secret:
			secretName: webserver-secret

1. Refresh your browser, and see how the greeting changed.

## Follow up exercise (30 Minutes)

Try to launch a database in Kubernetes and connect an application with this database.  
A simple example can be found [here](https://github.com/FaztWeb/express-mongodb-crud). It's written in Node.JS and uses MongoDB.