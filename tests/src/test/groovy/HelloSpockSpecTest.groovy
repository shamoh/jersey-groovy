import spock.lang.*

class HelloSpockSpecTest extends spock.lang.Specification {
    def "length of Spock's and his friends' names"() {
        expect:
        name.size() == length

        where:
        name     | length
        "Spock"  | 5
        "Kirk"   | 4
        "Scotty" | 6
    }

    def "Test StringExtension :: reverseToUpperCase"() {
        expect:
        ".NUK YKCUOTULZ SILIRP" == "Prilis Zlutoucky Kun.".reverseToUpperCase()
    }

}
