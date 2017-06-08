/**
* Build soap request xml using csv data
* so reference http://stackoverflow.com/questions/37956927/groovy-reference-repeating-nodes-values-in-xml-with-xpath-interpolation-misuse/37958846#37958846
**/
import groovy.xml.*
import static com.xlson.groovycsv.CsvParser.parseCsv
//closure which builds the request based on the data provided
def requestBuilder = { csvData ->
    def builder = new StreamingMarkupBuilder()
    builder.encoding = 'UTF-8'
    def soapRequest = builder.bind {
        mkp.xmlDeclaration()
        namespaces << [soap: 'http://schemas.xmlsoap.org/soap/envelope/',
                           web : 'http://www.webserviceX.NET']
        soap.Envelope {
            soap.Header{}
            soap.Body {
              //loop thru the rows
                csvData.each { row ->
                  //create GetWeather element for each row
                    web.GetWeather{
                        web.CityName(row.CityName)
                        web.CountryName(row.CountryName)
                    }
                }
            }
        }
    }
}
//Used fixed csv data. But you can replace it with reading from file too
def csv = '''CityName,CountryName
Cairo,Africa
Heidelberg,Germany
Strasbourg,France'''
/**
//use this to read from file and remove above statement
def csv = new File('/absolute/csv/file/path').text
**/
//parse the csv using groovy csv library
def data = parseCsv(csv)
//call the above closure get the request and serialize it to string 
def request = XmlUtil.serialize(requestBuilder(data))
log.info request
