package org.glassfish.jersey.groovy.tests;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class Message {

    private String greeting;
    private Date timestamp;

    public Message() {
    }

    public Message(String greeting, Date timestamp) {
        this.greeting = greeting;
        this.timestamp = timestamp;
    }

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
