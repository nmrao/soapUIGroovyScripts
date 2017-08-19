/**
* This is SubmitListener.beforeSubmit type
* Adds user header dynamically without losing other existing headers
* Refer : https://community.smartbear.com/t5/SoapUI-Pro/Adding-a-request-Header-in-all-rest-request-in-different-Suites/m-p/148177#M33754
**/

import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestRequestStep
import com.eviware.soapui.impl.wsdl.teststeps.RestTestRequestStep
def testStep = context.wsdlRequest.testStep
//Add more headers as needed
//Below adds Authorization header
def userHeaders = ['Authorization': [context.expand('${#Project#token}')]]
//Total headers : user header + existing headers
def headers =  userHeaders +  testStep.testRequest.requestHeaders
if( testStep instanceof WsdlTestRequestStep || testStep instanceof RestTestRequestStep  ) {
      log.info("Setting HTTP headers $headers in test case ${testStep.testCase.label}, step ${testStep.name} ")
      testStep.testRequest.requestHeaders = headers
}
