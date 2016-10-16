/**
 * Ths is the Script Assertion
 * which sets headers to the requested  targetted steps
 * by extracting header from currest step response
 * Refer: http://stackoverflow.com/questions/40059304/passing-jsessionid-from-a-soap-response-to-a-http-request-in-soap-ui/40060851#40060851
 **/
//Assert if response has headers
assert messageExchange.responseHeaders, "Response does not have any headers"

 //Specify all the headers to retrieve from current test step response as keys, target step request headers as values
 //key - current response header name
 //value - target request header name
 //Add more key, values into map if you need to extract and set more headers
def headerMap = ['Set-Cookie' : 'Cookie']
//Specify the test  step name for which headers to be set.
//Add call to setHttpHeaders with different test step names as needed to apply for more steps
setHttpHeaders('step2', headerMap)


/**
  * method sets headers to targeted step
  * step is the step name for which headers to be set
  * header map consists key, header name in the current step and value, header name to appear in the 
  * targeted step
  * 
  **/
def setHttpHeaders(def step, def headerMap) {    
    def nextRequest = context.testCase.testSteps[step]?.httpRequest
    def existingHeaders = nextRequest?.requestHeaders
    headerMap.each {
        existingHeaders[it.value] = getHttpHeaderValue(it.key)
    }
    nextRequest?.requestHeaders = existingHeaders
}

/**
 * method to retrieve the value of the specified header
 **/
def getHttpHeaderValue(def headerToLookup) {    
    if (messageExchange.responseHeaders.containsKey(headerToLookup)) {
        log.info "Found ${headerToLookup} in the response headers"
        return messageExchange.responseHeaders[headerToLookup]
    } else {
        log.warn "${headerToLookup} is not found in the response headers"
    }
    null
}
