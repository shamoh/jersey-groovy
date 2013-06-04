package org.glassfish.jersey.groovy.internal

import spock.lang.Specification

import javax.ws.rs.GET
import javax.ws.rs.core.Response

class GetSpecTest extends Specification {

    def "GET response"() {
        expect:
        def response = GET.response "http://localhost:8080/base"
        response instanceof Response
    }

}
