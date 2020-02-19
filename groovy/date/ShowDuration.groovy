/**
* Compatible with JDK 8
* Ref: https://community.smartbear.com/t5/SoapUI-Pro/Need-to-find-the-datediff-for-the-below-code/m-p/197438#M45130
* finds duration between two dates and show the respective value
*
**/

import static java.time.LocalDateTime.parse
import static java.time.Duration.between

def t1 = '2007-12-03T10:15:30'
def t2 = '2007-12-11T16:01:10'
def t3 = '2007-12-23T11:11:10'

def getDuration = { date1, date2 ->
    def result
    switch(between(parse(date1), parse(date2)).toDays() as Integer) {
        case 8..15:
            result = 'Fortnight'
            break
        case 16..31:
            result = 'Month'
            break
        default:
            result = 'not matching'
            break
    }
    result
}

log.info getDuration(t1, t2)
assert 'Fortnight' == getDuration(t1, t2)
log.info getDuration(t2, t3)
log.info getDuration(t1, t3)
