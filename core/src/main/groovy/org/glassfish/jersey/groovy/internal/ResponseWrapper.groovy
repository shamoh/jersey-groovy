package org.glassfish.jersey.groovy.internal

import groovy.json.JsonSlurper
import groovy.util.slurpersupport.GPathResult

import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.Entity
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

/**
 * @author Libor Kramolis (libor.kramolis at oracle.com)
 */
class ResponseWrapper {

    Method method
    String target
    private MediaType requestMediaType
    private MediaType entityMediaType
    private Response response
    private Entity entity

    //
    // self init
    //

    def ResponseWrapper and(MediaType mediaType) {
        this.requestMediaType = mediaType
        return this
    }

    def ResponseWrapper or(MediaType mediaType) {
        this.entityMediaType = mediaType
        return this
    }

    def <T> ResponseWrapper leftShift(T data) {
        this.entity = Entity.entity(data, entityMediaType == null ? MediaType.TEXT_PLAIN_TYPE : entityMediaType)

        return this
    }

    def ResponseWrapper leftShift(Closure closure){
        println ">> $closure"

        return this
    }
/*
    private static xmlContent(Closure clos){
        def writer = new StringWriter()
        def xml = new MarkupBuilder(writer)
        if(xmlDeclaration){
            xml.mkp.xmlDeclaration(version:'1.0',encoding:'UTF-8')
        }
        clos.delegate=xml
        clos.call()
        writer.toString()
    }
*/

    //
    // final
    //

    def String getText() {
        checkMethod(Method.GET)
        requestMediaType = MediaType.TEXT_PLAIN_TYPE
        Invocation.Builder builder = getWebTarget().request(requestMediaType)
        return builder.get(String.class)
    }

    def GPathResult getXml() {
        checkMethod(Method.GET)
        requestMediaType = MediaType.APPLICATION_XML_TYPE
        Invocation.Builder builder = getWebTarget().request(requestMediaType) //TODO possibility to set another XML type
        InputStream inputStream = builder.get().readEntity(InputStream)
        try {
            return new XmlSlurper().parse(inputStream)
        } finally {
            inputStream.close()
        }
    }

    def Object getJson() {
        checkMethod(Method.GET)
        requestMediaType = MediaType.APPLICATION_JSON_TYPE
        Invocation.Builder builder = getWebTarget().request(requestMediaType)
        Reader reader = builder.get().readEntity(Reader)
        try {
            return new JsonSlurper().parse(reader)
        } finally {
            reader.close()
        }
    }

/*
    def <T> T rightShift(Class<T> type) {
        requestMediaType = MediaType.APPLICATION_XML_TYPE
        Invocation.Builder builder = getWebTarget().request(requestMediaType)
        checkMethod(Method.GET)
        return builder.get(type)
    }
*/

    def <T> T asType(Class<T> type) {
//        checkMethod(Method.GET)
//        Invocation.Builder builder = getWebTarget().request(requestMediaType)

        Invocation.Builder builder = requestMediaType != null ? getWebTarget().request(requestMediaType) : getWebTarget().request()

        switch (method) {
            case Method.GET:
                return builder.get(type)
            case Method.POST:
                return builder.post(entity, type)
        }
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
