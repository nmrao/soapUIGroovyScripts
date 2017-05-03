/**
* Refer http://stackoverflow.com/questions/43732160/update-rest-request-data-based-on-the-previous-step-response-data/43733782#43733782
* Below is the script assertion
* reads current response, extract certain data, and update this data into next test step request
* full script with data or for demo, refer : https://groovyconsole.appspot.com/script/5136269650690048
**/
import groovy.json.JsonSlurper
import groovy.json.JsonBuilder

//Provide the name of the next request test step name where you need to override the content
def nextStepName = 'step2'

//DO NOT REQUIRE TO MODIFY 
//Check the current step response
assert context.response, 'Response is empty'

def json = new JsonSlurper().parseText(context.response)
def products = json.response.products
log.info "Products details from current response: $products"

//Get the next test step request
def nextStepRequest = context.testCase.testSteps[nextStepName].httpRequest

//Check if the next step request content is empty
assert nextStepRequest.requestContent, "Next step, $nextStepName, request is empty"

def slurped = new JsonSlurper().parseText(nextStepRequest.requestContent)
def builder = new JsonBuilder(slurped)

//Change the products of next request
builder.content.products = products.inject([]){l, item -> def map = [:];map['id'] = item.id; l << map; l}

//Update the product details in the request
nextStepRequest.requestContent = builder.toPrettyString()
log.info "Updated request for the step ${nextStepName} is : ${nextStepRequest.requestContent}"
