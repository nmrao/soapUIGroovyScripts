/**
* Ref: https://community.smartbear.com/t5/SoapUI-Pro/How-to-parse-JSON-or-XML-in-Assertions/m-p/149830#M34177
* This is script assertion for reading json response and get the size of the array and print array data
* Demo : https://ideone.com/FGSawS
def xml = new XmlSlurper().parseText(context.response)
def members = xml.'**'.findAll{it.name() == 'e'}
log.info "Total members: ${members.size()}"
log.info "Meber names: ${members.collect{it.MemberName} }"
