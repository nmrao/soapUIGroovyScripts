/**
* Ref: https://community.smartbear.com/t5/SoapUI-Open-Source/How-to-parse-CData-element-in-xml-response-and-pass-it-to-next/m-p/146664
*
**/

/*
def xml = """<data contentType="application/x-www-form-urlencoded;charset=UTF-8" contentLength="53"><![CDATA[atsToken=DA:1501022300340:fmsBe51FV_TUmnJFj1nz5f77Jqg]]></data>"""
def pxml = new XmlSlurper().parseText(xml)
*/
//Commented above two lines as that works only for fixed response and added below statement as user need to deal with dynamic response
def pxml = new XmlSlurper().parseText(context.response)
def p = new Properties()
p.load(new StringReader(pxml.text()))
log.info p.atsToken
context.testCase.setPropertyValue('ATS_TOKEN', p.atsToken)
