/**
* This groovy script
* compares two xmls with different element names
* and assert the values
* Ref: https://community.smartbear.com/t5/SoapUI-Open-Source/SOAP-UI-5-0-groovy-Automation/m-p/180017#M28413
*
* Assuming there are two SOAP request steps and this will be the third step of Groovy Script type
**/

//Below two statements(response1, response2) are commented to be able work with dynamic response. Uncomment it to test with fixed data for visualization

/*

def response1 = '''<?xml version="1.0"?>
<soap:Envelope
xmlns:soap="http://www.w3.org/2003/05/soap-envelope/"
soap:encodingStyle="http://www.w3.org/2003/05/soap-encoding">

<soap:Body>
<m:basicObj xmlns:m="https://www.w3schools.com/prices">
<m:personId>22345</m:personId>
<m:personType>M</m:personType>
</m:basicObj>
<n:addressObj xmlns:n="https://www.w3schools.com/address">
<n:addressLine1>Rajaji nagar</n:addressLine1>
<n:city>Mumbai</n:city>
<n:pinCode>245321</n:pinCode>
</n:addressObj>
</soap:Body>
</soap:Envelope>'''

def response2 = '''<?xml version="1.0"?>
<soap:Envelope
xmlns:soap="http://www.w3.org/2003/05/soap-envelope/"
soap:encodingStyle="http://www.w3.org/2003/05/soap-encoding">
<soap:Body>
<m1:basicObject xmlns:m1="https://www.w3schools.com/prices">
<m1:personIdentification>12456</m1:personIdentification>
<m1:AdminPersonType>F</m1:AdminPersonType>
</m1:basicObject>
<m1:basicObject xmlns:m1="https://www.w3schools.com/prices">
<m1:personIdentification>22345</m1:personIdentification>
<m1:AdminPersonType>M</m1:AdminPersonType>
</m1:basicObject>
<k:addressObject xmlns:k="https://www.w3schools.com/address">
<k:address>Rajaji nagar</k:address>
<k:cityAddress>Mumbai</k:cityAddress>
<k:stateAddress>DF</k:stateAddress>
<k:pin>245321</k:pin>
</k:addressObject>
</soap:Body>
</soap:Envelope>'''

*/

//The above is to show that it works sample data. In order to work dynamic responses, user have to define and assign the data 
//dynamically
//If you happened to uncomment above fixed data, then comment below two statements

def response1 = context.expand('${Step1_Name#Response}')
def response2 = context.expand('${Step2_Name#Response}')

//This is key value pairs; key denotes the element name in response1 and value denotes the element name in response2
//Modify either of key or value if needed
def map = [personId: 'personIdentification', personType: 'AdminPersonType', addressLine1: 'address', city: 'cityAddress', pinCode: 'pin']

def getXml = { data -> new XmlSlurper().parseText(data) }

def getData = { data, elementName, elementValue=null ->
	if (elementValue) {
		data.'**'.find{ it.name() == elementName && it == elementValue }?.text()
	} else {
		data.'**'.find{it.name() == elementName}?.text()
	}
}

def sb = new StringBuffer()

def xml1 = getXml(response1)
def xml2 = getXml(response2)

map.each { k, v ->
	def valueResponse1 = getData(xml1, k)
	def valueResponse2 = getData(xml2, v, valueResponse1)
	if (valueResponse1 != valueResponse2) {
		sb.append("Comparison of $k and $v failed, $valueResponse1 != $valueResponse2 \n")
	}	
}

if (sb.toString()) {
	throw new Error(sb.toString())
} else {
	log.info 'Both response are compared successfully'
}

