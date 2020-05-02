pipeline {
    agent any

    stages {
        stage("Build") {
            steps {
                runFile("build.sh")
            }
        }
        stage("Test") {
            steps {
                runFile("test.sh")
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