/**
* script to check if there is a single match
* for details:
* https://community.smartbear.com/t5/SoapUI-NG/Script-assertion-using-regex-to-get-a-value-from-the-response/m-p/128147#U128147
**/
def str = '''{"ProcessingMilliseconds":  11.34,"ResponseCount":1,"ProcessingMachine":"QAC1","ApplicationVersion":"2.1.766.10728","BuildNumber":"2.1.766.10728","EnvironmentName":"QA","MethodName":"/MemberStore/123/2.1/Routers/QualifiedQuotas"}'''
def regEx = "(ProcessingMilliseconds\"[\\s?]*:[\\s?]*[\\d]+[\\.][\\d]*,)"
def result =  (str =~ regEx)
//Equal to 1, because there must be one match.
assert result.count ==1
