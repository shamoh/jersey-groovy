package org.glassfish.jersey.groovy.internal

import javax.ws.rs.GET

class Util {

    static def void test () {
        def result = GET.from "http://a.b.c/"
//        def result = GET from "http://a.b.c/"
//        result = "123".length()
//        result = test2 ("1", "2", "3")
//        result = org.glassfish.jersey.groovy.internal.Util2 test2 "1", "2", "3"
        println "| " + result

    }

}
