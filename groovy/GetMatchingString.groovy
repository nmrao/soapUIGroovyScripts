/**This script extracts a partial string of a soap response node value of matching string 
**and assigns a test case property
**/
import com.eviware.soapui.support.XmlHolder
def xml = new XmlHolder(context.response)
//change the xpath as per the need
def responseValue = xml.getNodeValue("//*:CDES-PUT-Response/*:response-message")
//partial string of a node value from the responseValue
def string = 'Job received successfully'
// partial string of the same node value from the responseValue to be matched and extracted
// using regex
def pattern = '[A-Z]+[0-9]+'
if (responseValue.contains(string)) {
    def group = (responseValue =~ /${pattern}/)
    context.testCase.setPropertyValue('JOB_ID',group[0])
} else {
	log.warning "Unexpected value in the response"
}
