/**
 * http://stackoverflow.com/questions/39229462/soapui-export-and-import-properties-to-file-by-script/39240253#39240253
 * this method exports test case properties into a file.
 * @param context
 * @param filePath
 */
def exportTestCaseProperties(def context, String filePath) {
    def  props = new Properties()
    //Get all the property names of test cases
    def names = context.testCase.getPropertyNames()
    //loop thru names and set Properties object
    if (names) {
        names.each { name ->
            log.info "Set property ${name}"
            props.setProperty(name, context.testCase.getPropertyValue(name))
        }
        //Write properties object to a file
        def propFile = new File(filePath)
        props.store(propFile.newWriter(), null)
        log.info "Check the properties file: ${filePath}"
    } else {
        log.info "There does not seem to have any test case properties to write, check it."
    }
}
//How to use above method
exportTestCaseProperties(context, 'D:/Temp/testCase1.properties')
