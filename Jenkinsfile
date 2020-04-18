#!/usr/bin/groovy
node ('kubernetes'){

        sh """
        git config --system http.sslVerify false    
        git config --global http.sslVerify false
        """
	  
		gitCheckout {}

        buildDocker {}

		forgePodTemplate.deploymentToolsPod {
			sh "kubectl set image deployment/training training=${forgeUtils.getDefaultDockerImageName()}"
		}
        
}