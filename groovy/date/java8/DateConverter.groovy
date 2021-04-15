/**
* Date time convert to different zone
* Ref: https://community.smartbear.com/t5/API-Functional-Security-Testing/Set-date-timezone-in-REST-response/m-p/215778#M48672
**/

import java.time.ZonedDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

//Generic date pattern
def dateFormat = "yyyy-MM-dd'T'HH:mm:ss[.SSS][xxx][xx][X]"

/**
 * Closure returns DateTimeFormatter
 * based on the input pattern 
 * otherwise, ISO_OFFSET_DATE_TIME Date format
 */
def getFormatter = { 
	(null != it) ? DateTimeFormatter.ofPattern(it, Locale.ROOT) : DateTimeFormatter.ISO_OFFSET_DATE_TIME
}

/**
 * Converts date time string from one zone to another 
 * @sourceDateString - user date time string to convert
 * @offset - offset value of the target time zone 
 * @targetDatePattern - date pattern of the converted date time, default is "yyyy-MM-dd'T'HH:mm:ss[.SSS][xxx][xx][X]"
 * @sourceDatePattern - date pattern of the source date string, default is "yyyy-MM-dd'T'HH:mm:ss[.SSS][xxx][xx][X]"
 * @return formatted date time string with offset
 */
def convertDate = { sourceDateString, offset, targetDatePattern = dateFormat, sourceDatePattern = dateFormat ->  
	def sdate = ZonedDateTime.parse(sourceDateString, getFormatter(sourceDatePattern))
	def tdate = sdate.withZoneSameInstant(ZoneOffset.of(offset))
	tdate.format(getFormatter(targetDatePattern))
}

//Call
println convertDate('2021-04-11T06:35:56Z', '+02:00', "yyyy-MM-dd'T'HH:mm:ss")
