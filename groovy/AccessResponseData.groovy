/**
*This is groovy script to access the Response Object of a rest request test step and 
* then access the json data using JsonSlurper.
* Also access required response headers
* For more details, refer:
* https://community.smartbear.com/t5/API-Functional-Security-Testing/Is-there-an-easy-way-to-access-parts-of-an-API-response-in-a/td-p/213831
*
*/

//No need to modify below part

//Closure to get the response object of the given test step 
def getStepResponse = { stepName ->
	context.testCase.testSteps[stepName].httpRequest.response
}

//Closure to get the json object of the given test step response
def getJson = { stepName ->
	new groovy.json.JsonSlurper().parseText(getStepResponse(stepName).responseContent)
}

//Clusure to get the response header of the given test step name
def getHeader = { stepName, header ->
	getStepResponse(stepName).responseHeaders[header].first()
}

// User can modify below as per his/her test

//Assuming the test step name and its response as below

//the test step name user like to access
def step = 'addPet_JSON_200_OK'

//Example user json response; used in the assertions as sample
/** 
 *  {"result": "OK"}
 *  /
 */

assert 'application/json' == getHeader(step, 'Content-Type')
//Example user json response
/** 
 *  {"result": "OK"}
 *  /
 */
assert 'OK' == getJson(step).result
