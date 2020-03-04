/**
* Ref: https://community.smartbear.com/t5/SoapUI-Pro/Solution-Script-to-Cleanup-of-Custom-properties-in-the-project/m-p/198108#M45275
*
* Project level's Save Script
* Cleans up all the custom properties 
* If CLEAN_PROPERTIES value of project property is set to true
* And save the project
*
*/

def ignoreTestCaseProperties = [ ] 
def ignoreTestSuiteProperties = [ ] 
def ignoreProjectProperties = ['CLEAN_PROPERTIES'] 
def cleanProperties = { model, list -> model.properties.keySet().findAll{ !(it in list) } each { tProp -> model.setPropertyValue(tProp,'') } }
if ('true' == project.getPropertyValue('CLEAN_PROPERTIES')) {
	project.testSuiteList.each { tSuite -> 
		cleanProperties(tSuite, ignoreTestSuiteProperties)		
		tSuite.testCaseList.each { tCase -> cleanProperties(tCase, ignoreTestCaseProperties) }	
	} 
	cleanProperties(project, ignoreProjectProperties)	
	project.setPropertyValue('CLEAN_PROPERTIES', 'false') 
}
