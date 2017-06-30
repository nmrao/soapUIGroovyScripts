/**
* Refer: https://community.smartbear.com/t5/SoapUI-NG/Assertions-to-verify-items-are-being-returned-in-alphabetical/m-p/145204#M32948
* this script checks if the given property of json is in sorted order or not
**/

def response = """{
	"results": [
		{
			"transactionId": 001,
			"documentNumber": "1234",
			"purchaseDate": "2012-05-01T00:00:00",
			"uploadedBy": "Kane, Lucy"
		},
		{
			"transactionId": 002,
			"userId": "f4b012345678",
			"documentNumber": "105343998358653377",
			"customerReference": "32145",
			"purchaseDate": "2004-12-01T00:00:00",
			"uploadedBy": "Mac, Mike"
		},
		{
			"transactionId": 003,
			"userId": "f4b0d0c3",
			"documentNumber": "1085593563205677",
			"purchaseDate": "2006-09-21T00:00:00",
			"uploadedBy": "Kelly, Anne"
		}
	]
}"""

def json = new groovy.json.JsonSlurper().parseText(response)

def getPropertyValues = { prop ->
	json.results."$prop"
}

def areValuesAscending = { list ->
	(list == list.sort(false)) ? true : false
}

def tidsActualOrder = getPropertyValues('transactionId')
println "lis of transactionId : ${tidsActualOrder}"
assert areValuesAscending(tidsActualOrder), "${tidsActualOrder} is not in ascending order"
