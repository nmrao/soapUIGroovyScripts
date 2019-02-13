/**
* Groovy script to call varying query and header parameters
* parameters are read from custom properties of test case
*
* Ref:https://community.smartbear.com/t5/SoapUI-Pro/How-to-omit-parameters-in-a-REST-request/m-p/179296#M40815
***/

import wslite.rest.*

def getMap = { key ->
	def props = context.testCase.propertyNames.findAll { it.startsWith(key)}
	def result = [:]
	props.each { result [it.split('_').last()] = context.testCase.getPropertyValue(it) }
	result
}


def headerz = getMap('HEADER')
def queriez = getMap('QUERY')
log.info headers
log.info queries
def serviceHost = context.expand('${#Project#SERVICE_HOST_PORT}'
def urlPath = '/agents/organizations'

def client = new RESTClient(serviceHost)
def response = client.get(path: urlPath,
					 accept: ContentType.JSON,
					 query : queriez,
					 headers: headerz
					 )
assert response.statusCode == 200
log.info groovy.json.JsonOutput.prettyPrint(response.text)
