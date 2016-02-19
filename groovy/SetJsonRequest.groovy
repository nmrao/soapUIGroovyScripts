/**
* this script will set the request to
* a specified test step using groovy script
* NOTE: you may modify stepName variable as needed
**/
def stepName='restStep'
def request = context.testCase.getTestStepByName(stepName).getTestRequest()
def jsonText = '''
{
  "id" : "sample id",
  "name" : "sample name",
  "tags" : [ "sample tags" ],
  "address" : {
    "street" : "sample street",
    "zipcode" : "sample zipcode",
    "city" : "sample city"
  }
}       
'''
request.setRequestContent(jsonText)
