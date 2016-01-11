/**
 * Assuming that the Test Case has following Steps
 * 1. REST Request Step1 - this gets access token
 * 2. REST Request Step2 - user needs to add the above
 * access token as http headers and user conditions taken care by below script
 * In case if you have more steps ado so.
 * For this below is the proposed goovy script.
 * Follow the instructions carefully
 * Introduce the Groovy Script test step between Step 1 and Step 2 and copy below code
 * Add Test Case level custom properties as given below
 * ===========================================================================
 * NEXT_STEP_HEADER_NAME_FOR_TOKEN - value should be the http header name for sending access token
 * EXIT_STEP_NAME - provide any value
 * ===========================================================================
 * Add another Groovy Script test step as last step in this test case,
 * name the test step same as the value given for EXIT_STEP_NAME property
 * but this is just need for control of execution, there may or may not be
 * having any code in it or just use log statement saying test finished
 *
 * Finall test case will appear with below steps
 * 1. rest step to get token
 * 2. groovy script step with this code
 * 3. rest2 step using access token
 * 4. exit groovy script
 */
import com.eviware.soapui.model.testsuite.TestCaseRunContext
import net.sf.json.groovy.JsonSlurper
import org.apache.log4j.Logger

/**
 * Created by Rao on 1/11/2016.
 */

class ResponseCodes {
    String code
    String description
    boolean isNextStepToBeExecuted
}
class ConditionEvaluator {

    static Map<String, ResponseCodes> responseCodes

    static {
        responseCodes = new HashMap<>()
        responseCodes["0000"] =  new ResponseCodes(code: "0000", description: "Success")
        responseCodes["0058"] =  new ResponseCodes(code: "0058", description: "Profile Already Exist.", isNextStepToBeExecuted: false)
        responseCodes["5002"] =  new ResponseCodes(code: "5002", description: "Invalid Request: [JSV0007] Invalid string: '' does not match pattern '^d{1,5}\\\$'.", isNextStepToBeExecuted: true)
        //add here if there are more response codes
    }

    TestCaseRunContext context
    Logger log

    def parseAndEvaluateResponse(String response, String propertyName) {
        def currentStatusCode
        def currentDescription
        def currentResponseCode
        def toBeFound = true
        def jsonResonponse = new JsonSlurper().parseText(response)
        if (jsonResonponse.createProfileAccountResponse) {
            log.info("Found createProfileAccountResponse key")
            currentStatusCode = jsonResonponse.createProfileAccountResponse.statusCode
            currentDescription = jsonResonponse.createProfileAccountResponse.statusDescription
        } else if (jsonResonponse.response) {
            log.info("Found response key")
            currentStatusCode = jsonResonponse.response.header.statusCode
            currentDescription = jsonResonponse.response.header.statusDesc
            toBeFound = false
        }
        log.info("Status Code :${currentStatusCode}")
        log.info("Status Description :${currentDescription}")
        if (responseCodes.containsKey(currentStatusCode)) {
            log.info("Found existing statusCode")
            currentResponseCode = responseCodes.get(currentStatusCode)
            if (currentResponseCode.description == currentDescription) {
                log.info("Matched the description")
                if (toBeFound) {
                    if (jsonResonponse.createProfileAccountResponse.rxStatus.rxEncryptedToken) {
                        log.info("Found rxEncryptedToken and setting true for the next step execution")
                        currentResponseCode.isNextStepToBeExecuted = true
                        assert null != jsonResonponse.createProfileAccountResponse.rxStatus.rxEncryptedToken.toString(), "Toke is either empty or null"
                        context.testCase.setPropertyValue(propertyName, jsonResonponse.createProfileAccountResponse.rxStatus.rxEncryptedToken.toString())
                        log.info("Setting a test case level custom property ${propertyName}, this is going to contains token")
                    } else {
                        log.info("Could not found rxEncryptedToken and setting false for the next step execution")
                        currentResponseCode.isNextStepToBeExecuted = false
                    }
                }
            } else {
                log.error("Not matching description")
            }
        } else {
            log.error("Count not found the status code")
        }
        currentResponseCode.isNextStepToBeExecuted
    }
}

def setHttpHeaders(String nextStepName, def headers) {
    def nextRequest = context.testCase.testSteps[nextStepName].httpRequest
    def existingHeaders = nextRequest.requestHeaders
    headers.each {
        existingHeaders[it.key] = it.value
    }
    nextRequest.requestHeaders = existingHeaders
}

def previousStepName = context.testCase.getTestStepAt(context.currentStepIndex - 1).name
def nextStepName = context.testCase.getTestStepAt(context.currentStepIndex + 1).name
def exitStepName = context.testCase.getPropertyValue('EXIT_STEP_NAME')
def conditionalEvaluation = new ConditionEvaluator(context: context, log:log)
def isNextStepExecute = conditionalEvaluation.parseAndEvaluateResponse(context.expand('${'+previousStepName+'#Response}'), 'ACCESS_TOKEN')
/**
 * Assuming you need to set the access token as http header for next request.
 * Then use below lines, comment otherwise.
 */
def headers = [(context.testCase.getPropertyValue('NEXT_STEP_HEADER_NAME_FOR_TOKEN')): [(context.testCase.getPropertyValue('ACCESS_TOKEN'))]]
setHttpHeaders(nextStepName, headers)

/**
 * Based on below condition it will run or skip execution of Request Step 2
 * if true control will go to Request Step2
 * otherwise, control will be transferred to EXIT step directly
 */
if (isNextStepExecute) {
    testRunner.gotoStepByName(nextStepName)
} else {
    testRunner.gotoStepByName(exitStepName)
}