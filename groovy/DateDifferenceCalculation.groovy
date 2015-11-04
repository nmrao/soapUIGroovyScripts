import groovy.time.DatumDependentDuration
import org.apache.log4j.Logger

import java.text.SimpleDateFormat

class AgeCalculator {

    String dob
    String when
    String format = 'yyyy-MM-dd'
    Logger log

    def getDate = { String date, String format ->
        def dateFormat = new SimpleDateFormat(format)
        dateFormat.parse(date)
    }

    def getAge() {
        if (!dob) {
            log.error "dob is mandatory"
        }
        Date now
        if (!when) {
            log.info "Value not provided for when, considering today's date"
            now = new Date()
        } else {
            now = getDate(when, format)
        }
        Date dob = getDate(dob,format)
        dob.clearTime()
        now.clearTime()
        assert dob < now
        Calendar.instance.with { c ->
            c.time = dob
            def (years, months, days) = [ 0, 0, 0 ]

            while( ( c[ YEAR ] < now[ YEAR ] - 1 ) ||
                    ( c[ YEAR ] < now[ YEAR ] && c[ MONTH ] <= now[ MONTH ] ) ) {
                c.add( YEAR, 1 )
                years++
            }

            while( ( c[ YEAR ] < now[ YEAR ] ) ||
                    ( c[ MONTH ] < now[ MONTH ] && c[ DAY_OF_MONTH ] <= now[ DAY_OF_MONTH ] ) ) {
                // Catch when we are wrapping the DEC/JAN border and would end up beyond now
                if( c[ YEAR ] == now[ YEAR ] - 1 &&
                        now[ MONTH ] == JANUARY && c[ MONTH ] == DECEMBER &&
                        c[ DAY_OF_MONTH ] > now[ DAY_OF_MONTH ] ) {
                    break
                }
                c.add( MONTH, 1 )
                months++
            }

            while( c[ DAY_OF_YEAR ] != now[ DAY_OF_YEAR ] ) {
                c.add( DAY_OF_YEAR, 1 )
                days++
            }
            log.info "Years : ${years}"
            new DatumDependentDuration( years, months, days, 0, 0, 0, 0 )
        }
    }
}

//Provide dateFormat as needed
def dateFormat = 'dd/MM/yyyy'

//Please assign value for dateOfBirth from data driven by editing, for now using assumed values

def dateOfBirth = '26/12/1995'
def quarterEndDate = '31/12/2015'
def calculate = new AgeCalculator(log: log,
        dob: dateOfBirth,
        when: quarterEndDate,
        format: dateFormat)
assert calculate.age.years >= 18, "Not an adult"
