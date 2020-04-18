# CI with Github Actions and Docker (1 hour)

This exercise is building up on exercise 1.4 (Github Actions).

The goal of this exercise is to push your previously built Docker image to the public Docker hub: hub.docker.com

**Performing this step is absolutely necessary for most of the following exercises.**

The goal of this exercise is to extend your your Github Actions to:

- Use the Github Action provided by Docker (see documentation [here](https://www.docker.com/blog/first-docker-github-action-is-here/))
- Push your image with a unique tag to hub.docker.com (use the `GITHUB_SHA` environment variable)