/**
* http://stackoverflow.com/questions/38007809/soapui-how-to-extract-column-specific-data-form-the-json-response/38009693#38009693
**/
def str = '''Account Id#%#Territory#%#District#%#Area#%#Region#%#objname#%#~ID~#%#~Lat-Lon Linked~#%#~Latitude~#%#~Longitude~#%#~Lat-Lon Zip~#%#School Name#%#Address#%#City#%#State#%#ZIP#%#ZIP4#%#School Type#%#Status#%#School Level#%#Count Free Lunch#%#Count Reduced Lunch#%#Total Lunch Pgm#%#Total Students#%#PreKindergarten#%#Kindergarten#%#Grade 1#%#Grade 2#%#Grade 3#%#Grade 4#%#Grade 5#%#Grade 6#%#Grade 7#%#Grade 8#%#Grade 9#%#Grade 10#%#Grade 11#%#Grade 12#%#Territory1#%#Region1#%#lat#%#lon#%#terrid\r\n15709#%#Hartford, CT#%#New England#%#Unassigned#%#Unassigned#%#Account#%#15709#%#True#%#41.934711#%#-72.770021#%#06026#%#R. DUDLEY SEYMOUR SCHOOL#%#185 HARTFORD AVENUE#%#EAST GRANBY#%#CT#%#6026#%#9520#%#1#%#1#%#2#%#0#%#0#%#0#%#131#%#0#%#0#%#0#%#0#%#0#%#60#%#71#%#0#%#0#%#0#%#0#%#0#%#0#%#0#%#Hartford, CT#%#New England#%#5151204.33051376#%#-8100721.57141633#%#3\r\n15707#%#Hartford, CT#%#New England#%#Unassigned#%#Unassigned#%#Account#%#15707#%#True#%#41.934894#%#-72.730656#%#06026#%#EAST GRANBY HIGH SCHOOL#%#95 SOUTH MAIN STREET#%#EAST GRANBY#%#CT#%#6026#%#9550#%#1#%#1#%#3#%#0#%#0#%#0#%#219#%#0#%#0#%#0#%#0#%#0#%#0#%#0#%#0#%#0#%#0#%#57#%#55#%#53#%#54#%#Hartford, CT#%#New England#%#5151231.26605957#%#-8096340.03625871#%#3\r\n15708#%#Hartford, CT#%#New England#%#Unassigned#%#Unassigned#%#Account#%#15708#%#True#%#41.934894#%#-72.730656#%#06026#%#EAST GRANBY MIDDLE SCHOOL#%#95 SOUTH MAIN STREET#%#EAST GRANBY#%#CT#%#6026#%#9550#%#1#%#1#%#2#%#0#%#0#%#0#%#201#%#0#%#0#%#0#%#0#%#0#%#0#%#0#%#67#%#73#%#61#%#0#%#0#%#0#%#0#%#Hartford, CT#%#New England#%#5151231.26605957#%#-8096340.03625871#%#3\r\n15706#%#Hartford, CT#%#New England#%#Unassigned#%#Unassigned#%#Account#%#15706#%#True#%#41.944215#%#-72.732696#%#06026#%#ALLGROVE SCHOOL#%#33 TURKEY HILLS ROAD#%#EAST GRANBY#%#CT#%#6026#%#9570#%#1#%#1#%#1#%#0#%#0#%#0#%#275#%#3#%#69#%#65#%#82#%#56#%#0#%#0#%#0#%#0#%#0#%#0#%#0#%#0#%#0#%#Hartford, CT#%#New England#%#5152627.52929053#%#-8096567.12801993#%#3\r\n15710#%#Hartford, CT#%#New England#%#Unassigned#%#Unassigned#%#Account#%#15710#%#True#%#41.944215#%#-72.732696#%#06026#%#HOMEBOUND#%#33 TURKEY HILL ROAD#%#EAST GRANBY#%#CT#%#6026#%#674#%#4#%#3#%#4#%#0#%#0#%#0#%#0#%#0#%#0#%#0#%#0#%#0#%#0#%#0#%#0#%#0#%#0#%#0#%#0#%#0#%#0#%#Hartford, CT#%#New England#%#5152627.52929053#%#-8096567.12801993#%#3\r\n15923#%#Hartford, CT#%#New England#%#Unassigned#%#Unassigned#%#Account#%#15923#%#True#%#42.0027#%#-72.942#%#06027#%#HOMEBOUND#%#30 SOUTH ROAD#%#EAST HARTLAND#%#CT#%#6027#%#9710#%#4#%#3#%#4#%#0#%#0#%#0#%#0#%#0#%#0#%#0#%#0#%#0#%#0#%#0#%#0#%#0#%#0#%#0#%#0#%#0#%#0#%#Hartford, CT#%#New England#%#5161383.89953631#%#-8119866.29744296#%#3\r\n15922#%#Hartford, CT#%#New England#%#Unassigned#%#Unassigned#%#Account#%#15922#%#True#%#42.0027#%#-72.942#%#06027#%#HARTLAND ELEMENTARY SCHOOL#%#30 SOUTH ROAD#%#EAST HARTLAND#%#CT#%#6027#%#9710#%#1#%#1#%#1#%#0#%#0#%#0#%#2#%#0#%#25#%#17#%#26#%#29#%#37#%#36#%#38#%#35#%#40#%#0#%#0#%#0#%#0#%#Hartford, CT#%#New England#%#5161383.89953631#%#-8119866.29744296#%#3'''
//split by carriage return and new line
def data = str.split('\r\n')
//split by field to get the just column names from header row
def headers = data[0].split('#%#')
//Create map with just header keys, so that it can be used while storing the data
def headerMap = [:]
headers.each { header ->
    headerMap[header] = ''
}
/**
 * Closure allows you to query the required data
 * Need to pass all the records and row (human readable starting with 1) and header key/ field name
 * so the information is displaced as well as returns matched value
 */
def getData = { recordList, row, field ->
    println "Requested data : \n Row : ${row} \n Column : ${field} \n Column Value : ${recordList[row-1].get(field)}"
    recordList[row-1].get(field)
}
// This is the variable which holds all the records
// And each record will be in the form of a map so that it can be queried easily based on the field
def records = []
for (i=1;i<data.size();i++) {
    def fieldData = data[i].split('#%#')
    def record = headerMap.clone()
    if (fieldData.size() == headerMap.size()) {
        def keys = headerMap.keySet()
        for (j=0;j<keys.size();j++) {
            record[keys[j]] = fieldData[j]
        }
        println record
        //Add the record to records
        records << record
    }
}
//Some meta data information
println "Headers : ${headers}"
println "No of headers : ${headers.size()}"
println "No of rows : ${records.size()}"
/**
 * Here is how you can query the specific data
 * and since it returns value, you can also assign it variable as well
 */
getData(records, 1, 'Territory')
getData(records, 7, 'Account Id')
