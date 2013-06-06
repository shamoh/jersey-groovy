package org.glassfish.jersey.groovy.internal

import javax.ws.rs.GET
import javax.ws.rs.POST

/**
 * @author Libor Kramolis (libor.kramolis at oracle.com)
 */
class JerseyStaticExtension {

    //
    // javax.ws.rs.GET
    //

    public static ResponseWrapper getAt(GET method, String target) {
        return new ResponseWrapper(method: ResponseWrapper.Method.GET, target: target)
    }

    public static ResponseWrapper getAt(POST method, String target) {
        return new ResponseWrapper(method: ResponseWrapper.Method.POST, target: target)
    }

}
