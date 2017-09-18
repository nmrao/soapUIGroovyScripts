/**
* Ref: https://community.smartbear.com/t5/SoapUI-Pro/Need-help-with-XmlSlurper/m-p/149851#M34183
* Online demo: https://ideone.com/z8Rg76
* Below is the script assertion
**/
assert context.response, 'Response is empty or null'
def xml = new XmlSlurper().parseText(context.response)
def docs = xml.'**'.findAll{it.name() == 'Document'}
def map = (docs.collect{it.Name.@id.text()} as Set).collectEntries{id -> [(id as Integer): docs.findAll{it.Name.@id.text() == id && it.DateOfIssuance.@value.text() && it.DateOfIssuance.@value.text() != "-" }.collect{Date.parse('dd.MM.yyyy', it.DateOfIssuance.@value.text())}.max()?.format('dd.MM.yyyy')]}.sort{it.key}.findAll{it.value}
map.collect { log.info "Max date of issuance for ${it.key} is ${it.value}" }

​
