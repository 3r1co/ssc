# Static Code Analysis with OWASP Dependency Check (1 hour)

After scanning your source code with Sonar, we now need to analyze the dependencies of your application, as they can also introduce vulnerabilities to your application.

One very famous tool to do so is **OWASP Dependency Check**. There is already a Github Action available for this tool [here](https://github.com/Sburris/dependency-check-action).

n this exercise, integrate this program into your Github workflow and do the validation before you actually deploy resources in AWS. Also, fix all issues that are reported through the scanner.

