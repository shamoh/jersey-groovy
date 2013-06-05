package org.glassfish.jersey.groovy.tests

import org.glassfish.grizzly.http.server.HttpServer
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification

import javax.ws.rs.GET
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.Entity
import javax.ws.rs.client.WebTarget
import javax.ws.rs.core.MediaType

/**
 * @see CommonSpecTest
 */
class DslSpecTest extends Specification {

    private static final String RESOURCE_URL = App.BASE_URL + App.RESOURCE_PATH;

    def "[TEXT] GET hello world"() {
        expect:
        def response = GET[RESOURCE_URL].text
        response == "Hello World!"
    }

    def "[TEXT] GET hello Jersey"() {
        expect:
        def response = GET[RESOURCE_URL/"Jersey"].text
        response == "Hello Jersey!"
    }

    def "[XML] GET hello Jersey"() {
        expect:
        def response = GET[RESOURCE_URL/"Jersey"].xml
        response instanceof Message == false
        response.greeting.text() == "Hello Jersey!"
    }

    def "[XML/Object] GET hello Jersey"() {
        expect:
        def response = GET[RESOURCE_URL/"Jersey"] as Message// via MediaType.APPLICATION_XML
        response instanceof Message
        response.greeting == "Hello Jersey!"
//        def response = target.path("Jersey").request(MediaType.APPLICATION_XML).get(Message)
    }

    def "[JSON] GET hello Jersey"() {
        expect:
        WebTarget target = ClientBuilder.newClient().target(RESOURCE_URL)
        def response = target.path("Jersey").request(MediaType.APPLICATION_JSON).get(Message)
        response instanceof Message
        response.greeting == "Hello Jersey!"
    }

    def "[XML] POST hello Jersey"() {
        expect:
        WebTarget target = ClientBuilder.newClient().target(RESOURCE_URL)
        def Entity entity = Entity.entity(new Message(greeting: "Jersey", timestamp: new Date()), MediaType.APPLICATION_XML_TYPE)
        def response = target.request().post(entity, String)
        response == "Hello Jersey!"
    }

    def "[JSON] POST hello Jersey"() {
        expect:
        WebTarget target = ClientBuilder.newClient().target(RESOURCE_URL)
        def Entity entity = Entity.entity(new Message(greeting: "Jersey", timestamp: new Date()), MediaType.APPLICATION_JSON_TYPE)
        def response = target.request().post(entity, String)
        response == "Hello Jersey!"
    }

    @Ignore
    def "GET"() {
        expect:
        WebTarget target = ClientBuilder.newClient().target(RESOURCE_URL)
        def response = target.path("Jersey").request(MediaType.APPLICATION_XML_TYPE).get(Message)
        response == "Hello Jersey!"

//        def response = GET.text "http://localhost:8080/base/helloworld"

//        def response = GET.response "http://localhost:8080/base"
//        response instanceof Response
    }

    //
    // lifecycle
    //

    @Shared
    HttpServer server;

    def setupSpec() {
        this.server = App.startServer()
    }

    def cleanupSpec() {
        this.server.stop()
    }

}
