pipeline {
    agent any

    stages {
        stage("Build") {
            steps {
                echo "building project..."
                sh "./gradlew clean build -x test"
            }
        }
        stage("Test") {
            steps {
                echo "testing project..."
                sh "./gradlew test --info --stacktrace"
            }
        }
    }
}