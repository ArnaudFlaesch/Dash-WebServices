pipeline {
	agent {
		docker { image 'gradle:6.8.3-jdk15' }
	}

  stages {
    stage('Build') {
      steps {
        sh 'gradle clean build -x test'
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
