/**
 * This is Script Assertion
 * Checks if the response value falls in certain range
 * for more details: https://community.smartbear.com/t5/SoapUI-NG/I-need-to-be-able-to-put-a-Range-into-my-Assertions/m-p/128750#U128750
 * */

//Check if the response is not empty
assert context.response, 'Response is empty or null'

//Parse the response
def parsedXml = new XmlSlurper().parseText(context.response)

//Get the Total Results as Integer
def results = parsedXml.'**'.find {it.name() == 'TotalResults'}.text() as Integer
log.info "Total Results : $results"

//Define the range of values that needs to verified against
def range = 181..200

//Check if the response value falls in the given range
assert range.contains(results), "Response value is not falling in the given range"
