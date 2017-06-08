/**
* http://stackoverflow.com/questions/38032465/how-to-convert-soap-xml-response-to-delimited/38067469#38067469
* this script will read the previous step response
* extract the cdata at the given xpath
* read all the records and transfroms into csv file
**/

import com.eviware.soapui.support.XmlHolder
import groovy.xml.*

/**Define the output file name in test case custom property say OUTPUT_FILE_NAME and value as absolute file path
* otherwise, it write a file chargedata.csv in system temp directory
**/
def outputFileName = context.testCase.getPropertyValue('OUTPUT_FILE_NAME') ?: System.getProperty("java.io.tmpdir")+ '/chargedata.csv'

//csv field separator - change it if needed
def delimiter = ','

/**
* Below statement will fetch the previous request step response. 
*/
def response = context.testCase.testStepList[context.currentStepIndex - 1].testRequest.response.responseContent

//Create the xml holder object to get the xpath value which cdata in this case
def responseHolder = new XmlHolder(response)
def xpath = '//*:Charges_FileResponse/*:Charges_FileResult'

//Get the cdata part from above xpath which is a string
def data = responseHolder.getNodeValue(xpath)

//This again parses the xml inside of cdata
def chargeRecords = new XmlParser().parseText(data)

//This is going hold all the data from ChargeRecords
def chargeRecordsDataStructure = []

//This is to hold all the headers
def headers = [] as Set

/**
* This is to create Charge data
**/
def buildChargeDataStructure = { charge ->
	def chargeDataStructure = new Expando()
	charge.children().each {
		def elementName = it.name()
		def elementText = it.value().join()
		chargeDataStructure[elementName] = elementText
		//Add to headers list if not already added
		(elementName in headers) ?: headers << elementName
	}
	chargeDataStructure
}

/**
* this is to create a csv row in string format
**/
def createRow = { recordDataStructure ->
	def row = new StringBuffer()
	headers.each {
		if (row) {
			row += delimiter + recordDataStructure[it] ?: ''
		} else {
			row += recordDataStructure[it] ?: ''
		}
	}
	row.toString()+'\n'
}

//Build the whole data structure of Charge Records
chargeRecords.Charge.each { charge ->
	chargeRecordsDataStructure << buildChargeDataStructure( charge )
}

//Build the rows
def rows = new StringBuffer()
rows << headers.join(',') +'\n'
chargeRecordsDataStructure.each { rows << createRow (it)}

//Write the rows into file
new File(outputFileName).text = rows
