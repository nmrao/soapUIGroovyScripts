//http://stackoverflow.com/questions/39229462/soapui-export-and-import-properties-to-file-by-script/39240253#39240253
/**
 * this method imports properties to a test case from a file.
 * @param context
 * @param filePath
 */
def importPropertiesToTestCase(def context,String filePath) {
    def  props = new Properties()
    def propFile = new File(filePath)
    //load the properties files into properties object
    props.load(propFile.newDataInputStream())
    //loop thru the properties and set them at test case level
    props.each {
        context.testCase.setPropertyValue(it.key, it.value.toString())
    }
}
//How to use above method. Make sure you have file with properties, change path if needed.
importPropertiesToTestCase(context, 'D:/Temp/testCase.properties')
