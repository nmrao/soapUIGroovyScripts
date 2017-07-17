/**
* Refer:https://community.smartbear.com/t5/SoapUI-NG/Re-Groovy-for-assertion-not-contain-and-json-path/m-p/145921#M33172
**/
def json = '''
{
"quote": {
"id": "test_id",
"amount": 100,
"links":    [
    {
    "rel": "self",
    "href": "http://localhost:8080/quote/777"
    },
    {
    "rel": "customer",
    "href": "http://localhost:8080/customer/12345"
    }
]
 }
}'''
def parsedJson = new groovy.json.JsonSlurper().parseText(json)
log.info parsedJson.quote.id
​
