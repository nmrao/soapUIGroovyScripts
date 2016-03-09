/**
* Below script extract the cdata from xml first
* And then extracts name from the first Table
**/
import com.eviware.soapui.support.XmlHolder
def response = '''<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
<soap:Body>
<GetCountryByCountryCodeResponse xmlns="http://www.webserviceX.NET">
<GetCountryByCountryCodeResult><![CDATA[<NewDataSet>
<Table>
<countrycode>uk</countrycode>
<name>United Kingdom</name>
</Table>
<Table>
<countrycode>uk</countrycode>
<name>United Kingdom</name>
</Table>
</NewDataSet>]]></GetCountryByCountryCodeResult>
</GetCountryByCountryCodeResponse>
</soap:Body>
</soap:Envelope>'''
def holder = new XmlHolder(response)
def countryCodeResult = holder.getNodeValue('//*:GetCountryByCountryCodeResult')
log.info countryCodeResult
def cdataHolder = new XmlHolder(countryCodeResult)
def countryName = cdataHolder.getNodeValue("//Table[1]/name")
log.info countryName
