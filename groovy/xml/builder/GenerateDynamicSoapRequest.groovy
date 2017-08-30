/**
 * Below script shows how to build xml using MarkupBuiler with easily
 * using element names as need by the user
 * just like how xml appears
 * (as shown in color code;elements, data)
 **/

import groovy.xml.StreamingMarkupBuilder
import groovy.xml.XmlUtil

//Define all your namespaces here
def nameSpacesMap = [
        soap: 'http://schemas.xmlsoap.org/soap/envelope/',
        ns: 'http://www.domain1.example.com/person',
        ns1: 'http://www.domain2.example.com/student',
        xsi: 'http://www.w3.org/2001/XMLSchema-instance'
]

//For instance, let us assume the data read[record]
//from datasource
def name = 'John'
def age = 20
def type = 'Student'
def education
def adress

def createRequest = {
    def builder = new StreamingMarkupBuilder()
    builder.encoding = 'utf-8'
    def soapRequest = builder.bind {
        mkp.xmlDeclaration()
        namespaces << nameSpacesMap
        soap.Envelope {
            soap.Body {
                //example for element attribute
                ns.UserRequest(type: type) {
                    ns1.name(name)
                    ns1.age(age)
                    //add education element irrespective of data
                    ns1.education(education)
                    //add address only if there is data
                    if (!"$address") {
                        ns1.address(address)
                    }
                }
            }
        }
    }
    XmlUtil.serialize(soapRequest)
}

println createRequest()
