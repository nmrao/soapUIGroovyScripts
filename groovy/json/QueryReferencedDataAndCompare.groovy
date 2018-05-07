/**
* Ref:
* */
def jsonString="""
{
  "prodS": {
    "langTrans": [
      {
        "Id": "11",
        "lang": "EN"
      },
      {
        "Id": "11",
        "lang": "FR"
      },
      {
        "Id": "12",
        "lang": "EN"
      },
      {
        "Id": "12",
        "lang": "FR"
      }
    ],
    "rates": [
      {
        "Id": "11",
        "rate": 100
      },
      {
        "Id": "12",
        "rate": 200
      }
    ]
  }
}"""
def expectedLanguages = ['EN', 'FR']
def json = new groovy.json.JsonSlurper().parseText(jsonString)
println "Rate Ids are $json.prodS.rates.Id"
json.prodS.rates.Id.collect { id ->  
   def actualLanguages = json.prodS.langTrans.findAll {it.Id == id}*.lang?.sort()
   println "${actualLanguages} found for $id under langTrans"
   assert expectedLanguages == actualLanguages, "Not matching languages for Id ${id}" 
}
