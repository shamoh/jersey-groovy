package org.glassfish.jersey.groovy.internal

import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author Libor Kramolis (libor.kramolis at oracle.com)
 */
class JerseyExtensionSpecTest extends Specification {

    @Unroll
    def "String.div (#base, #path)"(String base, String path, String result) {
        expect:
        base/path == result

        where:
        base                          | path          || result
        "http://localhost:8080/base/" |  "helloworld" || "http://localhost:8080/base/helloworld"
        "http://localhost:8080/base/" | "/helloworld" || "http://localhost:8080/base/helloworld"
        "http://localhost:8080/base"  |  "helloworld" || "http://localhost:8080/base/helloworld"
        "http://localhost:8080/base"  | "/helloworld" || "http://localhost:8080/base/helloworld"
    }

}
