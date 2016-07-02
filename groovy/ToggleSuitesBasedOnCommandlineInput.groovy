/**
* http://stackoverflow.com/questions/38147995/how-to-select-the-test-suites-dynamically-in-soap-ui-based-on-the-command-line-a
* this script reads System property say EXECUTION_GROUP as command line options
* and toggle to test suites according to the user input
* if list is found enable the suite, disable it otherwise.
* if system property is set, then all the suites are enabled
**/ 
//Below closure toggle the suite based on the list of names
def toggleSuite = { suite, list ->
   def groups = suite.getPropertyValue('EXECUTION_GROUP').split(',').collect{it.trim()}
   def isGroupFound = false
   list.each { group -> 
      if (groups.contains(group)) {
          isGroupFound = true
      }
   }
   if (!isGroupFound) {
      suite.disabled = true
   } else {
      suite.disabled = false
   }    
}

//Reads the system property
def userInput = System.getProperty('EXECUTION_GROUP')
log.info "Command line input: $userInput"
def cmdLineOptions = []

//Checks if the user provided value is null i.e., system property not set
if (null != userInput) {
    cmdLineOptions = userInput.split(',').collect{it.trim()}
    if (null != cmdLineOptions) {
        log.info "User has provided the execution group as input"
        log.info cmdLineOptions
        project.testSuiteList.each { suite -> toggleSuite(suite, cmdLineOptions) }
    } else {
        log.info "Receieved empty list of options, so disabling all the suites"
        project.testSuiteList.each { suite -> suite.disabled = true }
    }
} else {
    log.info "All suites are being enabled as no system property input found"
    project.testSuiteList.each { suite -> suite.disabled = false }
}
