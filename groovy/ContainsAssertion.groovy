//gets the Script Language project property value from standard property
def stdProjectPropValue = context.testCase.testSuite.project.defaultScriptLanguage
//get the response as text
def responseText = messageExchange.responseContent
//do the contains assert, show error message otherwise.
assert responseText.contains(stdProjectPropValue), "Response does not contain ${stdProjectPropValue}"
