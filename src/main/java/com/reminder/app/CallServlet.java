package com.reminder.app;

import com.bandwidth.sdk.BandwidthClient;
import com.bandwidth.sdk.model.Call;
import com.bandwidth.sdk.model.events.Event;
import com.bandwidth.sdk.model.events.EventBase;
import com.google.gson.Gson;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


public class CallServlet extends HttpServlet{
public static final Logger logger = Logger
        .getLogger(Main.class.getName());
/*
    //TEST
    private static String userId = "u-jbg4qvzfcs6hpnq2mah7ona";
    private static String apiToken =  "t-4iozqelr2fdj7amxjgz2o3y" ;
    private static String apiSecret = "grkw7yze7dy4w7lpt2jdifnxhrhcmpjd7ft2xca";
*/

    //PROD
    private static String userId = "u-m6vtffypexjt3k64ecumycy";
    private static String apiToken =  "t-tlq3f7nk2w5fjxre7zdmirq" ;
    private static String apiSecret = "buh23662yqwejlzohuqzpkouao22wirmhlrmgnq";


// BandwidthClient.setInstance(userId, apiToken, apiSecret);

    private static String toNumber = "+15302987471";// your phone number here
    //private static String fromNumber = "+18604195505";// this is a number that is allocated on the AppPlatform. You can do this
    private static String fromNumber = "+18595682277";// this is a number that is allocated on the AppPlatform. You can do this

// via the
// dev console or with the SDK (see AllocateNumberExample)

    public void init() throws ServletException
    {
       // BandwidthClient.getInstance().setEndpointandVersion("https://api.dev.catapult.inetwork.com", "v1");
        BandwidthClient.getInstance().setCredentials(userId, apiToken, apiSecret);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        logger.finer("doPost(ENTRY)");

        try {
            String body = getBody(req);
            String parsedBody = body.replaceAll("%2B", "+");

            logger.finest(body);


            String delims = "[=&]+";
            String[] tokens = parsedBody.split(delims);

            for (int i = 0; i < tokens.length; i++){
                if (tokens[i].equals("toNumber")){
                    toNumber = tokens[i+1];
                }
                else if (tokens[i].equals("fromNumber")){
                    fromNumber = tokens[i+1];
                }
            }

            Call call = Call.create(toNumber, fromNumber, "https://sheltered-eyrie-4361.herokuapp.com/servlet", "testcall");
            HttpSession session = req.getSession();
            session.setAttribute("callid", call.getId());

            resp.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            logger.severe(e.toString());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();

        }


        logger.finer("doPost(EXIT)");
    }

    protected String getBody(HttpServletRequest req) {
        logger.finest("getBody(ENTRY)");

        StringBuilder sb = new StringBuilder();
        try {
            InputStream in = req.getInputStream();

            InputStreamReader is = new InputStreamReader(in);

            BufferedReader br = new BufferedReader(is);
            String read = br.readLine();

            while (read != null) {
                sb.append(read);
                read = br.readLine();
            }
        } catch (Exception e) {
            logger.severe(e.toString());
            e.printStackTrace();
        }

        logger.finest("getBody(EXIT)");
        return sb.toString();
    }



    public void destroy()
    {
        // do nothing.
    }

}
