package org.glassfish.jersey.groovy.internal

/**
 * @author Libor Kramolis (libor.kramolis at oracle.com)
 */
class JerseyExtension {

    //
    // String
    //

    public static String div(String base, String path) {
        base = base.endsWith('/') ? base[0..-2] : base;
        path = path.startsWith('/') ? path[1..-1] : path;
        return "$base/$path"
    }

}
