/**
* Ref: https://community.smartbear.com/t5/SoapUI-Pro/Need-help-with-XmlSlurper/m-p/149851#M34183
* Online demo: https://ideone.com/z8Rg76
* Below is the script assertion
**/
assert context.response, 'Response is empty or null'
def dateFormat = 'dd.MM.yyyy'
def xml = new XmlSlurper().parseText(context.response)

//Get all the documents
def docs = xml.'**'.findAll{it.name() == 'Document'}

//Closure to get the max date of selected id
def getMaxDateOfId = { id -> docs.findAll { it.Name.@id.text() == id && it.DateOfIssuance.@value.text() && it.DateOfIssuance.@value.text() != "-" }
      .collect { Date.parse(dateFormat, it.DateOfIssuance.@value.text()) }
      .max()?.format(dateFormat) 
}

def map = (docs.collect{it.Name.@id.text()} as Set).collectEntries{ id -> [(id as Integer): getMaxDateOfId(id)] }
.sort { it.key }
.findAll { it.value }

map.collect { log.info "Max date of issuance for ${it.key} is ${it.value}" }
