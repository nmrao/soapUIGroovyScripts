/**
* Ref: https://community.smartbear.com/t5/SoapUI-Pro/TechCorner-Challenge-4-How-to-Generate-a-Request-Body-Based-on/m-p/203916
*
**/
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import static org.apache.poi.ss.usermodel.Cell.*
import groovy.xml.StreamingMarkupBuilder
import groovy.xml.XmlUtil

//Used to create the data map from excel cells 
def columnMap = [0: 'title', 1: 'artist', 2: 'price']

//Validate if the data file exists and only supports .xlsx files as input
def isFileValid = { 	
	assert null != it, 'File path is empty'
	def xFile = new File(it)
	assert xFile.exists(), "${it} file doesn't exists"
	assert it.endsWith('.xlsx'), 'Only allows file extension .xlsx'
	true
}

// A closure that creates data (list of rows; and each row as map) from input file
// User can choose the sheet name (default is Sheet 1), throws if sheet does not exists
// And whether to ignore first row or not
// Also handles empty rows and empty cells
// Support apache-poi 3.8 and tested in SoapUI 5.4
def getData = { filepath, isIgnoreFirstRow = true, sheetName = 'Sheet1' ->
	def data = [] 
	if (isFileValid(filepath)) {
		def sheet = new XSSFWorkbook(filepath).getSheet sheetName
		assert null != sheet, "${sheetName} does not existing in the ${filepath}"		
		sheet.rowIterator().eachWithIndex { row, rowId ->
			def map = [:]
			if (!isIgnoreFirstRow || rowId != 0) {
				for (index = 0; index <row.lastCellNum;index++){
					cell = row.getCell(index, row.CREATE_NULL_AS_BLANK);
					switch (cell.cellType) {
            				case CELL_TYPE_STRING: map.put(columnMap[index], cell.stringCellValue); break
            				case CELL_TYPE_NUMERIC: map.put(columnMap[index], cell.numericCellValue); break
            				case CELL_TYPE_BOOLEAN: map.put(columnMap[index], cell.booleanCellValue); break
            				case CELL_TYPE_FORMULA: map.put(columnMap[index], cell.getCachedFormulaResultType()); break
            				default: map.put(columnMap[index], ''); break
					}
        			}
        			data << map
			}				
		}
	}	
	assert data.size(), 'No data created from file'
	data
}

//A closure that builds the xml request based on the user input
def createRequest = { cds, trans ->
	def nameSpacesMap = [
            soap: 'http://schemas.xmlsoap.org/soap/envelope/',
            m: 'https://www.w3schools.com/transaction/'
    ]
    def builder = new StreamingMarkupBuilder()
    builder.encoding ='utf-8'
    def soapRequest = builder.bind {
        namespaces << nameSpacesMap
        soap.Envelope {
            soap.Header {
            	m.Trans('soap:actor': 'https://www.w3schools.com/code/', trans)
            }
            soap.Body {
               CATALOG {
               	cds.each  { cd ->
               		CD {
               			TITLE cd.title
               			ARTIST cd.artist
               			PRICE cd.price
               		}
               	}
               }
            }
        }
    }
    XmlUtil.serialize soapRequest 
}

//Actuall flow of the script starts here
def file = context.testCase.getPropertyValue('DATA_FILE')
def sheet =  context.testCase.getPropertyValue('SHEET_NAME') ?: 'Sheet1'
def ignoreFirstRow = context.testCase.getPropertyValue('IGNORE_FIRST_ROW')?.toBoolean() ?: true

//Call to the closures
log.info createRequest(getData(file, ignoreFirstRow, sheet), 234)
