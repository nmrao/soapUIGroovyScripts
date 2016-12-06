/**
* This is script assertion for the soap request test step.
* Extracts CDATA of response first.
* Later checks if the xpath is ok
* refer for more details: http://stackoverflow.com/questions/40959157/how-to-get-a-node-on-soap-uis-test-case-xpath
**/
//Closure to extract data of given node name
def searchData = { data, element ->
   def parsedData = new XmlSlurper().parseText(data)
   parsedData.'**'.find {it.name() == element} as String
}

//Closure to check the xpath
def searchByXpath = {data, xpath ->
   def holder = new com.eviware.soapui.support.XmlHolder(data)
   holder.getNodeValue(xpath)
}

//check if the response is non empty
//assert context.response, "Response is empty or null"
def xml = """<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
  <soap:Body>
    <GetCitiesByCountryResponse xmlns="http://www.webserviceX.NET">
            <GetCitiesByCountryResult><![CDATA[<NewDataSet>
      <Table>
        <Country>Belarus</Country>
        <City>Riga Airport</City>
      </Table>
      <Table>
        <Country>Mauritius</Country>
        <City>Plaisance Mauritius</City>
      </Table>
      <Table>
        <Country>Mauritius</Country>
        <City>Rodrigues</City>
      </Table>
      <Table>
        <Country>Cyprus</Country>
        <City>Ercan</City>
      </Table>
      <Table>
        <Country>Cyprus</Country>
        <City>Larnaca Airport</City>
      </Table>
      <Table>
        <Country>Cyprus</Country>
        <City>Athalassa</City>
      </Table>
      <Table>
        <Country>Cyprus</Country>
        <City>Paphos Airport</City>
      </Table>
      <Table>
        <Country>Cyprus</Country>
        <City>Akrotiri</City>
      </Table>
      <Table>
        <Country>Austria</Country>
        <City>Niederosterreich / Lugplatz  Vos</City>
      </Table>
      <Table>
        <Country>Austria</Country>
        <City>Graz-Thalerhof-Flughafen</City>
      </Table>
      <Table>
        <Country>Austria</Country>
        <City>Innsbruck-Flughafen</City>
      </Table>
      <Table>
        <Country>Austria</Country>
        <City>Klagenfurt-Flughafen</City>
      </Table>
      <Table>
        <Country>Austria</Country>
        <City>Linz / Hoersching-Flughafen</City>
      </Table>
      <Table>
        <Country>Austria</Country>
        <City>Salzburg-Flughafen</City>
      </Table>
      <Table>
        <Country>Austria</Country>
        <City>Wien / Schwechat-Flughafen</City>
      </Table>
      <Table>
        <Country>Austria</Country>
        <City>Aigen Im Ennstal</City>
      </Table>
      <Table>
        <Country>Austria</Country>
        <City>Horsching Aus-Afb</City>
      </Table>
      <Table>
        <Country>Austria</Country>
        <City>Schwaz Heliport</City>
      </Table>
      <Table>
        <Country>Austria</Country>
        <City>Tulln</City>
      </Table>
      <Table>
        <Country>Austria</Country>
        <City>Zeltweg</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Jakutsk</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Cul'Man</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Ekimchan</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Habarovsk</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Troickoe</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Anadyr</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Buhta Providenja</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Magadan</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Petropavlovsk-Kamchatskij</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Juzhno-Sahalinsk</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Vladivostok</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Chita</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Irkutsk</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Ust'Ordynskij</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Bodajbo</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Kirensk</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Nizhneudinsk</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Horinsk</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Ulan-Ude</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Arhangel'Sk</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Kotlas</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>St. Peterburg</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Murmansk</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Velikie Luki</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Tot'Ma</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Vologda</City>
      </Table>
      <Table>
        <Country>Belarus</Country>
        <City>Vitebsk</City>
      </Table>
      <Table>
        <Country>Belarus</Country>
        <City>Minsk</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Barnaul</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Enisejsk</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Novosibirsk</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Krasnodar</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Mineral'Nye Vody</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Rostov-Na-Donu</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Adler</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Elista</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Volgograd</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Hanty-Mansijsk</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Surgut</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Ekaterinburg</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Brjansk</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Moscow / Sheremet'Ye</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Tver</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Voronez</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Moscow / Vnukovo</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Ust', Kulom</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Syktyvkar</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Penza</City>
      </Table>
      <Table>
        <Country>Russian Federation</Country>
        <City>Samara</City>
      </Table>
      <Table>
        <Country>Brunei Darussalam</Country>
        <City>Brunei Airport</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Archerfield Aerodrome</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Amberley Aerodrome</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Alice Springs Aerodrome</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Brisbane Airport M. O</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Coolangatta Airport Aws</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Cairns Airport</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Charleville Airport</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Gladstone</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Longreach Airport</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Mount Isa Amo</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Mackay Mo</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Oakey Aerodrome</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Proserpine Airport</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Rockhampton Airport</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Broome Airport</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Townsville Amo</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Weipa City</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Gove Airport</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Tennant Creek Airport</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Yulara Aws</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Albury Airport</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Devonport East</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Goldstream Aws</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>East Sale Aerodrome</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Hobart Airport</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Launceston Airport</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Laverton Aerodrome</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Moorabbin Airport Aws</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Mount Gambier Aerodrome</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Mildura Airport</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Melbourne Airport</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Macquarie Island</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Wynyard West</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Adelaide Airport</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Albany Airport</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Broken Hill Patton Street</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Ceduna Airport</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Derby</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Darwin Airport</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Bullsbrook Pearce Amo</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Edinburgh M. O.</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Forrest Airport</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Geraldton Airport</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Kalgoorlie Boulder Amo</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Kununurra Kununurra Aws</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Leigh Creek Airport</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Learmonth Airport</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Meekatharra Airport</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Port Hedland Pardoo</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Parafield Airport</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Belmont Perth Airport</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Katherine Aerodrome</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Woomera Aerodrome</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Bankstown Airport Aws</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Canberra</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Coffs Harbour Mo</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Cooma</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Camden Airport</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Dubbo</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Norfolk Island Airport</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Nowra Ran Air Station</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Richmond Aus-Afb</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Sydney Airport</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Tamworth Airport</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Wagga Airport</City>
      </Table>
      <Table>
        <Country>Australia</Country>
        <City>Williamtown Aerodrome</City>
      </Table>
    </NewDataSet>]]></GetCitiesByCountryResult>
        </GetCitiesByCountryResponse>
    </soap:Body>
</soap:Envelope>"""

assert xml, "Response is empty or null"
//Gets the CDATA part of the response
def cdata = searchData(xml, 'GetCitiesByCountryResult')

//Gets the xpath result
def cityName = 'Rodrigues'
def result = searchByXpath(cdata, "exists(//Table[City = '$cityName'])")
log.info "Is city ${cityName} exist in the table: ${result}" 

//Check the xpath result
assert result, "${cityName} does not exist in the result table"
