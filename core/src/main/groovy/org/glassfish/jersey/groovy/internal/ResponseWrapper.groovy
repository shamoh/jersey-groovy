package org.glassfish.jersey.groovy.internal

import groovy.util.slurpersupport.GPathResult

import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.MediaType

/**
 * @author Libor Kramolis (libor.kramolis at oracle.com)
 */
class ResponseWrapper {

    Method method
    String target

    def String getText() {
        Invocation.Builder builder = getWebTarget().request(MediaType.TEXT_PLAIN)
        if (method == Method.GET) {
            builder.get(String.class)
        } else {
            throw new IllegalStateException("Wrong REST method ($method) to get text!")
        }
    }

    def GPathResult getXml() {
        Invocation.Builder builder = getWebTarget().request(MediaType.APPLICATION_XML) //TODO moznost zadat jiny a presto XML typ
        InputStream inputStream = builder.get().readEntity(InputStream)
        try {
            return new XmlSlurper().parse(inputStream)
        } finally {
            inputStream.close()
        }
    }

    //
    // utils
    //

    private def getWebTarget() {
        return ClientBuilder.newClient().target(target)
    }

    //
    // enum Method
    //

    static enum Method {
        GET,
        POST
    }

}
