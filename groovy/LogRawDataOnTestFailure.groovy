/** this script logs raw request and response data on test case failure,
* user need to provide the step name as shown below comment
* this can be used in teardown script of a test case
**/
import com.eviware.soapui.model.testsuite.TestRunner.Status

/*You need to provide the request step name(s) that you wanted to log*/

def testStepNames = ['Test Request1', 'Test Request2']

def logRawData = { stepName ->
     def step = testRunner.testCase.getTestStepByName(stepName)
     def rawRequest = new String(step.testRequest.messageExchange.rawRequestData)
     def rawResponse = new String(step.testRequest.messageExchange.rawResponseData)
     log.error rawRequest
     log.error rawResponse	
}

if (testRunner.status == Status.FAILED) {
  testStepNames.each { step -> logRawData(step) }
}
