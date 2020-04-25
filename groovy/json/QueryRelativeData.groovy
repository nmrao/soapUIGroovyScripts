/**
* Refer: https://community.smartbear.com/t5/SoapUI-Open-Source/Getting-data-out-of-json-response/m-p/200662/highlight/false#M30368
*
**/
def jsonString = '''{
  "result": [
    {
      "role": "ADMIN",
      "relationNumber": 8046017,
      "individual": {
        "firstName": "aaaa",
        "lastName": "bbbb",
        "birthDate": "1980-09-03"
      },
      "addresses": [
        {
          "street": "blablabla",
          "houseNbr": "0014"
        }
      ],
      "involvedObjects": [
        
      ]
    },
    {
      "role": "VIEWER",
      "relationNumber": 8046018,
      "individual": {
        "firstName": "cccc",
        "lastName": "dddd",
        "birthDate": "1980-09-03"
      },
      "addresses": [
        {
          "street": "blablabla",
          "houseNbr": "0014"
        }
      ],
      "involvedObjects": [
        
      ]
    }
  ],
  "infos": [
    
  ]
}'''

def individualViewer = new groovy.json.JsonSlurper().parseText(jsonString).result.find{it.role == 'VIEWER'}.individual
log.info "first name: ${individualViewer.firstName}"
log.info "last name: ${individualViewer.lastName}"
