/**
* Below is the Script Assertion for the JDBC Request test step
* extracts column names and values for a single row (assuming query retuns a single row)
* and put the extracted data into following Properties test step
* Refer for more details
* https://community.smartbear.com/t5/forums/replypage/board-id/SoapUING_forum/message-id/40377
** /

//For testing, using fixed response
//Comment it and uncomment  line 19 when you need the script to process current jdbc test step response
def jdbcResponse = """<Results>
   <ResultSet fetchSize="0">
      <Row rowNumber="1">
         <CATEGORY.CAT_ID>7</CATEGORY.CAT_ID>
         <CATEGORY.CAT_NAME>Human Resource</CATEGORY.CAT_NAME>
      </Row>
      </ResultSet>
</Results>"""
//def jdbcResponse = context.response

def xml = new XmlSlurper().parseText(jdbcResponse)
//Modify the test step name if different from Properties
def step = context.testCase.testSteps['Properties']
step.propertyNames?.each { step.removeProperty(it) }
xml.'**'.find{it.name() == 'Row'}.childNodes().each { 
	def prop = step.hasProperty(it.name()) ? step.getProperty(it.name()) : step.addProperty(it.name())
	prop.value = it.text()
}
log.info "Check the properties step for the details"
