/**
* At times, user like to share objects between the Groovy Scripts.
* SoapUI allows to share properties for test case / suite / project. But these can only hold String Type data.
* If you want to use complex objects like list / map or any custom object, there is no stright way.
* Luckily, since there is groovy script support, one can use power of goovy's meta programming to achieve the same.
* Refer: https://stackoverflow.com/questions/44421624/how-to-pass-a-two-dimensional-list-array-between-groovy-scripts-in-soap-ui/44469058#44469058 
* Refer : https://community.smartbear.com/t5/SoapUI-NG/Passing-groovy-lists-via-testcase-properties/m-p/143878#M32513
**/

//In script 1:
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase
WsdlTestCase.metaClass.myList = [1,2,3,4,5]

//In script 2 :
log.info "From script 2: ${context.testCase.myList}"
assert [1,2,3,4,5] == context.testCase.myList
