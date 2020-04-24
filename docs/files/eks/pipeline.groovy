pipeline {
  agent {
    kubernetes {
      //cloud 'kubernetes'
      yaml """
kind: Pod
metadata:
  name: kaniko
spec:
  containers:
  - name: kaniko
    image: gcr.io/kaniko-project/executor:debug-539ddefcae3fd6b411a95982a830d987f4214251
    imagePullPolicy: Always
    command:
    - cat
    tty: true
    volumeMounts:
      - name: docker-config
        mountPath: /kaniko/.docker
  - name: alpine
    image: alpine
    imagePullPolicy: Always
    command:
    - cat
    tty: true
  volumes:
    - name: docker-config
      configMap:
        name: docker-config
"""
    }
  }
  stages {
    stage('Build with Kaniko') {
      steps {
        git 'https://github.com/3r1co/juice-shop'
        container(name: 'kaniko') {
            sh '''
            /kaniko/executor --dockerfile `pwd`/Dockerfile --context `pwd` --destination=542954954204.dkr.ecr.us-east-1.amazonaws.com/sample-microservice:latest --destination=542954954204.dkr.ecr.us-east-1.amazonaws.com/sample-microservice:v$BUILD_NUMBER
            '''
        }
        container(name: 'alpine') {
            sh '''
            apk add --update -X http://dl-cdn.alpinelinux.org/alpine/edge/testing kubernetes
            sed -i "s#IMAGE_NAME#542954954204.dkr.ecr.us-east-1.amazonaws.com/sample-microservice#g" kustomization.yaml
            sed -i "s/IMAGE_TAG/v$BUILD_NUMBER/g" kustomization.yaml
            kubectl kustomize . | kubectl apply -f -
            '''
        }
      }
    }
  }
}
