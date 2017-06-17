/**
* This script changes the Run Test Case dynamically as per the user input to the following variables. Change the values as needed.
* @targetTestSuite
* @targetTestCase
* Place this groovy script test step before the Run Test Case test step in the existing test case
**/

import com.eviware.soapui.impl.wsdl.teststeps.WsdlRunTestCaseTestStep
def targetTestSuite = 'TestSuite 8'
def targetTestCase = 'TestCase2'
def kase = context.testCase.testSuite.project.testSuites[targetTestSuite]?.testCases[targetTestCase]
def step = context.testCase.testStepList[context.currentStepIndex +1]
if(step instanceof WsdlRunTestCaseTestStep && kase){
	step.targetTestCase = kase
}
