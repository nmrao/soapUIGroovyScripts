/**
 * Below is the setup script of the test case
 * which adds the headers to SOAP/REST/HTTP request type test steps of that test case
 */
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestRequestStep
import com.eviware.soapui.impl.wsdl.teststeps.RestTestRequestStep
import com.eviware.soapui.impl.wsdl.teststeps.HttpTestRequestStep
//define your headers as needed in the below statement
def headers = ['header1': ['header1 value'], 'header2': ['header2 value']]

def setHttpHeaders(def step, def headers) {
    def nextRequest = step.httpRequest
    def existingHeaders = nextRequest.requestHeaders
    headers.each {
        existingHeaders[it.key] = it.value
    }
    nextRequest.requestHeaders = existingHeaders
}

//calling the above method
testCase.testStepList.each { step ->
	if (step instanceof WsdlTestRequestStep 
				|| step instanceof RestTestRequestStep
				|| step instanceof HttpTestRequestStep) {
		setHttpHeaders(step, headers)
	}
}
