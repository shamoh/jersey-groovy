package org.glassfish.jersey.groovy.tests

import org.glassfish.grizzly.http.server.HttpServer
import org.glassfish.jersey.groovy.tests.App
import org.glassfish.jersey.groovy.tests.HelloWorldResource
import org.glassfish.jersey.groovy.tests.Message
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification

import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.Entity
import javax.ws.rs.client.WebTarget
import javax.ws.rs.core.MediaType

/**
 * @todo form data
 */
class CommonSpecTest extends Specification {

    private static final String RESOURCE_URL = App.BASE_URL + App.RESOURCE_PATH;

    def "[TEXT] GET hello world"() {
        expect:
        WebTarget target = ClientBuilder.newClient().target(RESOURCE_URL)
        def response = target.request(MediaType.TEXT_PLAIN).get(String.class)
        "Hello World!" == response
    }

    def "[TEXT] GET hello Jersey"() {
        expect:
        WebTarget target = ClientBuilder.newClient().target(RESOURCE_URL)
        def response = target.path("Jersey").request(MediaType.TEXT_PLAIN).get(String.class)
        "Hello Jersey!" == response
    }

    def "[XML] GET hello Jersey"() {
        expect:
        WebTarget target = ClientBuilder.newClient().target(RESOURCE_URL)
        def response = target.path("Jersey").request(MediaType.APPLICATION_XML).get(Message.class)
        "Hello Jersey!" == response.greeting
    }

    def "[JSON] GET hello Jersey"() {
        expect:
        WebTarget target = ClientBuilder.newClient().target(RESOURCE_URL)
        def response = target.path("Jersey").request(MediaType.APPLICATION_JSON).get(Message.class)
        "Hello Jersey!" == response.greeting
    }

    def "[XML] POST hello Jersey"() {
        expect:
        WebTarget target = ClientBuilder.newClient().target(RESOURCE_URL)
        def Entity entity = Entity.entity(new Message(greeting: "Jersey", timestamp: new Date()), MediaType.APPLICATION_XML_TYPE)
        def response = target.request().post(entity, String.class)
        "Hello Jersey!" == response
    }

    def "[JSON] POST hello Jersey"() {
        expect:
        WebTarget target = ClientBuilder.newClient().target(RESOURCE_URL)
        def Entity entity = Entity.entity(new Message(greeting: "Jersey", timestamp: new Date()), MediaType.APPLICATION_JSON_TYPE)
        def response = target.request().post(entity, String.class)
        "Hello Jersey!" == response
    }

    @Ignore
    def "GET"() {
        expect:
        WebTarget target = ClientBuilder.newClient().target(RESOURCE_URL)
        def response = target.path("Jersey").request(MediaType.APPLICATION_XML_TYPE).get(Message.class)
        "Hello Jersey!" == response

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
