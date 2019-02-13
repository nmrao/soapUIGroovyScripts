/**
* Below script generate next date in the given format each time it is executed
* and stores the result in test case level custom property "NEW_DATE"
* Use can use ${#TestCase#NEW_DATE} in later test request steps or
* context.expand('${#TestCase#NEW_DATE}') in groovy script
*
* Ref:
**/

//Change the format as needed
def dateFormat = 'yyyy-MM-dd'

def getToday = { -> new Date().format(dateFormat) }

def setDate = { date = null ->
	date = date ?: getToday()
	context.testCase.setPropertyValue('NEW_DATE', date)
}

def getNextDate = {
	def existingDate = context.testCase.getPropertyValue('NEW_DATE')
	def newDate
	if (existingDate) {
		newDate = (Date.parse(dateFormat, existingDate) + 1).format(dateFormat)
	} else {
		newDate = getToday()
	}
	setDate(newDate)
	newDate
}

log.info getNextDate()
