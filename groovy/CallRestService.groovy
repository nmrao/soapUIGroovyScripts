/**
* Groovy script which can call REST Service with
* Template and Varying query parameters (including keys & no of params)
*
* /
import wslite.rest.*
def serviceHost = 'http://petstore.swagger.io' 
//Below to handle template parameter, additional $ required before
def getPetIdPath = '''/v2/pet/${petId}'''
//define all template param values as shown below
def binding = [petId : 6598053714149417000]

def template = new groovy.text.SimpleTemplateEngine().createTemplate(getPetIdPath)

def queryParams = [:]
//Get the properties of Property Test step
context.testCase.testSteps["Properties"].properties.each {
	queryParams[it.key] = it.value.value
}
def client = new RESTClient(serviceHost)
def response = client.get(path: template.make(binding) as String,
					 accept: ContentType.JSON,
					 query : queryParams
					 )
assert response.statusCode == 200
log.info groovy.json.JsonOutput.prettyPrint(response.text)
