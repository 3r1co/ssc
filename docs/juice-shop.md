# Juice Shop (1 hour)

OWASP Juice Shop is probably the most modern and sophisticated insecure web application! It can be used in security trainings, awareness demos, CTFs and as a guinea pig for security tools! Juice Shop encompasses vulnerabilities from the entire OWASP Top Ten along with many other security flaws found in real-world applications!

In this lab, you will deploy the Juice Shop, which we will use in later tutorial to analyze weaknesses.

To do so:

1. Fork [this](https://github.com/bkimminich/juice-shop) repository.
1. Create a Github Workflow that builds the Juice Shop image and pushes it to docker.io
1. Deploy the previously built Juice Shop in your EKS cluster
    - Use the files `customization.yaml` and `deployment.yaml` from your previous exercise and adapt the image name
