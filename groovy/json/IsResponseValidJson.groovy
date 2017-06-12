/**
* This script assertion
*
**/

import com.eviware.soapui.support.JsonUtil

assert context.response, 'Response is empty or null'
assert true == JsonUtil.isValidJson(json), 'Response is not a valid json'
