pipeline {
    agent any
    stages {
        stage('Pull and start database') {
            steps {
                sh 'docker pull postgres:13.2-alpine'
                sh 'docker run postgres:13.2-alpine -e POSTGRES_DATABASE=postgres -e POSTGRES_PASSWORD=postgres -d'
            }
        }

        stage('Tests') {
            agent {
                docker { image 'gradle:7.0.0-jdk16' }
            }
            stages {
                stage('Build') {
                    steps {
                        sh 'gradle clean build -x test'
                    }
                }

                stage('Lint') {
                    steps {
                        sh 'gradle ktlintCheck'
                    }
                }

                stage('Test jUnit') {
                    steps {
                        sh ' gradle test '
                    }
                }
            }
        }
    }

    post {
        always {
          junit 'build/test-results/**/*.xml'
        }
    }

}
