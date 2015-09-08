/** this sample reads value of the response, creates an object and reads node value using xpath and writes
** into a file **/
import com.eviware.soapui.support.XmlHolder
def xml = new XmlHolder(context.response)
def preValidationMinValue = xml.getNodeValue("//*:PrevalidationMin")
new File('c:/temp/myfile.csv').write(preValidationMinValue)
