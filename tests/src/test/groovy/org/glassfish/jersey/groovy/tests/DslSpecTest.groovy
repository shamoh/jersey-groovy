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

    def "[XML/pure] GET hello Jersey"() {
        expect:
        def response = GET[RESOURCE_URL/"Jersey"].xml
        response instanceof Message == false
        response.greeting.text() == "Hello Jersey!"
    }

    def "[XML/object] GET hello Jersey"() {
        expect:
        def response = (GET[RESOURCE_URL/"Jersey"] & MediaType.APPLICATION_XML_TYPE) as Message
        response instanceof Message
        response.greeting == "Hello Jersey!"
    }

    def "[JSON/pure] GET hello Jersey"() {
        expect:
        def response = GET[RESOURCE_URL/"Jersey"].json
        response instanceof Message == false
        response.greeting == "Hello Jersey!"
    }

    def "[JSON/object] GET hello Jersey"() {
        expect:
        def response = (GET[RESOURCE_URL/"Jersey"] & MediaType.APPLICATION_JSON_TYPE) as Message
        response instanceof Message
        response.greeting == "Hello Jersey!"
    }

    def "[XML/pure] POST hello Jersey"() {
        expect:
        WebTarget target = ClientBuilder.newClient().target(RESOURCE_URL)
        def Entity entity = Entity.entity(new Message(greeting: "Jersey", timestamp: new Date()), MediaType.APPLICATION_XML_TYPE)
        def response = target.request().post(entity, String)
        response == "Hello Jersey!"
    }

    def "[XML/object] POST hello Jersey"() {
        expect:
        WebTarget target = ClientBuilder.newClient().target(RESOURCE_URL)
        def Entity entity = Entity.entity(new Message(greeting: "Jersey", timestamp: new Date()), MediaType.APPLICATION_XML_TYPE)
        def response = target.request().post(entity, String)
        response == "Hello Jersey!"
    }

    def "[JSON/pure] POST hello Jersey"() {
        expect:
        WebTarget target = ClientBuilder.newClient().target(RESOURCE_URL)
        def Entity entity = Entity.entity(new Message(greeting: "Jersey", timestamp: new Date()), MediaType.APPLICATION_JSON_TYPE)
        def response = target.request().post(entity, String)
        response == "Hello Jersey!"
    }

    def "[JSON/object] POST hello Jersey"() {
        expect:
        WebTarget target = ClientBuilder.newClient().target(RESOURCE_URL)
        def Entity entity = Entity.entity(new Message(greeting: "Jersey", timestamp: new Date()), MediaType.APPLICATION_JSON_TYPE)
        def response = target.request().post(entity, String)
        response == "Hello Jersey!"
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
