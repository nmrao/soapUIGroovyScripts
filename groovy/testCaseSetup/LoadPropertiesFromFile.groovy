/**
* Refer: https://community.smartbear.com/t5/SoapUI-Open-Source/Need-to-pass-REST-parameters-from-a-file/m-p/199862#M30214
*
* Test Case setup script
* Loads properties from the file
* file name is provided in test case custom property DATA_FILE 
**/
def fileName = testCase.getPropertyValue('DATA_FILE')
assert fileName, 'No value set for file name. Set value for DATA_FILE in test case custom properties'
def file = new File(fileName)
assert file.exists() : 'file not found'
assert file.canRead() : 'file cannot be read'
def properties = new Properties()
file.withInputStream { properties.load(it) }
properties.each { k, v -> testCase.setPropertyValue(k, v) }
