/**
 * This groovy script is to find 
 * the details of groovy script test step 
 * which has certain string defined by the user
 * in the variable "key"
 **/
import com.eviware.soapui.impl.wsdl.teststeps.WsdlGroovyScriptTestStep
//Define the string to be searched for in a script
def key = 'db query'

/**
 * Don't edit beyond this point
 **/
def stepTypes = [WsdlGroovyScriptTestStep]
def project = context.testCase.testSuite.project
def currentStepMap = [ suite : context.testCase.testSuite.name, case  : context.testCase.name, step  : context.currentStep.name ]
def msg = new StringBuffer()
def logMatchingScript = { suite, kase, step ->  
	def tempMap = [suite : suite.name, case : kase.name, step: step.name]	
	def result = currentStepMap != tempMap ? true : false
	if (result &&(stepTypes.any{step in it}) && (step?.script?.contains(key)) ) {
		msg.append(tempMap.toString())
	}
}

project.testSuiteList.each { suite ->
	suite.testCaseList.each { kase ->
		kase.testStepList.each { step ->
			logMatchingScript(suite, kase, step)
		}
	}	
}
if (msg.toString()) {
	log.info "Matching details: ${msg.toString()}"
} else {
	log.info "No matching steps"
}
