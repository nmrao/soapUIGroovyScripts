/*
* Below is the Setup Script at the test suite
* which changes the order of the test cases alphabetically.
* Refer: https://stackoverflow.com/questions/47633766/groovy-script-keeps-running-when-running-a-test-suite/47647409#47647409
**/
//Get the sorted order of the test case which is expected order
def newList = testSuite.testCaseList.name.sort()
log.info "Expected order of test cases: ${newList}"

//Get the current index of the test case
def getTestCaseIndex = { name -> testSuite.getIndexOfTestCase(testSuite.getTestCaseByName(name))}

//Closure definition and this is being called recursively to make the desired order
def rearrange
rearrange = {
    def testCaseNames = testSuite.testCaseList.name
    if (testCaseNames != newList) {
        log.info testCaseNames
        newList.eachWithIndex { tc, index ->
            def existingIndex = getTestCaseIndex(tc)
            if (index != existingIndex) {
                testSuite.moveTestCase(index, existingIndex-index)
                rearrange()
            }
        }
    } else {
        log.info 'All cases sorted'
    }
}

//Call the closure
rearrange()
