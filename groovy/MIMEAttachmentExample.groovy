/**
* Get the attachment from the response and
* Set the attachment string as request for next/given step dynamically
* This assumes single attachment in current response
* Script can be used as script assertion
**/


/*you can use it as property expansion as well for below 
two properties if you do not want to modify here*/

//set the required content type of attachment
def expectedContentType = 'text/xml; charset=UTF-8'

//set the next request step name where dynamic request has to be created
def nextStepName='step2'

def actualContentType = messageExchange.responseAttachments[0].contentType

//assert if it is matching with response received
assert actualContentType == expectedContentType, "Content type is not matching to ${expectedContentType}"

//if the assertion is passed then only read the attachment text
def attachmentText = messageExchange.responseAttachments[0].inputStream.text

//assert the text of attachment is not null
assert attachmentText, "Attachment text is empty or null"

//Get the existing request for the next step
def nextStepRequest = context.testCase.getTestStepByName(nextStepName).getTestRequest()

//Set the dynamic value from the attachment text to next step request.
nextStepRequest.setRequestContent(attachmentText)
