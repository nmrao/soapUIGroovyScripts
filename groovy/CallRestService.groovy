/**
* Groovy script which can call REST Service with
* Template and Varying query parameters (including keys & no of params)
* Refer: https://community.smartbear.com/t5/SoapUI-Pro/A-bit-more-groovy-help-Populating-a-REST-URI-with-Properties-Set/m-p/177766/highlight/false#M40455
* Basically, SoapUI does not allow dynamic names for query parameter names. This script is to overcome that.
* /
import wslite.rest.*
//Change the host as per your environment
def serviceHost = 'http://petstore.swagger.io' 

//Below to handle template parameter, additional $ required before
//The value is nothing but value that you see in the Resource 
def path = '''/v2/pet/${petId}'''

//define all template param values as shown below and these gets replaced in the actual call
def binding = [petId : 6598053714149417000]

def template = new groovy.text.SimpleTemplateEngine().createTemplate(path)

def queryParams = [:]
//Get the properties of Property Test step and put them in a map; which will finally be send in REST call as query parameters
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
