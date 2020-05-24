/**
* Ref: https://community.smartbear.com/t5/SoapUI-Open-Source/JsonPath-RegEx-Match-Assertion-is-not-working-on-SoapUI-5-5-0/m-p/202106#M30563
*/

def regEx = 'Auto*' 
def expectedRecords = 2

def matchedPrograms = new groovy.json.JsonSlurper().parseText(context.response).records.findAll {it.Program_Name =~ /$regEx/}.Program_Name
log.info matchedPrograms
assert matchedPrograms.size == expectedRecords
