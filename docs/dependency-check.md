# Static Code Analysis with OWASP Dependency Check (1 hour)

After scanning your source code with Sonar, we now need to analyze the dependencies of your application, as they can also introduce vulnerabilities to your application.

One very famous tool to do so is **OWASP Dependency Check**. There is already a Github Action available for this tool [here](https://github.com/Sburris/dependency-check-action).

In this exercise, integrate this program into your Github workflow and do the validation before you actually deploy resources in AWS.

You need to save your report with the following instruction:

    - name: Archive dependency check reports
      uses: actions/upload-artifact@v1
      with:
        name: reports
        path: reports

Attention: You will have to install the Node.JS dependencies before running the actual dependency check. You can do this by adding the following lines to your workflow after the checkout action:

    - uses: actions/checkout@v2
    - name: Install NPM dependencies
      run: |
        npm install --production --unsafe-perm
