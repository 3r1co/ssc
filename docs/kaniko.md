# Docker Security with Kaniko (1 hour)

Interacting with the Docker engine directly through the `docker` command line tool can impose a significant security thread.

In order to improve this risk and limit access to the Docker engine, there are several projects that allow building Docker images without a Docker engine. One of them is **kaniko**, which you will use to build and push a Docker image to ECR.

We will use Jenkins and our previously created EKS cluster in order to build highly secure and scalable build system.

As the setup will be rather complex, before we begin, please have a look at the following graphic that will visualize our final setup:

![Kaniko Setup](./files/kubernetes/kaniko-setup.png)

To do so, please follow these steps:

1. Create an Elastic Container Repository Repository

    1. Go to the AWS Console and open the ECR menu (by searching for Elastic Container Registry)
    1. Click on "Create repository"
    1. Enter "juice-shop" and click on "Create Repository"
    
1. Add new permissions to your EKS Node Group

    1. Go to the AWS Console and open the IAM menu
    1. Go to Roles
    1. Search for "NodeInstanceRole"
    1. Select the NodeInstanceRole and click the button "Attach policies"
    1. Search for "Container"
    1. Check "AmazonEC2ContainerRegistryPowerUser"
    1. Click on "Attach Policy"

1. Create an IAM Role for your Jenkins EC2 Instance
    
    1. Go to your AWS Console and open the IAM menu
    1. Go to Roles
    1. Click on "Create role"
    1. Click on "EC2" and confirm with "Next: Permissions"
    1. Click on "Next: Tags"
    1. Click on "Next: Review"
    1. Give it the name "role-jenkins-master"
    1. Go to the EC2 menu
    1. Select your Jenkins EC2 instance and click on "Actions" -> "Instance Settings" -> "Attach/Replace IAM Role" and select the previously created role from the drop-down menu.

1. Update the aws-auth ConfigMap in your EKS cluster

    1. From your workstation, enter the following command:
        
            kubectl edit cm aws-auth -n kube-system
        
        and add the following entry to the mapRoles section:

                - groups:
                    - system:masters
                  rolearn: arn:aws:iam::XXXXXXXXXXXX:role/role-jenkins-master
                  username: system:node:{{EC2PrivateDNSName}} 

        where XX... is your AWS Account ID

        For your information, the AWS Account ID can be found in the IAM Menu on the bottom left corner, where it's written "AWS account ID" (a 12 digit number).

1. Update your Jenkins Security Group

    1. In the EC2 Console, open the security group that you assigned to your Jenkins EC2 Instance
    1. Allow requests on port **8080** and **50000** from anywhere (Attention, this is not best practice and we only do this for the purpose of the course)
1. Create a new kubeconfig and upload it to the Jenkins Kubernetes Plugin

    1. In your workstation, enter the following command:

            aws eks update-kubeconfig --kubeconfig config-for-jenkins --name isen

        A new file with the name "config-for-jenkins" will be created in your current directory. You'll need it in the next step.
1. Install the Jenkins Kubernetes Plugin

    1. In your Jenkins, go to "Settings" and select "Manage plugins".
    1. Click on the tab "Available" and search for "Kubernetes plugin"
    1. Check it and click "Install". In the next screen, select to restart Jenkins after installation.

1. Configure the Kubernetes Plugin

    1. In Jenkins, go "Settings" and select "Manage nodes"
    1. Click on "Configure Clouds"
    1. Click on "Add cloud" and select "Kubernetes".
    1. Click on "Kubernetes Cloud details..."
    1. In Kubernetes namespace, enter "default"
    1. Next to Credentials, click on "Add" and select "Jenkins".
    1. As Kind, choose "Secret file" and in the file row, upload the file `config-for-jenkins` file that you created in step 5
    1. Confirm with "Add" and choose the uploaded file as credential.
    1. Click on "Save".

1. Create a docker-config ConfigMap in Kubernetes

    1. Save the following file as `docker-config.yaml` on your PC:

            apiVersion: v1
            kind: ConfigMap
            metadata:
            name: docker-config
            data:
            config.json: |-
                {
                "credHelpers": {
                    "XXXXXXXXXXXX.dkr.ecr.us-east-1.amazonaws.com": "ecr-login"
                }
                }
        
    1. Replace XX... with your AWS Account ID
    1. Create this file in your Kubernetes cluster by entering:

            kubectl create -f docker-config.yaml

After all these steps are done, you can create a pipeline with the example below:

    pipeline {
    agent {
        kubernetes {
        //cloud 'kubernetes'
        yaml """
    kind: Pod
    metadata:
    name: kaniko
    spec:
    containers:
    - name: kaniko
        image: gcr.io/kaniko-project/executor:debug-539ddefcae3fd6b411a95982a830d987f4214251
        imagePullPolicy: Always
        command:
        - cat
        tty: true
        volumeMounts:
        - name: docker-config
            mountPath: /kaniko/.docker
    volumes:
        - name: docker-config
        configMap:
            name: docker-config
    """
        }
    }
    stages {
        stage('Build with Kaniko') {
        steps {
            git 'https://github.com/<your-github-username>/juice-shop'
            container(name: 'kaniko') {
                sh '''
                /kaniko/executor --dockerfile `pwd`/Dockerfile --context `pwd` --destination=XXXXXXXXXXXX.dkr.ecr.us-east-1.amazonaws.com/juice-shop:latest --destination=XXXXXXXXXXXX.dkr.ecr.us-east-1.amazonaws.com/juice-shop:v$BUILD_NUMBER
                '''
            }
        }
        }
    }
    }

Please replace XX... with your AWS Account ID.
