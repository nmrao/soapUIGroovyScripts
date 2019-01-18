/**
* This groovy script compares the specified property values of two property steps and report the error if any
* For more details: https://community.smartbear.com/t5/SoapUI-Pro/Comparison-of-Two-Property-Values-In-Two-Different-Properties/m-p/175573#M40015
**/
//Define the property names as defined in the property steps
//Say, Name in Property Step 1 : Name in Property Step 2
//This way, it is possible to compare even if property names do not match in two Property Steps
//Add or remove as per your need
def map = ['id1': 'id2', 'name':'name']

//Modify the names of the Property Test step if needed. Here two property steps with given names are defined in the test case.
def p1 = context.testCase.testSteps["Properties1"].properties
def p2 = context.testCase.testSteps["Properties2"].properties

def result = []

def assertPropertyValue = { p1key, p2key ->	
	def temp = p1[p1key].value == p2[p2key].value
	log.info("Comparing $p1key, and $p2key values respectively ${p1[p1key].value} == ${p2[p2key].value} ? $temp")
	temp
}

map.each { result << assertPropertyValue(it.key, it.value) }
assert result.every{it == true}, 'Comparison failed, check log'
