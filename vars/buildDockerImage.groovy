def call(String microservice) {
    def pom = readMavenPom file: 'pom.xml'
    sh "echo Hello 000 ${pom}"
    def shortCommit = sh(returnStdout: true, script: "git log -n 1 --pretty=format:'%h'").trim()
    sh "echo Hello 1111 ${shortCommit}"
    def TicketNumber = sh(returnStdout: true, script: "git log -1 --pretty=format:%s | sed 's/.*\\[\\(.*\\)\\].*/\\1/'").trim()
    sh "echo Hello 2222 ${TicketNumber}"
    def TICKET
    if (TicketNumber.startsWith("ICE-")) {
        TICKET = "-${TicketNumber}"
        sh "echo Hello 333 ${TICKET}"
    } else {
        TICKET = "ICE-XXX"
        sh "echo Hello 444 ${TICKET}"
    }
    def buildNumber = currentBuild.number
    sh "echo Hello 555_111 ${TICKET}"
    sh "echo Hello 555 ${buildNumber}"
    def TAG = shortCommit + "-build-" + buildNumber
    sh "echo Hello 666 ${TAG}"

    script {
        docker.withRegistry('https://${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com', 'ecr:us-east-1:ecr') {
            sh "echo Hello 777 ${microservice}:${TAG}"
            sh "echo Hello 888 ${microservice}:${TAG}${TICKET}"
            def customImage = docker.build("${microservice}:${TAG}${TICKET}")
            //        customImage.push()
            sh "echo Hello 999 ${microservice}:${TAG}${TICKET}"
        }
    }

    return
}
