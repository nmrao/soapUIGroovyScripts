/**
*this example check the json array is empty
**/
import net.sf.json.groovy.JsonSlurper
def jsonArray = '''{
    "employees":[]
}'''
def jsonSlurper = new JsonSlurper()
def object = jsonSlurper.parseText(jsonArray)
def employees = object.employees
if (!employees) {
	log.warn "Array is empty" 
}
