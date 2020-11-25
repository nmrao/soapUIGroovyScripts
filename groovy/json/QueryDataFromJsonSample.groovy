def jsonString = '''[
    {
        "firstName": "John",
        "lastName": "doe",
        "age": 26,
        "address": {
            "streetAddress": "naist street",
            "city": "Nara",
            "postalCode": "630-0192"
        },
        "phoneNumbers": [
            {
                "type": "iPhone",
                "number": "0123-4567-8888"
            },
            {
                "type": "home",
                "number": "0123-4567-8910"
            }
        ]
    },
    {
        "firstName": "Kevin",
        "lastName": "Hall",
        "age": 10,
        "address": {
            "streetAddress": "naist street",
            "city": "Nara",
            "postalCode": "630-0192"
        },
        "phoneNumbers": [
            {
                "type": "iPhone",
                "number": "0123-4567-8899"
            }
        ]
    },
    {
        "firstName": "Mark",
        "lastName": "Dennis",
        "age": 56,
        "address": {
            "streetAddress": "naist street",
            "city": "Nara",
            "postalCode": "630-0192"
        },
        "phoneNumbers": [
            {
                "type": "iPhone",
                "number": "0123-4567-8889"
            },
            {
                "type": "home",
                "number": "0123-4567-8911"
            }
        ]
    },
     {
        "firstName": "Penny",
        "lastName": "Moris",
        "age": 80,
        "address": {
            "streetAddress": "naist street2",
            "city": "Nara",
            "postalCode": "630-0192"
        },
        "phoneNumbers": [
            {
                "type": "home",
                "number": "0123-4567-9911"
            }
        ]
    }
]'''

def json = new groovy.json.JsonSlurper().parseText(jsonString)
def iPhone = {it.type == 'iPhone'}
def homePhone = {it.type == 'home'}
def seniorsCitizens = {it.age > 60 }
def kids = {it.age < 13 }
def personHasPhone = { person, klosure ->  person.phoneNumbers.find (klosure) }
def getPersonsWithPhone = { klozure=iPhone -> json.findAll{ person -> personHasPhone(person, klozure) } }
def getPersonDataWithPhone = { key, k=iPhone -> getPersonsWithPhone(k)."$key" }

println getPersonsWithPhone(iPhone).'firstName'

//Get firstName(s) who has iPhone
println getPersonDataWithPhone('firstName')

//Get firstName(s) who has homePhone
println getPersonDataWithPhone('firstName', homePhone)

//Get Address(s) who has iPhone
println getPersonDataWithPhone('address')

//Get all zip who has iPhone
println getPersonDataWithPhone('address').postalCode

//Get unique zip codes who has iPhone
println getPersonDataWithPhone('address').postalCode.unique()

//Get last names of senior citizens who has home phone
println getPersonsWithPhone(homePhone).findAll(seniorsCitizens ).lastName

//Get first names of kids who has iPhone
println getPersonsWithPhone().findAll(kids).firstName

