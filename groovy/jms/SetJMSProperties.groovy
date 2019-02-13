/**
* Below is the Groovy Script
* which can add the JMS Properties to the given test step from a map
*
* Ref: https://community.smartbear.com/t5/SoapUI-Open-Source/Dynamically-set-JMS-Properties-from-groovy-code/m-p/179202/highlight/false#M28346
**/

import com.eviware.soapui.config.JMSPropertyConfig

//Properties to be set to JMS
def map = [a:'one', b: 'two']

//Provide the step name to set the properties
def stepName = 'Submit Order_OrderSubmission'

//Donot modify beyond this
def step = context.testCase.testSteps[stepName]
def existingProperties =  step.httpRequest.getJMSPropertiesConfig().getJMSProperties()

def newList = new ArrayList<JMSPropertyConfig>();
map.each { key, value ->
	def jmsPropertyConfig = JMSPropertyConfig.Factory.newInstance()
	jmsPropertyConfig.setName(key)
	jmsPropertyConfig.setValue(value)
	newList.add(jmsPropertyConfig)
}

            
existingProperties.clear()
step.httpRequest.getJMSPropertiesConfig().setJMSProperties(newList)
