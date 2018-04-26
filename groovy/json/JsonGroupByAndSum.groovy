/**
* Ref: https://community.smartbear.com/t5/SoapUI-Pro/Groupby-using-groovy/m-p/163467#M36765
*
**/
def jsonString = """
{
	"prods": [
	{
		"Item":"11",
		"rate": 100
	},
	{
		"Item":"12",
		"rate":200
	},
	{
		"Item":"12",
		"rate":200
	}]
}"""
def json = new groovy.json.JsonSlurper().parseText(jsonString)
json.prods.groupBy{it.Item}.each { key, value -> 
	log.info "Item : ${key}, Sum (of rate): ${value.sum{it.rate}}" 
}
