# CI with Github Actions and ECR (1 hour)

This exercise is building up on exercise 1.4 (Github Actions).

ECR is the Elastic Container registry of AWS. We will use it throughout this course, as it integrates very well with ECS and EKS.

**Attentation**: For this exercise, you will have to create an IAM user in AWS according to [this](https://docs.aws.amazon.com/IAM/latest/UserGuide/id_users_create.html) guide and provision credentils according to this [this](https://github.com/marketplace/actions/configure-aws-credentials-action-for-github-actions) tutorial.

**Performing this step is absolutely necessary for most of the following exercises.**

The goal of this exercise is to extend your your Github Actions to:

- Log in to the AWS ECR (see documentation [here]())
- Push your image with a unique tag to ECR (use the `GITHUB_SHA` environment variable)