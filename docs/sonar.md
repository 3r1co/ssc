# Static Code Analysis with Sonar and PMD (1 hour)

The first thing you need to scan in your application is your actual source code.

One very famous tool to do so is **Sonarqube**. There is already a Github Action available for this tool [here](https://github.com/SonarSource/sonarcloud-github-action
).

For this tutorial, you need to create an account on [sonarcloud.io](https://sonarcloud.io).

In this exercise, integrate this action into your Github workflow and do the validation before you actually deploy resources in AWS. Also, fix all issues that are reported through the scanner.


Another tool to scan your source for vulnerabilities is **PMD**. It can be good to use several different source code scanners in order to find the maximum number of vulnerabilities and improve the quality of your code.

There is already a Github Action available for PMD [here](https://github.com/marketplace/actions/pmd-source-code-analyzer-action).

Your exercise is to integrate this tool into your Github workflow.



