/*This script reads the salesOrderNumber from request using xpath
* and checks corresponding response xml file from
* mockResponses directory of your soapui project location.
* Also expects a soapFault.xml file in the same location
* in order to send soap fault if no sales order number maches the
* existing files.
* For example, soapui project located under C:\soapuiProjects
* create a subdirectory called mockResponses under above directory
* Make sure all your mock response files for sales orders under
* C:\soapuiProjects\mockResponses directory including soapFault.xml
* Assuming that  the soap response file extension is xml, not txt
*/
def groovyUtils = new com.eviware.soapui.support.GroovyUtils(context)
def holder = groovyUtils.getXmlHolder(mockRequest.requestContent)
def soNumber = holder.getNodeValue("//*:BillingOrderLoadRequest/*:salesOrderNumber")
def file = new File (groovyUtils.projectPath+"/mockResponses/${soNumber}.xml")
def fileToLoad = 'soapFault'
if (file.exists()) {
    fileToLoad = soNumber
}
def tempContent = groovy.xml.XmlUtil.serialize(new XmlParser().parse(groovyUtils.projectPath+"/mockResponses/${fileToLoad}.xml"))
tempContent.replace("\$")
context.content = tempContent

