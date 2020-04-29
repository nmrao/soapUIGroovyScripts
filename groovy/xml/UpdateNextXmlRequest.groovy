/**
* This is groovy script test step
* retrieves response from previous step and read a node named 'travelSolutions'
* reads next request step, removes 'travelSolutions' nodes if any from previous run
* update the request with travelSolutions from response
*
*
* Ref: https://community.smartbear.com/t5/SoapUI-Open-Source/How-to-pass-a-list-of-nodes-from-a-response-to-a-new-request/m-p/200678#M30371
*
**/
def getStep = { context.testCase.testStepList[context.currentStepIndex+(it)] }

def response = new XmlParser(false, true).parseText(getStep(-1).httpRequest.responseContent)
def request = new XmlParser(false, true).parseText(getStep(1).httpRequest.requestContent)
//Remove existing travelSolutions
def eTSols = request.'**'.findAll{it.name() == 'travelSolutions'}
eTSols.each {
	request.'**'.find { it.name() == 'searchBaseRequest' }?.children()?.remove(it)
}

//Add travelSolutions from previous step to next step
def tsols= response.'**'.findAll{it.name() == 'travelSolutions'}
tsols.each {
	request.'**'.find { it.name() == 'searchBaseRequest' }.children().add( 1, it )
}
getStep(1).httpRequest.requestContent = groovy.xml.XmlUtil.serialize(request)
