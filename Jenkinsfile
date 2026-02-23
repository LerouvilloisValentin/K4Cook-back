pipeline {
  agent any
  tools {
    maven 'Maven 3.9.6'
  }  
  stages {
    stage ('checkout'){
      steps {
       git branch: 'main',
           url: 'https://github.com/k4cook/K4Cook-back'
      }  
    }
    stage ('Build') {
     steps {
       sh 'mvn clean package'
     }
    }
    stage('Test'){
     steps{
       echo("Lancement des test unitaire")
       sh 'mvn test'
     }
    }  
    stage('Deploy') {
     steps {
       script {
        def mvnHome = tool 'Maven 3.9.6' //
        withSonarQubeEnv('SonarQ') {
        sh "${mvnHome}/bin/mvn clean verify sonar:sonar -Dsonar.projectKey=K4Cook-back -Dsonar.projectName='K4Cook-back'"
       }
      }
     }   
    } 
  }
}
