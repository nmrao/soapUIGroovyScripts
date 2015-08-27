def expectedHTTPResponse = ['HTTP/1.1 200 OK']
def headers = messageExchange.response.responseHeaders
def actualHTTPResponse = headers['#status#']
assert expectedHTTPResponse == actualHTTPResponse
