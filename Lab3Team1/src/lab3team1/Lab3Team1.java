package lab3team1;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/* 
Project:
Purpose Details:
Course:
Author:
Date Developed:
Last Date Changed:
Revision:
 */
public class Lab3Team1 {

    public static void main(String[] args) {

        try {
            URL redditURL = new URL("https://www.reddit.com/r/popular");
            System.out.println("Get the Port for Reddit: " + redditURL.getPort());
            System.out.println("Reddit Host IP address:  " + redditURL.getHost());
            System.out.println("Get the user info for Reddit:  " + redditURL.getUserInfo());
            System.out.println("Get Reddit Content: " + redditURL.getRef());
            System.out.println("Get Reddit Path: " + redditURL.getPath());
            System.out.println("Get Reddit Authority: " + redditURL.getAuthority());

        } catch (MalformedURLException ex) {
            Logger.getLogger(Lab3Team1.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            InetAddress redditInet = InetAddress.getByName("www.reddit.com");
            System.out.println("  : " + redditInet.getCanonicalHostName());
            System.out.println("  : " + redditInet.getHostName());
            System.out.println("  : " + redditInet.isReachable(10000));
            System.out.println("  : " + redditInet.getHostAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
