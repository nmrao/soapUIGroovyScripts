/**
* below method script will set http headers to the given test step with in the same test case
* user need to pass the test step name, and headers map<String, List<String>>
**/
def setHttpHeaders(String stepName, def headers) {
    def nextRequest = context.testCase.testSteps[stepName].httpRequest
    def existingHeaders = nextRequest.requestHeaders
    headers.each {
        existingHeaders[it.key] = it.value
    }
    nextRequest.requestHeaders = existingHeaders
}

//calling the above method
def headers = ['header1': ['header1 value'], 'header2': ['header2 value']]
setHttpHeaders('REST Request', headers)
