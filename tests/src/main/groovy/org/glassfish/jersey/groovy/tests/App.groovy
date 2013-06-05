/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * http://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package org.glassfish.jersey.groovy.tests

import org.glassfish.jersey.filter.LoggingFilter
import org.glassfish.jersey.server.ResourceConfig

import javax.ws.rs.core.Application;

import java.util.logging.Level
import java.util.logging.Logger;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

/**
 * Hello world application using only the standard JAX-RS API and lightweight HTTP server bundled in JDK.
 *
 * @author Martin Matula (martin.matula at oracle.com)
 */
public class App {

    public static final URI BASE_URI = URI.create("http://localhost:8080/base/");
    public static final String RESOURCE_PATH = "helloworld";

    public static HttpServer startServer() {
        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, create());
        return server;
    }

    private static ResourceConfig create() {
        return new ResourceConfig()
                .registerClasses(HelloWorldResource.class)
                .registerInstances(new LoggingFilter(Logger.getLogger(App.class.getName()), true));
    }

    public static void main(String[] args) {
        try {
            final HttpServer server = startServer()

            System.out.println(String.format("Application started.\n" +
                    "To test long-running asynchronous operation resource, try %s%s\n" +
                    "To test async chat resource, try %s%s\n" +
                    "Hit enter to stop it...", BASE_URI, RESOURCE_PATH, BASE_URI, "chat"));

            System.in.read();

            server.stop();
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
