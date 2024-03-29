pipeline {
    agent any
    stages {
        stage('Pull and start database') {
            steps {
                sh 'docker pull postgres:13.2-alpine'
                sh 'docker run -p 5432:5432 --name database-test -d -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=dash_test postgres:13.2-alpine'
            }
        }

        stage('Backend tests') {
            agent {
                docker { image 'gradle:7.6.0-jdk17-alpine' }
            }
            stages {
                stage('Lint') {
                    steps {
                        sh 'gradle ktlintCheck'
                    }
                }

                stage('Build and test') {
                    steps {
                        sh 'gradle clean build -Dspring.profiles.active=test -Dspring.config.location=src/test/resources/application-test.properties'
                    }
                }
            }
        }
    }

    post {
        always {
          publishHTML([
              reportDir: 'build/reports/tests/test',
              reportFiles: 'index.html',
              alwaysLinkToLastBuild: true,
              keepAll: false,
              allowMissing : false,
              reportName: 'HTML Report'
          ])
          sh 'docker rm database-test'
          junit 'build/test-results/**/*.xml'
        }
    }

}
