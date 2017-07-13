/**
* Refer: https://stackoverflow.com/questions/45066034/could-not-find-matching-constructor-for-com-eviware-soapui-support-xmlholderjav
* Compares Jdbc xml and Json response
*
**/
def jdbcResponse = """<Results>
   <ResultSet fetchSize="128">
      <Row rowNumber="1">
         <PortalId>87776</PortalId>
         <ItemKey>Asset</ItemKey>
         <ItemValue>Customer Equipment</ItemValue>
         <ItemPluralValue>Customer Equipment</ItemPluralValue>
      </Row>
      <Row rowNumber="3">
         <PortalId>87776</PortalId>
         <ItemKey>AssignedTo</ItemKey>
         <ItemValue>Assign/Appointment</ItemValue>
         <ItemPluralValue>Assign/Appointment</ItemPluralValue>
      </Row>
      <Row rowNumber="2">
         <PortalId>87776</PortalId>
         <ItemKey>AssignedBy</ItemKey>
         <ItemValue>Assigned By</ItemValue>
         <ItemPluralValue>Assigned By</ItemPluralValue>
      </Row>
   </ResultSet>
</Results>"""

def jsonResponse = """{
   "totalRows": 32,
   "results":    [            
       {
         "key": "AssignedBy",
         "value": "Assigned By",
         "pluralValue": "Assigned By",
         "portalId": 87776
      },
      {
         "key": "Asset",
         "value": "Customer Equipment",
         "pluralValue": "Customer Equipment",
         "portalId": 87776
      },
      {
         "key": "AssignedTo",
         "value": "Assign/Appointment",
         "pluralValue": "Assign/Appointment",
         "portalId": 87776
      }]
}"""

def parsedXml = new XmlSlurper().parseText(jdbcResponse)
def parsedJson = new groovy.json.JsonSlurper().parseText(jsonResponse)
def sortByKey = {a, b -> a.key <=> b.key }


def buildXmlDataList = {
	parsedXml.'**'.findAll{ it.name() == 'Row'}.collect{ [key: it.ItemKey.text(), value: it.ItemValue.text(), pluralValue: it.ItemPluralValue.text(), portalId: it.PortalId.text() as Integer]}.sort(sortByKey)
} 

def buildJsonDataList = {
	parsedJson.results.sort(sortByKey)
}

println buildXmlDataList()
println buildJsonDataList()

assert buildXmlDataList() == buildJsonDataList()
