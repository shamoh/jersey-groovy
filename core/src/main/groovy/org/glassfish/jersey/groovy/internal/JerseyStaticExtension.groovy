package org.glassfish.jersey.groovy.internal

import javax.ws.rs.GET

/**
 * Libor Kramolis (libor.kramolis (at) oracle.com)
 */
class JerseyStaticExtension {

    //
    // javax.ws.rs.GET
    //

    public static String from(GET method, String target) {
        return "> $target <"
    }

}
