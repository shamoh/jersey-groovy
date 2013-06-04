package org.glassfish.jersey.groovy.internal

import javax.ws.rs.GET

class Util2 {

    static def String test2(String s1, String s2, String s3) {
        return "[ $s1 | $s2 | $s3 ]"
    }

}
