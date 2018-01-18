/**
* This is a "Save Script" at project level
* which clears all the property values including project, test suite, test case, and test step levels
* provided project level property "CLEAR_PROPERTIES" value is true and project is saved.
* User has an option to ignore certain property names which you do not wish to clear on saving the project.
* It is suggested that make sure value "false" is set to "CLEAR_PROPERTIES" while working. And only set its value to "true"
* if you want to commit the project either into a repository or give it external parties etc.,
* Also refer:
* https://community.smartbear.com/t5/SoapUI-Pro/Export-project-without-any-properties-data/m-p/156946#M35571
**/



import com.eviware.soapui.impl.wsdl.teststeps.RestTestRequestStep
def ignoreProperties = ['Endpoint', 'CLEAR_PROPERTIES', 'result', 'script', 'Request', 'AuthType', 'Response', 'RawRequest']


def filteredProperties = { object, clear = true ->
	def names =  object.properties?.keySet().findAll { !(it in ignoreProperties)}
	if (object instanceof RestTestRequestStep){
		names = names.findAll{!(object.config.config.restRequest.parameters.properties.entryList.key)}		
	}	
	log.info names.collect {[(object.properties[it].name): object.properties[it].value]}
	if(clear) {
		log.info "clearing properties for ${object.name}"
		names.collect {object.properties[it].value = ''}
	}
}


if ('true' == project.properties['CLEAR_PROPERTIES'].value) {
	filteredProperties(project)
	project.testSuiteList.collect { suite -> 
		filteredProperties(suite)
		suite.testCaseList.collect { kase ->
			filteredProperties(kase)
			kase.testStepList.collect { step ->
				filteredProperties(step)
			}
		}
	}	
}
