/**
* this example shows how to assert a json element of an array
**/
import net.sf.json.groovy.JsonSlurper
def jsonText = '''{
  "cars": [
    {
      "name" : "Jetta",
      "madeBy" : "Volkswagen"
    },
    {
      "name" : "Polo GT",
      "madeBy" : "Volkswagen"
    },
    {
      "name" : "i30",
      "madeBy" : "Hyundai"
    }
  ]
}'''
def jsonSlurper = new JsonSlurper()
def cars = jsonSlurper.parseText(jsonText).cars
def expectedName = 'Jetta'
def expectedValueExists=false
if (cars) {
    cars.each{if (it.name==expectedName) {expectedValueExists=true} }
}
if (expectedValueExists) { println "Expected car ${expectedName} exists"}
assert expectedValueExists, "Expected car name does not exists"
