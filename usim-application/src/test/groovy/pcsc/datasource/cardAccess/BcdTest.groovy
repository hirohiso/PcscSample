package pcsc.datasource.cardAccess

import spock.lang.Specification

class BcdTest extends Specification {
    def "test"(){
        when:
        def actual = Bcd.of(byteArray)
        then:
        actual.bcdString() == string
        where:
        byteArray || string
        [0x12,0x34] as byte[] || "1234"
    }

    def "testReverse"(){
        when:
        def actual = Bcd.ofReverseOrder(byteArray)
        then:
        actual.bcdString() == string
        where:
        byteArray || string
        [0x12,0x34] as byte[] || "2143"
    }
}
