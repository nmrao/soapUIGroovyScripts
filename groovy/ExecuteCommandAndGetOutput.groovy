/**
* Below script runs any command along with it arguments
* and reads the output of the command and set it as
* test case level property whose name is "propertyName"
* provide the value for command as you needed.
**/
def command = "you batch script command"
log.info command
def process = command.execute()
def outputStream = new StringBuffer()
def errorStream = new StringBuffer()
process.consumeProcessOutput(outputStream, errorStream)
process.waitFor()
log.info("return code: ${process.exitValue()}")
log.error("standard error: ${process.err.text}")
log.info("standard out: ${process.in.text}" + outStream.toString())
context.testCase.setPropertyValue("propertyName", outStream.toString())
