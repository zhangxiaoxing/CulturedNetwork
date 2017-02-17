/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iodegree.lib;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author Libra
 */
public class URIHandle {

    public static void open(URI uri) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(uri);
            } catch (IOException e) { /*
                 * TODO: error handling
                 */ }
        } else { /*
             * TODO: error handling
             */ }
    }

    public static void mailme() {
        try {
            final URI uri = new URI("mailto:zhangxiaoxing@ion.ac.cn");
            open(uri);
        } catch (URISyntaxException e) {
            System.out.println("URI error!");
        }
    }
}
