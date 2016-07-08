/**
 * This script updates value of Response SLA assertion in the soapui project
 * with value mentioned for 'newSLA' variable below which 
 * currently assigns project level custom property call RESPONSE_TIME_SLA and
 * you need to define it with required value for the request steps of type
 * SOAP, REST, JDBC, HTTP
 */
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestRequestStep
import com.eviware.soapui.impl.wsdl.teststeps.RestTestRequestStep
import com.eviware.soapui.impl.wsdl.teststeps.JdbcRequestTestStep
import com.eviware.soapui.impl.wsdl.teststeps.HttpTestRequestStep
import com.eviware.soapui.impl.wsdl.teststeps.assertions.basic.ResponseSLAAssertion
//update the new value as needed
def newSLA = '\${#Project#RESPONSE_TIME_SLA}'
//Get the project object
def project = context.testCase.testSuite.project

//Closure to update the Response SLA assertion value
def updateAssertionSLA = { assertion, sla ->
	if (assertion instanceof ResponseSLAAssertion) {
		log.info "Found a request step assertion with Response SLA type, and updating its value"
		assertion.setSLA(sla)
	}
}
//Actual script that traverse thru the project -> suite -> case -> step
project.testSuiteList.each { suite ->
	log.info "Looking into test suite: ${suite.name}"
	suite.testCaseList.each { tCase ->
		log.info "Looking into test case: ${tCase.name}"
		tCase.testStepList.each { step ->
			log.info "Looking into test step: ${step.name}"
			if (step instanceof WsdlTestRequestStep 
				|| step instanceof RestTestRequestStep
				|| step instanceof JdbcRequestTestStep
				|| step instanceof HttpTestRequestStep) {
				log.info "Found a request step of required type "
				def assertions = step.assertionList
				assertions.each{ updateAssertionSLA(it, newSLA) }
			}
		}
	}
}
