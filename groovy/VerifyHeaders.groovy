/**
* this script can verify given http headers both in request and response
* checks if given HEADER_NAMES in request vs response
* also its value and size
* Assumes, a custom test case property HEADER_NAMES is defined with 
* appropriate value(s) separated by comma like Content-Type, Cookie etc., any thing for that matter
* This needs to used as Script Assertion
**/
//Aso published the same at
//http://community.smartbear.com/t5/SoapUI-NG/Fetch-cookies-from-soapUI-Request-and-Response-and-reuse-for/m-p/109182#U109182
import groovy.transform.Canonical
@Canonical
class HeaderVerificationResult {
	String name
	def requestValue
	def responseValue
	def failureMessage = new StringBuilder()
	boolean result = false
}

Map<String, HeaderVerificationResult> verificationMap = [:]
def keysString = context.testCase.properties['HEADER_NAMES'].value
if (keysString) {
	keysString.split(',').each { key ->
		verificationMap[key] = buildModel(key)
	}
} else { throw new Error('No header names provided') }

def testResultFail = verificationMap.findAll{ it.value.result == false }
def errorMessage = StringBuilder()
testResultFail.each {
	log.info "Verifying Header : ${it.key}"
	errorMessage.append("\n${it.value.toString()}")
	log.info "Failure${it.value.failureMessage.toString()}"
}
if (errorMessage) { throw new Error(errorMessage.toString())}

def buildModel = { key ->
	def currentResultObject = new HeaderVerificationResult(name: key)
	addMessageIfNotContainsHeader(messageExchange.requestHeaders.containsKey(key), 'Request', currentResultObject)
	addMessageIfNotContainsHeader(messageExchange.responseHeaders.containsKey(key), 'Response', currentResultObject)
	if (currentResultObject.requestValue != currentResultObject.requestValue) {
		currentResultObject.failureMessage.append('\nValue mismatch')
	} 
	if (currentResultObject.requestValue.size() != currentResultObject.requestValue..size()) {
		currentResultObject.failureMessage.append('\nValue size mismatch')
	} 
	if (!currentResultObject.failureMessage) ) {
		currentResultObject.result = true
	}
	currentResultObject
}

def addMessageIfNotContainsHeader = { value, message,  object ->	
	object.failureMessage
	if (!value) {
		object.failureMessage.append("\n${message} does not contain header")
	} else {
		if ('Request' == message) {
			object.requestValue = messageExchange.requestHeaders[object.name].value
		} else if ('Response' == message) {
			object.responseValue = messageExchange.responseHeaders[object.name].value
		}
	}
}
