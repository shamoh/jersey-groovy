package org.glassfish.jersey.groovy.internal

import javax.ws.rs.GET
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.WebTarget
import javax.ws.rs.core.MediaType

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

    public static String text(GET method, String target) {
        WebTarget webTarget = ClientBuilder.newClient().target(target)
        return webTarget.request(MediaType.TEXT_PLAIN).get(String.class)
    }

    //
    // utils
    //



}
