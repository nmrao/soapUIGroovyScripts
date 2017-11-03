/**
* Ref: https://stackoverflow.com/questions/46949726/soup-ui-jdbc-custom-parameter-definition?noredirect=1#comment81133230_46949726
* Below script would set the query to Jdbc request from groovy script.
**/

import groovy.text.SimpleTemplateEngine

//Edit the jdbc test step name if required
def nextStep = 'JDBC Request'

//Edit query if required, but not ids variable below as that is used in binding
def query = 'select * from job where id in ( $ids )'
def binding = [ids: context.testCase.getPropertyValue('IDS')]
def step = context.testCase.testSteps[nextStep]
def template = new SimpleTemplateEngine().createTemplate(query).make(binding)

log.info "updated query : ${template.toString()}"

//Set the query to jdbc step
step.jdbcRequestTestStepConfig.query = template.toString()
