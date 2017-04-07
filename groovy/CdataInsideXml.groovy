/**
* refer https://community.smartbear.com/t5/SoapUI-Open-Source/How-to-get-response-property-value-and-use-it-as-next-step-input/m-p/139479/highlight/false#M23744
* this is a script assertion
* Response is xml whic again as cdata and cdata again has xml where user needs to lookup for a name based on sibling value
* then extracted country name should be persisted for later use
**/

//Check if there is repose
assert context.response, 'Response is empty or null'
def lookpcode = context.expand('${#TestCase#CountryCode}')
def dataSet = new XmlSlurper().parseText(context.response).'**'.find{ it.name() == 'GetCountryByCountryCodeResult')} as String
def countryName = new XmlSlurper().parseText(dataSet).'**'.find{ it.name() == 'countrycode' && it == lookpcode)}.parent().name.text()
log.info "Country name is ${countryName} where code is ${lookpcode}"
assert countryName, 'Country name empty or null'
context.testCase.setPropertyValue('COUNTRY_NAME', countryName)
