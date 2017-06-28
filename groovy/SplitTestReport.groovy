/**
* Refer: https://stackoverflow.com/questions/44756409/read-xml-to-get-failed-testcases-in-python2/44783258#44783258
* This script takes Junit xml file and splits each testcase into surefire format xml
**/
//Provide the absolute path of the report xml
def inputXml = 'report.xml'

//Define the location where files needs to be written
def outputDir = System.getProperty("java.io.tmpdir")

def templateXml = '''<?xml version="1.0" encoding="UTF-8"?> 
<testsuite xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="https://maven.apache.org/surefire/maven-surefire-plugin/xsd/surefire-test-report.xsd" name="$className" time="$suiteTime" tests="$tests" errors="$errors" skipped="$skipped" failures="$failures"> 
<testcase name="$caseName" classname="$className" time="$caseTime"/> 
</testsuite>'''

def templateFailXml = '''<?xml version="1.0" encoding="UTF-8"?> 
<testsuite xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="https://maven.apache.org/surefire/maven-surefire-plugin/xsd/surefire-test-report.xsd" name="$className" time="$suiteTime" tests="$tests" errors="$errors" skipped="$skipped" failures="$failures"> 
    <testcase name="$caseName" classname="$className" time="$caseTime">
         <error type="">$failedMessage</error>
    </testcase>
</testsuite>'''

def binding = [failures:'0', errors: '0', skipped:'0', suiteTime: '0.00', caseTime:'0.00', caseName:'', tests: '1', suite: '', failedMessage: '', className: 'com.amadeus.developers.pad.amp.catalogue.Catalogue_TryIt_002_SoapSpec']

def xml = new XmlSlurper().parseText(new File('report.xml').text)
def testcases = xml.'**'.findAll{it.name() == 'testcase'}
def engine = new groovy.text.SimpleTemplateEngine()

//Save the contents to a file
def saveToFile(file, content) {
    if (!file.parentFile.exists()) {
         file.parentFile.mkdirs()
         println "Directory did not exist, created"
    }
    file.write(content) 
    assert file.exists(), "${file.name} not created"
}

def writeCaseData = { kase ->
	def tempXml = templateXml
	def bindingKase = binding.clone()
	bindingKase.errors = kase.parent().@errors
	bindingKase.suiteTime = kase.parent().@time
	bindingKase.suite = kase.parent().@name
	bindingKase.caseName = kase.@name
	bindingKase.caseTime = kase.@time
	if (kase.failure.size()) {
		bindingKase.failures = '1'
		bindingKase.failedMessage = kase.failure.text()
		tempXml = templateFailXml
	}
	def template = engine.createTemplate(tempXml).make(bindingKase)
	saveToFile(new File("${outputDir}/${kase.@name}.xml"), template.toString())
}

testcases.each { writeCaseData it }
