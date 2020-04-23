# Docker Vulnerability Scanning with Clair (1 hour)

So far, we uncovered vulnerabilities in

- your application source
- third party libraries in your application
- your infrastructure as code

Another vulnerability factor can be the libraries that are integrated in your operating system, or in the case of Docker, your Docker image.

A famous open source vulnerability scanner for Docker images [CoreOS Clair](https://github.com/quay/clair).

For the purpose of this course, there is centralized Clair instance running at clair.3r1.co .

Your task is now to perform a vulnerability scan of your Docker image with [klar](https://github.com/optiopay/klar/releases).

