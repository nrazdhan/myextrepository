pipeline {
  agent any
  environment {
    NEW_VERSION = '1.3.0'
    SERVER_CREDENTIAL = credentials('firstmultipipetest')
  }
  stages {
    stage("build") {
      steps {
        echo 'building the application...'
        echo "building the application version : ${NEW_VERSION}"
      }
    }
    
    stage("test") {
      when {
        expression {
          BRANCH_NAME == 'My2ndbranch' || BRANCH_NAME == 'dev'
        }
      }
      steps{
        echo 'testing the application...'
      }
    }

    stage("deploy") {
      steps{
        echo 'deploying the application...'
        echo "deploying the version: ${NEW_VERSION}"
        echo "server credentials ${SERVER_CREDENTIAL}"
      }
    }
  }
  post {
    always {
      echo 'always post processing..'
    }
    success {
      echo 'success post processing..'
    }
  }
}
