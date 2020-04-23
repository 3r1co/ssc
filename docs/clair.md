# Docker Vulnerability Scanning with Clair (1 hour)

So far, we uncovered vulnerabilities in

- your application source
- third party libraries in your application
- your infrastructure as code

Another vulnerability factor can be the libraries that are integrated in your operating system, or in the case of Docker, your Docker image.

A famous open source vulnerability scanner for Docker images [CoreOS Clair](https://github.com/quay/clair).

For the purpose of this course, there is centralized Clair instance running at clair.3r1.co .

Your task is now to perform a vulnerability scan of your Docker image with [klar](https://github.com/optiopay/klar/releases).

Here a way to run the clair scan:

    - name: use klar
      run: |
        wget https://github.com/optiopay/klar/releases/download/v2.4.0/klar-2.4.0-linux-amd64 -O klar
        chmod +x klar
        CLAIR_ADDR=http://clair.3r1.co:6060 ./klar docker.io/<your-username>/<your-image>:<sha-tag>

