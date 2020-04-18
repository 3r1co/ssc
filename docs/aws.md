# AWS Labs (1 hour)

In this lab, you'll learn

* How to connect to AWS from your workstation
* How to design an Infrastructure as Code (IaC) file with the help of Troposphere
* How to deploy this file in your Development environment
* How to deliver this file through the Infrastructure Automation Pipeline to Production

## AWS Connection setup

In this chapter, we'll explain you how to connect with the AWS CLI to your AWS Account.  

1. Create a user in IAM that has administrator privileges like described [here](https://docs.aws.amazon.com/IAM/latest/UserGuide/id_users_create.html#id_users_create_console)

1. Create a Access Key ID and Secret Access Key like described [here](https://docs.aws.amazon.com/IAM/latest/UserGuide/id_credentials_access-keys.html#Using_CreateAccessKey)

1. In a terminal, type the following:  

        aws configure

    - Enter the Access Key ID and Secret Access Key you retrieved earlier.  
    - Enter `eu-west-1` as default region and leave the default output empty.  
    
    These credentials will be stored in `%HOME%/.aws/credentials`.

1. Verify that you have access to the training account:

        aws ec2 describe-instances

## CloudFormation

Let's now deploy your first resource in the development account. 
Download the following file to your "CloudAwarenessLab" folder:  

- [cloudformation.yaml](./files/aws/cloudformation.yaml)  

This file deploys a single EC2 instance.

1. Deploy the EC2 instance with the help of CloudFormation:   

        aws cloudformation deploy --template-file cloudformation.yaml --stack-name <your-login>-stack

1. Wait until the stack is deployed and check if your instance is visible:  

        aws ec2 describe-instances

### Follow up exercise

Modify the CloudFormation template in order to add a [Security Group](https://docs.aws.amazon.com/de_de/AWSCloudFormation/latest/UserGuide/aws-properties-ec2-security-group.html) which allows access on Port 443 to your EC2 instance.

## Troposphere

Another way to generate CloudFormation templates is to use a framework for a programming language. One famous example is Troposphere, a Python Framework.  
The advantage of using a framework over bare CloudFormation is that you can use logic, conditions and loops when defining your infrastructure, so in short, it gives more flexibility when building it.  

In this example, we'll generate another EC2 instance.  

Download the following file to your "CloudAwarenessLab" folder:  

- [ec2_instance.py](./files/aws/ec2_instance.py)  

After you understood the logic of the file, let's deploy it now.

1. Generate CloudFormation from the Python script:  

        python ec2_instance.py > ec2_instance.yml

1. Deploy the CloudFormation stack:   

        aws cloudformation deploy --template-file ec2_instance.yml --stack-name <your-ldap-login>-ts-stack

### Follow up exercise

Package the EC2 Instance in a Launch Configuration that is referenced by an Auto Scaling Group. You can find an example [here](https://github.com/cloudtools/troposphere/blob/master/troposphere/autoscaling.py).  
Deploy this template and verify that that your EC2 instance is backed by an Autoscaling group (have a look at the instance tags).