# Docker Lab (1 hour)

The goal of this exercise is to build your first Docker container.
At the end of the lab, you should have

* a functioning web server based on Node.JS
* packaged this web server in a Docker image
* a running a Docker container that you can access from your workstation

To perform this lab:

1. Verify that you can log in to the global Docker Registry:  

        docker login

1. Create a directory for this training on your Desktop (e.g. "CloudAwarenessLab"). Download the following files in this directory:

    - [Dockerfile](../files/docker/Dockerfile) (the file to build the Docker image)
    - [app.js](./files/docker/app.js) (the application source code)

1.  In your terminal, position yourself in the directory:

        cd c:\Users\<loginldap>\Desktop\CloudAwarenessLab
   
1. Open the previously downloaded file "Dockerfile" in an editor:
    
    - the first line states `FROM node:alpine`
        - In case you wouldn't specify a tag (:alpine), Docker will default to "latest". It is a common best-practice to always specify a tag when referencing to an image.
    - you'll also see one line stating `ADD app.js .`
        - In this step the source code of your Node.JS server is added into `.` directory, so in the current working directory.
    - you'll also a line with the statement `ENTRYPOINT`. The entrypoint is the command that is run on your Docker container with the it is launched with the `docker run` command.


1. Launch the image build process:  

        docker build -t mywebserver:1.0 .

1. The Docker Engine has now built a new Docker image and you can find it in your local registry. To do so, type the following in your terminal:  

        docker images

1. Run the previously built image:  

        docker run -d --rm -p 3000:3000 mywebserver:1.0

    The id of the container is returned. The container is started in the background.

    **Notes on the parameters:**  

      - "**-d**" instructs Docker to run the container as a daemon, so in the background
      -  "**--rm**" instructs Docker to delete the container ones it is stopped. Like that, the local registry will not be poluted with "intermediate" containers
      - "**-p 3000:3000**" instructs Docker to expose the containers port 3000 on port 3000 on the host (**Attention**: in our case, the host is the Minikube VM)
    

1. You can check the logs of your running container with:  

        docker logs <id of the container>

1. Your container is running in the VM created by Minikube. As you exposed the port 3000 in the previous step, we now need to find the IP address if your Minikube VM:  

        minikube ip 


1. In your browser, open [http://minikube-ip:3000](http://<minikube-ip>:3000).  
   You should see "Hello World" now.

1. Once you are satisfied with the result, you can transfer your image from local Docker registry to the global Docker registry. Do do so, tag your image and push it to docker.io:

        docker tag mywebserver:1.0 <username>/mywebserver:1.0 
        docker push <username>/mywebserver:1.0

    **Attention: For this to work, you will need an account on hub.docker.com**

1. You can now stop your container (with the id of the container that was returned to you when you ran it)  

        docker stop  <id of the container>

## Follow up exercise (30 Minutes)

After you learned how to build, run and push a Docker image, try to build the same application you saw here in another programming language (preferrably Java).