/**
*This script automatically update the wsdl definition and its test requests in the soapui project
*Check for the variables- projectName, wsdlFiles to be updated before running the script
*/
import com.eviware.soapui.impl.wsdl.WsdlInterface
import com.eviware.soapui.impl.wsdl.WsdlProject
import com.eviware.soapui.model.iface.Interface
import static com.eviware.soapui.impl.wsdl.actions.iface.UpdateInterfaceAction.recreateRequests
import static com.eviware.soapui.impl.wsdl.actions.iface.UpdateInterfaceAction.recreateTestRequests
/**
* Created by nmrao on 12/24/14.
*/
class UpdateWsdls {
     
       WsdlProject wsdlProject

        public UpdateWsdls(String projectFileName) {
            this.wsdlProject = new WsdlProject(projectFileName)
        }

        def getBindingNames(String wsdlFile) {
            def definitions = new XmlParser().parse(new File(wsdlFile))
            return definitions.getByName('*:binding').@name
        }
       
        void updateInterfaceDefinitions(List<String> wsdlFileNames) {
            wsdlFileNames.each { fileName ->
                def interfaceNames = getBindingNames(fileName)
                interfaceNames.each {
                    updateInterfaceDefinition(it, fileName)
                }
            }       
        }
       
        void updateInterfaceDefinition(String interfaceName, String fileName) {       
            List<Interface> interfacesList = wsdlProject.interfaceList
            interfacesList.each { Interface anInterface ->
                if (anInterface instanceof WsdlInterface && interfaceName.equals(anInterface.name)) {
                    WsdlInterface wsdlInterface = (WsdlInterface) anInterface
                    wsdlInterface.updateDefinition(fileName, false)
                }
            }
        }
       
        void updateRequests () {
            List<Interface> interfacesList = wsdlProject.interfaceList
            interfacesList.each { Interface anInterface ->
                WsdlInterface wsdlInterface = (WsdlInterface) anInterface
                recreateRequests(wsdlInterface,false,false,true,false)
                recreateTestRequests(wsdlInterface,false,false,true,false)
            }
        }
       
        void saveWsdlProject() {
            wsdlProject.save()
            wsdlProject.release()
           
        }
       
}

String projectName = "/path/to/abc-soapui-project.xml" //absolute path of soapui project file
List<String> wsdlFiles = ["/path/to/service1.wsdl"] //or you can have multiple wsdls from different wsdl files which you want to update in one project
UpdateWsdls updateWsdl = new UpdateWsdls(projectName)
updateWsdl.updateInterfaceDefinitions(wsdlFiles)
updateWsdl.updateRequests()
updateWsdl.saveWsdlProject()

