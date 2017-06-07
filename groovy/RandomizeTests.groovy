/**
* Refer:https://community.smartbear.com/t5/SoapUI-NG/Is-it-possible-to-randomize-test-steps-in-test-cases-using/td-p/143107/page/4
*
**/
import com.eviware.soapui.model.testsuite.Assertable.AssertionStatus

def map = [ 'Login Test1 -> Test2' : 'Login SID Test2 --> pushINID Test2',
        'Login Test2 -> Test1' : 'Login SID TPSP --> requestEmergencyData',
        'Login Test2 -> Test1' : 'Random UID --> pushINID Test1',
        'requestEmergencyData Test2 -> Test1' : 'Random_TSDI',
        'requestEmergencyData Test2 -> Test1' : 'requestEM UID --> pushEM',
        'requestEmergencyData Test2 -> Test1' : 'requestEM SID --> CLDWN',
        'pushEmergencyData Test1 -> Test2' : 'pushEM UID --> CLDWN',
        'pushEmergencyData Test1 -> Test2' : 'pushEM SID --> Test2 -> TPSP Logout',
        'clearDown Test2 -> Test1' : 'CLDWN SID --> Test2 Logout'
      ]

def runDependentStep = { stepName ->
	if (stepName in map.keySet()) {
		log.info "Found dependent test step : ${stepName}"
		context.testCase.getTestStepByName(map[stepName]).run(testRunner, context)		
	}
}

def randomize = { list -> Collections.shuffle(list); list  }

def project = testRunner.testCase.testSuite.project

randomize(project.testSuiteList)?.each { suite ->
	randomize(suite.testCaseList)?.each { kase ->
		randomize(kase.testStepList)?.each { step ->
			log.info "Test step : ${step.label}"
			(context.currentStep.name == step.label) ?: step.run (testRunner, context)
                        runDependentStep(step.label)
			if( (step.metaClass.hasProperty(step, 'assertionStatus')) && (step?.assertionStatus == AssertionStatus.FAILED)){
				log.info "${step.name} FAIL..."
				testRunner.cancel("TestStep failed")
				fail()
			}
		}
	}
}
