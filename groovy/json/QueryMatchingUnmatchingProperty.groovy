/**
* Refer: https://community.smartbear.com/t5/SoapUI-NG/Autocomplete-API-Check-a-string-parameter-in-the-request-matches/m-p/145658#M33097
**/

def json = '''{
"results": [
{"documentNumber": "112069353213670593"},
{"documentNumber": "120212364882313025"},
{"documentNumber": "120055742821186593"},
{"documentNumber": "123134986738657857"},
{"documentNumber": "123207962820539585"},
{"documentNumber": "1234567"},
{"documentNumber": "123456789"},
{"documentNumber": "123597020827608033"},
{"documentNumber": "123639294264534913"}
]
}'''
def expectedPattern = '123'
def joinData = { items -> items?.join(', ') }
def printMessage = { msg, items -> !items.size() ?: log.info("${msg} : ${joinData(items)}") }
//If you are using it in script assertion i.e., to deal dynamic response use context.response instead of json in the below statement
//and remove above json string declaration
def parsedJson = new groovy.json.JsonSlurper().parseText(json)

//Closure
def getFilteredDocuments = { closure -> parsedJson.results.documentNumber.findAll(closure) }

//Pass different condition to the closure as shown below - contains, not contains, and all
def matchingDocuments = getFilteredDocuments { it.contains expectedPattern }
def unwantedDocuments = getFilteredDocuments { !it.contains(expectedPattern) }
def allDocuments = getFilteredDocuments { it }

printMessage 'Total document numbers', allDocuments
printMessage 'Matching document numbers', matchingDocuments
printMessage 'Unwanted document numbers', unwantedDocuments

//Check if there are unwanted / unexpected document numbers and list them if any
assert !unwantedDocuments.size(), "There are invalid document numbers found : ${joinData(unwantedDocuments)}" ​
