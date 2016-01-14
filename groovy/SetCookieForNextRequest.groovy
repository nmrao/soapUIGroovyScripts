
/**Below script should be used as script assertion for login request step
* Assumes below
* a. login response contains http header called 'Set-Cookie'
* b. other request needs to send http header called 'Cookie'
* In case if there is any change in able two you may need to change its references below
**/
def responseCookieKey = 'Set-Cookie'
def requestCookieKey = 'Cookie'
def setHttpHeaders(String nextStepName, def headers) {
    def nextRequest = context.testCase.testSteps[nextStepName].httpRequest
    def existingHeaders = nextRequest.requestHeaders
    headers.each {
        existingHeaders[it.key] = it.value
    }
    nextRequest.requestHeaders = existingHeaders
}


if (messageExchange.responseHeaders.containsKey(responseCookieKey)) {
  log.info "Found Cookie in the response headers"
  def cookiez = messageExchange.responseHeaders[responseCookieKey]
  assert null != cookiez, "Response does not contain Cookie"
  def nStepName = context.testCase.testStepList[context.currentStepIndex + 1].name
  def headers = [(requestCookieKey) : (cookiez)]
  setHttpHeaders(nStepName, headers)
} else {
  log.error "Not Found Cookie in the response headers"
}
