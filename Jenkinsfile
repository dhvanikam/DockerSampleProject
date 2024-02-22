pipeline {
    agent any  
    stages {
        stage('verify tooling') {
            steps {
                sh '''
          docker version
          docker info
          docker compose version
        '''
            }
        }

        stage('Clean running container') {
            steps {
                sh 'docker system prune -a --volumes -f'
            }
        }

        stage('Start container') {
            steps {
                retry(2) {
                    sh 'docker-compose -f docker-compose-v3.yml up -d --scale chrome=2 --scale firefox=2'
                    sh 'docker compose -f docker-compose-v3.yml ps'
                }
            }
        }

        stage('wait for container to start')
        {
            steps {
                sh 'sleep 5'
            }        
            
        }
        
        stage('Compile Stage') {
            steps {
                withMaven(maven:'MyMaven') {
                    sh 'mvn clean compile'
                }
            }
        }

        stage('Testing Stage') {
            parallel {
                stage('Test with chrome') {
                    steps {
                        withMaven(maven:'MyMaven') {
                            sh 'mvn test -Dbrowser=chrome -Dallure.results.directory=./target/chrome/allure-results'
                        }
                    }
                }

                stage('Test with firefox') {
                    steps {
                        withMaven(maven:'MyMaven') {
                            sh ' mvn test -Dbrowser=firefox -Dallure.results.directory=./target/firefox/allure-results'
                        }
                    }
                }
            }
        }
    }

    post {
        always {

            allure([includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [
                            [path: 'target/chrome/allure-results'],
                            [path: 'target/firefox/allure-results']
                        ]
                    ])
            sh 'docker compose -f docker-compose-v3.yml down --remove-orphans -v'
            
        }
    }
}
