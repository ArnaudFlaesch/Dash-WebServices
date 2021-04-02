pipeline {
	agent {
		docker { image 'openjdk:13-jdk' }
	}

  stages {
    stage('Build') {
      steps {
        sh '''gradle clean build -x test'''
      }
    }


    stage('Test jUnit') {
      steps {
        sh ' gradle test '
      }
    }



  post {
    always {
      junit 'build/test-results/**/*.xml'
    }
  }

}
