/**
* Ref: https://stackoverflow.com/questions/48061616/can-json-string-format-be-converted-to-actual-format-using-groovy/48071723#48071723
*
**/

//Provide the test step name where you want to add the request
def nextStepName = 'step4'

def setRequestToStep = { stepName, requestContent ->
    context.testCase.testSteps[stepName]?.httpRequest.requestContent = requestContent   
}

//Check the response
assert context.response, 'Response is empty or null'
setRequestToStep(nextStepName, context.response)
