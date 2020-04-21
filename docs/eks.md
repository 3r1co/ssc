# EKS Setup (1 hour)

You have already experimented with a local Kubernetes installation in the form of Minikube.
In the next step, we will set up a managed Kubernetes on AWS.
For this, we will use EKS, the Elastic Kubernetes Service.

To facilitate the instantiation of Kubernetes, we will use eksctl, a command line tool interact with Kubernetes.
To install it, please follow the guidelines [here](https://eksctl.io/introduction/installation/).

After the installation, please download the following file:

- [cluster.yaml](./files/eks/cluster.yaml)  

**Attention:** It is absolutely necessary that you use the file above for cluster definition. The AWS Educate has certain limitations that makes the "standard" installation fail.

You can then instantiate an EKS cluster by simply executing the following command:

`eksctl create cluster -f cluster.yaml`

After the cluster installation, it's time to extend your Github Actions workflow to launch a deployment in Kubernetes.

For this, please download the following files and place them in your repository:

- [kustomization.yaml](./files/kubernetes/kustomization.yaml)
- [deployment.yaml](./files/kubernetes/deployment.yaml)

You'll need to extend your workflow with the following lines:

    sed -i "s#IMAGE_NAME#<add-your-image-name-here>#g" kustomization.yaml
    sed -i "s/IMAGE_TAG/sha-$(git rev-parse --short HEAD)/g" kustomization.yaml
    aws eks update-kubeconfig --name isen
    kubectl kustomize . | kubectl apply -f -
