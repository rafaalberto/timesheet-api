pipeline {
    agent any

    stages {
        stage("Build") {
            steps {
                deleteDir()
                runFile("build.sh")
            }
        }
        stage("Test") {
            steps {
                runFile("test.sh")
            }
        }
        stage("Migration") {
            steps {
                runFile("migration.sh")
            }
        }
        stage("Deploy") {
            steps {
                runFile("docker.sh")
            }
        }
    }
}

def runFile(filename) {
    try {
        def pwd = pwd()
        sh "bash ${pwd}/pipeline/${filename}"
    } catch (exception) {
        echo "Error to execute ${filename}"
        throw exception
    }
}