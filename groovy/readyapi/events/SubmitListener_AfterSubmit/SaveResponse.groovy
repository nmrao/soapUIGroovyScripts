/**
* This SubmitListener.afterSubmit Event script saves responses automatically to the disk for SOAP and REST type test steps
* at given directory - project property "LOCATION"; use file path separated by / even on windows like C:/Users/apps/Data
*
* Ref: https://community.smartbear.com/t5/SoapUI-Pro/Saving-several-xml-responses/m-p/148270#M33805
**/

import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestRequestStep
import com.eviware.soapui.impl.wsdl.teststeps.RestTestRequestStep

//Save the contents to a file
def saveToFile(file, content) {
	if (!file.parentFile.exists()) {
	    file.parentFile.mkdirs()
	    log.info "Directory did not exist, created"
	}
	file.write(content) 
	assert file.exists(), "${file.name} not created"
}


def directoryToSave = context.expand('${#Project#LOCATION}')

//Define the possible test step types into below list
def stepTypes = [WsdlTestRequestStep, RestTestRequestStep]

//Get the current running step
def testStep = context.wsdlRequest.testStep

if( stepTypes.any{testStep in it}  ) {
     log.info("Saving test data for test case ${testStep.testCase.label}, step ${testStep.name} ")
     def millies = System.currentTimeMillis()
     //Decide the file extension based on response content type
     def ext = (testStep.testRequest.response.responseHeaders['Content-Type'][0]).contains('json') ? '.json' : '.xml'
     //Create the file name where to save the response
     def responseFilePath = "${directoryToSave}/Response_${testStep.testCase.label}_${testStep.name}_${millies}${ext}" as String
     saveToFile(new File(responseFilePath), testStep.testRequest.response.responseContent)
}
