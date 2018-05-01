/**
* Ref:https://community.smartbear.com/t5/forums/replypage/board-id/SoapUI_OS/message-id/26894
* Demo: https://ideone.com/PJposF
*
**/
def jsonString = '''{
    "response": {
        "responseRecherche": {
            "1": {
                "Acheteur": {
                    "Id": "azertyui",
                    "Date de création": "2018-03-29 12:19:27",
                    "Date de modification": "2018-03-29 12:19:27",
                    "Statut": "01",
                    "Accessibilité": true
                }
            },
            "2": {
                "dumeAcheteur": {
                    "Id": "itkqiisy",
                    "Date de création": "2018-03-21 10:41:05",
                    "Date de modification": "2018-03-21 10:41:05",
                    "Statut": "01",
                    "Accessibilité": true
                }
            }
        }
    }
}'''

def json = new groovy.json.JsonSlurper().parseText(jsonString)
def recharge =  json.response.responseRecherche
println recharge.collect {k,v -> v.collect { key, value -> value.collectEntries{[(value.'Id'):value.'Date de création']} } }.flatten()
