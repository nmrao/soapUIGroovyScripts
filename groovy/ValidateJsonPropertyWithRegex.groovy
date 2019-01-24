/**
* This is Script Assertion for REST Request test step
* to check a property value is matching the given regex and make the test fail if not
* Refer: https://community.smartbear.com/t5/SoapUI-Open-Source/UNABLE-TO-CREATE-REGEX-EXPRESSION-IN-ASSERTION-FOR-REST-JSON/m-p/177716
**/

//Below is to demonstration with fixed json; and one passing value and another one failing. If you uncomment below make sure line 28 is commented and vice-versa.
/*
def jsonString = '''{
  "payload": [
    {
      "maprefnum": "58",
      "stgtablename": "table_name",
      "tablename": "table_name_2",
      "sourcepriority": ""
    },
    {
      "maprefnum": "59a",
      "stgtablename": "table_name",
      "tablename": "table_name_2",
      "sourcepriority": ""
    }
  ]
}'''
*/

//To handle current REST Request test step response use below 
def jsonString = context.response
assert jsonString, 'Response is empty or null'

//Modify the regex as needed
def regex = '\\d+'

//Do not modify beyond this
//Check and collect all the values of property if they are matching given regex
def result = []
def assertValue = {
	result << (it ==~ /$regex/)
}

def json = new groovy.json.JsonSlurper().parseText(jsonString)

//Call the checking for all property values
json.payload*.maprefnum.each { assertValue(it)}
log.info result
//Assert the collect result
assert result.every {true == it}, 'Not all maprefnum has matching the given regex'
