/** this script logs raw request and response data on test case failure,
* user need to provide the step name as shown below comment
**/
import com.eviware.soapui.model.testsuite.TestRunner.Status

def logRawData = { stepName ->
	def step = testRunner.testCase.getTestStepByName(stepName)
     def rawRequest = new String(step.testRequest.messageExchange.rawRequestData)
     def rawResponse = new String(step.testRequest.messageExchange.rawResponseData)
     log.error rawRequest
     log.error rawResponse	
}

if (testRunner.status == Status.FAILED) {
//You need to provide the request step name and you can repeat the same 
//statement with different step name in case of multiple steps for a test case
  logRawData('Test Request1')
  logRawData('Test Request2')
}
