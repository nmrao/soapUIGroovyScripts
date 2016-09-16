/**
* this groovy script can run the soapui project and generate junit result
**/
import com.eviware.soapui.tools.SoapUITestCaseRunner
def runner = new SoapUITestCaseRunner()
runner.with {
//change the paths as needed to suit your environment
  setProjectFile('/path/to/Sample-soapui-project.xml')
//Ignore below if you do not have any special settings.
  setSettingsFile('/path/to/soapui-settings.xml')
  setOutputFolder('/tmp/results')
  setPrintReport(true)
  setExportAll(true)
  setJUnitReport(true)
  run()
}
