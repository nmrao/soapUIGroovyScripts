//This script assertion reads the http response, 
//collect the cookies for the next http request
//Provide the next step name where you want to set the Cookie to the request or
//Use property expansion for below
def nextStepName = 'step2'
def nextRequest = context.testCase.testSteps[nextStepName].httpRequest
def headers = nextRequest.requestHeaders
if (messageExchange.responseHeaders.containsKey('Set-Cookie')) {
  log.info "Found Cookie in the response headers"
  def cookiez = messageExchange.responseHeaders['Set-Cookie'].value
  def list = []  
  cookiez.each { cookies ->
     //def (name, value) = cookies.toString().split('=',2)
     list.add(cookies.toString())
  }
  headers['Cookie'] = list
} else {
  log.warn "Not Found Cookie in the response headers"
}
nextRequest.requestHeaders = headers
