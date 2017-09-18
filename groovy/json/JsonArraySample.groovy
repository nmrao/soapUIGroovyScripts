/**
* Ref: https://community.smartbear.com/t5/SoapUI-Pro/How-to-parse-JSON-or-XML-in-Assertions/m-p/149830#M34177
* This is script assertion for reading json response and get the size of the array and print array data
*  Also see the demo : https://ideone.com/Z3wyfh
**/

def json = new groovy.json.JsonSlurper().parseText(jsonString)
log.info "Total members: ${json.Data.size()}"
log.info "Meber names: ${json.Data.collect{it.MemberName}}"
