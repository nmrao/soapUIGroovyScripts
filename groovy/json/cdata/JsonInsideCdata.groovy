/**
* This example illustrates how to extract Json which is inside a cdata of an XML
* http://stackoverflow.com/questions/34136890/transfer-property-from-login-to-logout-in-saopui-pro/34137618#34137618
**/
import com.eviware.soapui.support.XmlHolder
import net.sf.json.groovy.JsonSlurper
def soapResponse = '''
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns="https://in.io/ns/20110517">
   <soap:Body>
      <login_resp>
         <in_env>
            <cid>1JMRXxxxDWF31PXC0EFQ</cid>
            <result>IN_OK</result>
         </in_env>
         <item>
            <response>{"timestamp": "2015-12-07T14:14:35Z", "data": {"profile": null, "token": "1a66k111-3177-0000-000b-aed1478c8309", "endpoints": [{"label": "app1", "branches": [{"url": "/app1/v1.0/", "name": "ext-", "api_version": "1.0", "label": "ext"}], "appname": "app1"}]}, "success": true}</response>
         </item>
      </login_resp>
   </soap:Body>
</soap:Envelope>'''
def holder = new XmlHolder(soapResponse)
def response = holder.getNodeValue('//*:response')
def json = new JsonSlurper().parseText(response)
log.info json.data.profile
log.info json.data.endpoints
//appending to previous answer
log.info json.data.token
