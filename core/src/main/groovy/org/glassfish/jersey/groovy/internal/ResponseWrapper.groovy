package org.glassfish.jersey.groovy.internal

import groovy.json.JsonSlurper
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
    private MediaType mediaType

    //
    // self init
    //

    def ResponseWrapper and(MediaType mediaType) {
        this.mediaType = mediaType
        return this
    }

    //
    // final
    //

    def String getText() {
        mediaType = MediaType.TEXT_PLAIN_TYPE
        Invocation.Builder builder = getWebTarget().request(mediaType)
        checkMethod(Method.GET)
        return builder.get(String.class)
    }

    def GPathResult getXml() {
        mediaType = MediaType.APPLICATION_XML_TYPE
        Invocation.Builder builder = getWebTarget().request(mediaType) //TODO moznost zadat jiny a presto XML typ
        checkMethod(Method.GET)
        InputStream inputStream = builder.get().readEntity(InputStream)
        try {
            return new XmlSlurper().parse(inputStream)
        } finally {
            inputStream.close()
        }
    }

    def Object getJson() {
        mediaType = MediaType.APPLICATION_JSON_TYPE
        Invocation.Builder builder = getWebTarget().request(mediaType)
        checkMethod(Method.GET)
        Reader reader = builder.get().readEntity(Reader)
        try {
            return new JsonSlurper().parse(reader)
        } finally {
            reader.close()
        }
    }

/*
    def <T> T rightShift(Class<T> type) {
        mediaType = MediaType.APPLICATION_XML_TYPE
        Invocation.Builder builder = getWebTarget().request(mediaType)
        checkMethod(Method.GET)
        return builder.get(type)
    }
*/

    def <T> T asType(Class<T> type) {
        Invocation.Builder builder = getWebTarget().request(mediaType)
        checkMethod(Method.GET)
        return builder.get(type)
    }

    //
    // utils
    //

    private def getWebTarget() {
        return ClientBuilder.newClient().target(target)
    }

    private def checkMethod(Method expectedMethod) {
        if (method != expectedMethod) {
            throw new IllegalStateException("Wrong REST method ($method) to process request!")
        }
    }

    //
    // enum Method
    //

    static enum Method {
        GET,
        POST
    }

}
