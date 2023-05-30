def call() {
    def pom = readMavenPom file: 'pom.xml'
    sh "echo Hello 000 ${pom}"
    def shortCommit = sh(returnStdout: true, script: "git log -n 1 --pretty=format:'%h'").trim()
    sh "echo Hello 1111 ${shortCommit}"
    def TicketNumber = sh(returnStdout: true, script: "git log -1 --pretty=format:%s | sed 's/.*\\[\\(.*\\)\\].*/\\1/'").trim()
    sh "echo Hello 2222 ${TicketNumber}"
    if (TicketNumber.startsWith("ICE-")) {
        def TICKET = "-${TicketNumber}"
        sh "echo Hello 333 ${TICKET}"
        return TICKET
    } else {
        def TICKET = "ICE-XXX"
        sh "echo Hello 444 ${TICKET}"
        return TICKET
    }
    def buildNumber = currentBuild.number
    sh "echo Hello 555 ${buildNumber}"
    def TAG = shortCommit + "-build-" + buildNumber
    sh "echo Hello 666 ${TAG}"
    return
}
