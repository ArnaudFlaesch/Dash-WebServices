pipeline {
	agent {
		docker { image 'gradle:jdk11' }
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
	}



  post {
    always {
      junit 'build/test-results/**/*.xml'
    }
  }

}
