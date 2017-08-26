/**
* This is SubmitListener.beforeSubmit type
* Adds user header dynamically without losing other existing headers
* Refer : https://community.smartbear.com/t5/SoapUI-Pro/Adding-a-request-Header-in-all-rest-request-in-different-Suites/m-p/148177#M33754
**/

import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestRequestStep
import com.eviware.soapui.impl.wsdl.teststeps.RestTestRequestStep

/** Edit below as needed **/
//Add more headers as needed
//Below adds Authorization header
def userHeaders = ['Authorization': [context.expand('${#Project#token}')]]

//Define the possible test step types into below list
def stepTypes = [WsdlTestRequestStep, RestTestRequestStep]

/**  Do not Edit beyond this **/
//Get the current running step
def testStep = context.wsdlRequest.testStep

//Total headers : user header + existing headers
def headers =  userHeaders +  testStep.testRequest.requestHeaders

//Add the user headers for the defined step types only
if( stepTypes.any{testStep in it}  ) {
     log.info("Setting HTTP headers $headers in test case ${testStep.testCase.label}, step ${testStep.name} ")
     testStep.testRequest.requestHeaders = headers
}
