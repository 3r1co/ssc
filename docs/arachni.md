# Dynamic Application Security Scanning (1 hour)

Similar to ZAP Proxy, [Arachni](https://www.arachni-scanner.com) is a DAST tool that is intended to run on the command line.

Unfortunately, there is no Github Action available for this tool, so your task today is to write your own Github Action, including Arachni and performing a scan on your application.

A good starting point is [here](https://help.github.com/en/actions/building-actions/creating-a-docker-container-action).

To succeed, you will have to build a Docker image containing arachni with an entrypoint as described in the documentation.