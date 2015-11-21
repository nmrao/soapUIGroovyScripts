/**
* this script reads the response of a test step
* takes headers with name 'Set-Cookie' and
* adds them to Cookie store
* Use as script assertion
**/
import com.eviware.soapui.impl.wsdl.support.http.HttpClientSupport
import org.apache.http.cookie.Cookie
import org.apache.http.impl.cookie.BasicClientCookie
def myCookieStore = HttpClientSupport.getHttpClient().getCookieStore()
if (messageExchange.responseHeaders.containsKey('Set-Cookie')) {
  log.info "Found Cookie in the response headers"
  def cookiez = messageExchange.responseHeaders['Set-Cookie'].value  
  cookiez.each { cookies ->
     def (name, value) = cookies.toString().split('=',2)
     Cookie cooky = new BasicClientCookie(name, value)
     myCookieStore.addCookie(cooky)
  } 
} else {
  log.warn "Not Found Cookie in the response headers"
}
log.info myCookieStore.getCookies().size()
