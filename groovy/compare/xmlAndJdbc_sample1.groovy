/**
* compare soap response with jdbc response.
* Refer https://community.smartbear.com/t5/SoapUI-Open-Source/How-to-compare-2-response-XMLs-from-2-Different-Test-Steps-and/m-p/139945#U139945
**/
def xmlResponse = """<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENC="http://schemas.xmlsoap.org/soap/encoding/" xmlns:tns="http://localhost/retail/namespaces" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
   <SOAP-ENV:Body>
      <ns1:GetAllCategoriesResponse xmlns:ns1="urn:RetailApp">
         <return xsi:type="tns:allcats">
            <allcategories xsi:type="SOAP-ENC:Array" SOAP-ENC:arrayType="tns:categorydetail[4]">
               <item xsi:type="tns:categorydetail">
                  <category_id xsi:type="xsd:string">7</category_id>
                  <category_name xsi:type="xsd:string">Human Resource</category_name>
               </item>
               <item xsi:type="tns:categorydetail">
                  <category_id xsi:type="xsd:string">5</category_id>
                  <category_name xsi:type="xsd:string">IT</category_name>
               </item>
               <item xsi:type="tns:categorydetail">
                  <category_id xsi:type="xsd:string">6</category_id>
                  <category_name xsi:type="xsd:string">Manufacturing</category_name>
               </item>
               <item xsi:type="tns:categorydetail">
                  <category_id xsi:type="xsd:string">8</category_id>
                  <category_name xsi:type="xsd:string">Marketing</category_name>
               </item>
            </allcategories>
            <sessiondata xsi:type="SOAP-ENC:Array" SOAP-ENC:arrayType="tns:sessiondetail[1]">
               <item xsi:type="tns:sessiondetail">
                  <sessionid xsi:type="xsd:string">O1GHg65lCq1492324865</sessionid>
                  <username xsi:type="xsd:string">dey.avik5@gmail.com</username>
               </item>
            </sessiondata>
         </return>
      </ns1:GetAllCategoriesResponse>
   </SOAP-ENV:Body>
</SOAP-ENV:Envelope>"""

def jdbcResponse = """<Results>
   <ResultSet fetchSize="0">
      <Row rowNumber="1">
         <CATEGORY.CAT_ID>7</CATEGORY.CAT_ID>
         <CATEGORY.CAT_NAME>Human Resource</CATEGORY.CAT_NAME>
      </Row>
      <Row rowNumber="2">
         <CATEGORY.CAT_ID>5</CATEGORY.CAT_ID>
         <CATEGORY.CAT_NAME>IT</CATEGORY.CAT_NAME>
      </Row>
      <Row rowNumber="3">
         <CATEGORY.CAT_ID>6</CATEGORY.CAT_ID>
         <CATEGORY.CAT_NAME>Manufacturing</CATEGORY.CAT_NAME>
      </Row>
      <Row rowNumber="4">
         <CATEGORY.CAT_ID>8</CATEGORY.CAT_ID>
         <CATEGORY.CAT_NAME>Marketing</CATEGORY.CAT_NAME>
      </Row>
   </ResultSet>
</Results>"""

getDetails = { data, recordElement, id, name ->
 new XmlSlurper().parseText(data).'**'.findAll{it.name() == recordElement && !it."$id".isEmpty()  }.inject([:]){map,item -> map[item."$id".text()] = item."$name".text();map}.sort()
}

//def items = new XmlSlurper().parseText(xmlResponse).'**'.findAll{it.name() == 'item' && !it.category_id.isEmpty()  }.inject([:]){map,item -> map[item.category_id.text()] = item.category_name.text();map}.sort()
//println items
//category_id

def soap = getDetails(xmlResponse, 'item', 'category_id', 'category_name')
log.info soap
def jdbc = getDetails(jdbcResponse, 'Row', 'CATEGORY.CAT_ID', 'CATEGORY.CAT_NAME')
log.info jdbc
assert soap == jdbc, 'both are not matching'
