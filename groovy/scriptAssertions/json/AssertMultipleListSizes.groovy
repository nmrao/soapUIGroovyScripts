/**
* Ref: https://community.smartbear.com/t5/SoapUI-Pro/How-to-get-size-of-a-list/m-p/165135#M37165
* Demo : https://ideone.com/W6TDfz
* Below is the script assertion for the Rest Request test step
**/

import groovy.json.JsonSlurper
//Define the expected values
def expectedMap = [Allergen: 22, Colour: 4]

//Check if there is response at all
assert context.response, 'Response is empty or null'

def resultMap = [:]
def json = new JsonSlurper().parseText(context.response)
expectedMap.collect{ key, value ->
	def result = json.'_embedded'.categories.find{it.name == key}.options?.size()
	log.info "Count of $key: $result"
	resultMap[key] = result == value ? true : false	
}
assert resultMap.values().every{it}
