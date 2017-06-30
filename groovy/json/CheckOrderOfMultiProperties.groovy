/**
* Refer:https://community.smartbear.com/t5/SoapUI-NG/Assertions-to-verify-items-are-being-returned-in-alphabetical/m-p/145218#M32959
* this is to handle multiple properties
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

//ATTENTION: add the list of property names
def userList = ['transactionId', 'uploadedBy', 'purchaseDate']

def json = new groovy.json.JsonSlurper().parseText(response)
def msg = new StringBuffer()

def getPropertyValues = { prop ->
	json.results."$prop"
}

def areValuesAscending = { list ->
	(list == list.sort(false)) ? true : false
}

def buildAssertionErrors = { propList ->
	propList.each { prop ->
		def getData = getPropertyValues(prop)
		println "list of ${prop} : ${getData}"
		if (!areValuesAscending(getData)) {
			if (!msg) msg.append('There are assertion errors\n')
			msg.append("${prop} : ${getData}").append('\n')
		}
	}
}

buildAssertionErrors(userList)

if (msg) { 
	println 'Test is a fail'
	throw new Error(msg.toString())
} else {
	println 'Test is a pass'
}
