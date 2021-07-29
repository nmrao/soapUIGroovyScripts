/**
This groovy script take soapui project as Input
And removes all the suites except the given list in "suites" variable
And writes the sub set of the project (as all the suites are removed) into given path.

Context: If the project size is very big, it takes time to load the project into the tool.
This script will help just to only create small project out of the big one, so that load the project with given suite in quick time.

Ref: https://community.smartbear.com/t5/API-Functional-Security-Testing/Best-way-to-load-test-suite-as-per-test-requirement/m-p/220444#M49415

How to execute this script?
You may create an empty project and create an arbitary test case and add groovy script test step and paste the below script in there
and modify values for below
-- templateProjectPath (absolute path  of the original soapui project)
-- subProjectPath (provide the absolute path where sub set of the project needs to be written
-- suites (provide the suite names which needs to be copied into sub project)

**/

// Modify values for below
def templateProjectPath = '/home/apps/projects/testProject-soapui-project.xml'
def subProjectPath = '/tmp/test-soapui-project.xml'
def suites = ['mySuite', 'contextTestSuite']

// No modification require beyond this

def templateProject = new com.eviware.soapui.impl.wsdl.WsdlProject(templateProjectPath)
templateProject.testSuiteList?.findAll { !(it.name in suites) }.each {templateProject.removeTestSuite(it) } 	
templateProject.saveAs(subProjectPath)
