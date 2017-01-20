/**
* This is Project level TearDown Script
* which generates a test report in a file
* Refer: http://stackoverflow.com/questions/41700437/creating-a-test-report-from-project-level-tear-down-script
* Modify the variable "reportFileName" below
**/

//Modify the file as needed for report file
def reportFileName = '/tmp/abctestreport.txt'


//NOTE: Not required to edit beyond this point

/**
* This class holds the test case details
**/
class TestCaseResultHolder {
    def log
    Map<String, String> properties = [:]
    boolean status

    def createProperties(testCase) {
        testCase.getPropertyNames().each { key ->
            properties[key] = testCase.getPropertyValue(key)
        }       
    }

    def getCaseResult(caseRunner, caseName) {
        log.info "Checking test case status ${caseName}"
        if ( caseRunner.status.toString() == 'FAILED' ){
            log.error "Test case $caseName has failed"
            for ( stepResult in caseRunner?.results ){
                stepResult.messages.each() { msg -> log.info msg }
            }
            return false
        } else {
            log.info "${caseName} is passed"
        }
        true
    }

    def buildCaseResult(caseRunner, caseName) {
        status = getCaseResult(caseRunner, caseName)
        if (!status) {
            createProperties(caseRunner.testCase)
        }
    }

}

/**
* This class holds the test suite details
**/
class SuiteResultsHolder {

    def log
    Map<String, TestCaseResultHolder> casaeResults = [:]
    int testCaseCount = 0
    int passedCasesCount = 0
    int failedCasesCount = 0

    def buildSuiteResults(suiteRunner, suiteName){      
        log.info "Building results of test suite ${suiteName}"
        for ( caseRunner in suiteRunner?.results ) {
            def caseName = caseRunner.testCase.name
            testCaseCount++
            def tcHolder = new TestCaseResultHolder(log: log)
            tcHolder.buildCaseResult(caseRunner, caseName)          
            casaeResults[caseName] = tcHolder
            if (tcHolder.status) {
                passedCasesCount++
            } else {
                failedCasesCount++
            }
        }           
    }

    def getStatus() {
        (0 < failedCasesCount) ? false : true
    }

}

/**
* This class holds the project details
**/
class ProjectResultsHolder {

    def log
    Map<String, SuiteResultsHolder> suiteResults = [:]
    int suiteCount = 0
    int passedSuitecount = 0
    int failedSuiteCount = 0

    def buildProjectResults(projectRunner, projectName) {
        log.info "Building results of test project ${projectName}"          
        for(suiteRunner in projectRunner?.results) {
            def suiteName =  suiteRunner.testSuite.name
            suiteCount++
            def suiteResultsHolder = new SuiteResultsHolder(log: log)
            suiteResultsHolder.buildSuiteResults(suiteRunner, suiteName)
            suiteResults[suiteName] = suiteResultsHolder
            if (suiteResultsHolder.status) {
                passedSuitecount++
            } else {
                failedSuiteCount++
            }
        }
    }

    def getStatus() {
        (0 < failedSuiteCount) ? false : true
    }

}

//Get the status string based on boolean
def getResult(status){ status == true ? 'SUCCEED' : 'FAILED'}

//Draws a line
def drawLine(def letter = '=', def count = 70) { letter.multiply(count)}

//Gets the summary report
def getSummaryReport(project, projectResultHolder) {
    def report = new StringBuffer()
    report.append(drawLine()).append('\n')
    report.append("\t\t\tTest Execution Summary\n")
    report.append(drawLine('-', 60)).append('\n')
    report.append("Project : ${project.name}\n")
    report.append("Result : ${getResult(projectResultHolder.status)}\n")
    report.append("Total test suites executed: ${projectResultHolder.suiteCount}\n")
    report.append("Test suites passed: ${projectResultHolder.passedSuitecount}\n")
    report.append("Test suites failed: ${projectResultHolder.failedSuiteCount}\n")
    report.append(drawLine()).append('\n')
    report
}

//Gets the test case report
def getTestCaseReport(testCaseReport) {
    def report = new StringBuffer()
    report.append(drawLine('-', 60)).append('\n')
    report.append("\t\tTest Case Details:\n")
    report.append(drawLine('-', 60)).append('\n')
    testCaseReport.each { kase, tcReport ->
        report.append("Name : ${kase}\n")
        report.append("Status : ${getResult(tcReport.status)}\n")
        if (!tcReport.status) {
            report.append("Properties : ${tcReport.properties.toString()}\n")
        }
    }
    report
}

//Get the detailed report
def getDetailedReport(projectResultHolder) {
    def report = new StringBuffer()
    report.append(drawLine()).append('\n')
    report.append("\t\t\tTest Execution Detailed Report\n")
    report.append(drawLine()).append('\n')
    projectResultHolder.suiteResults.each { suite, details ->
        report.append("Test Suite : ${suite}\n")
        report.append("Result : ${getResult(details.status)}\n")
        report.append("Total Cases : ${details.testCaseCount}\n")
        report.append("Cases Passed : ${details.passedCasesCount}\n")
        report.append("Cases Failed: ${details.failedCasesCount}\n")
        report.append(getTestCaseReport(details.casaeResults))
        report.append(drawLine()).append('\n')
        report.append(drawLine()).append('\n')
    }
    report
}

//Save the contents to a file
def saveToFile(file, content) {
    if (!file.parentFile.exists()) {
         file.parentFile.mkdirs()
         log.info "Directory did not exist, created"
    }
    file.write(content) 
    assert file.exists(), "${file.name} not created"
}

def holder = new ProjectResultsHolder(log: log)
holder.buildProjectResults(runner, project.name)

def finalReport = new StringBuffer()
finalReport.append(getSummaryReport(project, holder))
finalReport.append(getDetailedReport(holder))

def reportFile = new File(reportFileName)
saveToFile(reportFile, finalReport.toString())
