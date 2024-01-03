/**
*https://community.smartbear.com/discussions/readyapi-questions/how-to-change-test-case-status-using-groovy/261461
*/
import com.eviware.soapui.model.testsuite.Assertable.AssertionStatus
import com.eviware.soapui.model.testsuite.TestStepResult.TestStepStatus
def maxAttempts = 7
def waiting = 10
def doubleIt = { it * 2 }
def currentWait = 1

//Get the test step just before the current groovy script step, which is GET REST call step
def step = context.testCase.testStepList[context.currentStepIndex-1]
def isStepFailed = {
	log.info "Executing test step : ${step.name}"
	def result = step.run(testRunner, context)
	log.info "Assertion : ${step.assertionStatus}"
	log.info "Step result: ${result.status}"
	result.status == TestStepStatus.FAILED ? true : false
}

/**
* This will first wait for 10 sec, then 20 sec, then 40 sec so that number calls will be reduced, you can change it as needed
*/
def waitFor = { 
     currentWait = (it == 1) ? currentWait : doubleIt(currentWait)
     log.info "Waiting for ${waiting * currentWait} seconds before attempting ${step.name} again"
    Thread.sleep(1000 * waiting * currentWait)   
}

for(item in 1..maxAttempts) {
	waitFor(item)
	log.info "Attempt: $item"
	if (!isStepFailed()) {
		break
	} else {
               //Test will be failed once GET call fails after exhaustings maxAttempts
		if (item == maxAttempts) assert false, "GET instance failed after $item attempts"
	}
}
return
