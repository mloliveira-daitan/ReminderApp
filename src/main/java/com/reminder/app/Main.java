package com.reminder.app;

import com.bandwidth.sdk.*;
import com.bandwidth.sdk.model.Account;
import com.bandwidth.sdk.model.Call;
import com.bandwidth.sdk.model.Gather;
import com.bandwidth.sdk.model.Gender;
import com.bandwidth.sdk.model.events.EventBase;
import org.apache.http.HttpResponse;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main
{

    /*private static String userId = "u-jbg4qvzfcs6hpnq2mah7ona";
    private static String apiToken =  "t-4iozqelr2fdj7amxjgz2o3y" ;
    private static String apiSecret = "grkw7yze7dy4w7lpt2jdifnxhrhcmpjd7ft2xca";
*/

    private static String userId = "u-m6vtffypexjt3k64ecumycy";
    private static String apiToken =  "t-tlq3f7nk2w5fjxre7zdmirq" ;
    private static String apiSecret = "buh23662yqwejlzohuqzpkouao22wirmhlrmgnq";



   // BandwidthClient.setInstance(userId, apiToken, apiSecret);


    private static String toNumber = "+15302987471";// your phone number here
    private static String fromNumber = "+18595682277";// this is a number that is allocated on the AppPlatform. You can do this
// via the dev console or with the SDK (see AllocateNumberExample)


    public static void main( String[] args ) throws Exception {

        //BandwidthClient.getInstance().setEndpointandVersion("https://api.dev.catapult.inetwork.com", "v1");
        BandwidthClient.getInstance().setCredentials(userId, apiToken, apiSecret);

        String webappDirLocation = "src/main/webapp/";

        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }

        Server server = new Server(Integer.valueOf(webPort));
        WebAppContext root = new WebAppContext();

        root.setContextPath("/");
        root.setDescriptor(webappDirLocation + "/WEB-INF/web.xml");
        root.setResourceBase(webappDirLocation);

        // Parent loader priority is a class loader setting that Jetty accepts.
        // By default Jetty will behave like most web containers in that it will
        // allow your application to replace non-server libraries that are part
        // of the
        // container. Setting parent loader priority to true changes this
        // behavior.
        // Read more here:
        // http://wiki.eclipse.org/Jetty/Reference/Jetty_Classloading
        root.setParentLoaderPriority(true);

        server.setHandler(root);

        server.start();
        server.join();

    }

    protected static void waitForCallState(Call call) throws Exception {

        int tries = 8;
        while (!(call.getState() != null && call.getState().equals("active")) && tries > 0) {
            tries--;
            System.out.println(call.getState());
            Thread.sleep(1250);
        }
        System.out.println(call.getState());
        if (!(call.getState() != null && call.getState().equals("active"))) {
            System.out.println("Call didn't change to active.");
        }
    }




}
