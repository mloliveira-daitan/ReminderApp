package com.reminder.app;

import com.bandwidth.sdk.model.events.Event;
import com.bandwidth.sdk.model.events.EventBase;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.logging.Logger;


public class Servlet extends HttpServlet{
/*
    protected void service (HttpServletRequest request,
                            HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<body>");
        out.println("SERVLET");
        out.println("</body>");
        out.println("</html>");
    }*/
public static final Logger logger = Logger
        .getLogger(Main.class.getName());

    private static String userId = "u-jbg4qvzfcs6hpnq2mah7ona";
    private static String apiToken =  "t-4iozqelr2fdj7amxjgz2o3y" ;
    private static String apiSecret = "grkw7yze7dy4w7lpt2jdifnxhrhcmpjd7ft2xca";

// BandwidthClient.setInstance(userId, apiToken, apiSecret);


    private static String toNumber = "+15302987471";// your phone number here
    private static String fromNumber = "+18604195505";// this is a number that is allocated on the AppPlatform. You can do this
// via the dev console or with the SDK (see AllocateNumberExample)

    //BandwidthClient.getInstance().setCredentials(userId, apiToken, apiSecret);
    //Call call = Call.create(toNumber, fromNumber);
    private String message;
    private String callbackUrl;

    public void init() throws ServletException
    {
        // Do required initialization
        message = "Hello World";
    }


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        logger.finer("doPost(ENTRY)");

        //displayHeaders(req);
        //displayParameters(req);

        try {
            String body = getBody(req);
            logger.finest(body);
            Event event = (Event) EventBase.createEventFromString(body);

            String callLeg = req.getParameter("callLeg");
            String requestUrl = req.getRequestURL().toString();
            String requestUri = req.getRequestURI();
            String contextPath = req.getContextPath();
            callbackUrl = requestUrl + "?callLeg=outgoing"; // used for outgoing
            String baseUrl = requestUrl.substring(0, requestUrl.length()
                    - requestUri.length()) + contextPath;

            //logger.finer("requestUrl:" + requestUrl);
            //logger.finer("requestUri:" + requestUri);
            //logger.finer("contextPath:" + contextPath);
            //logger.finer("callbackUrl:" + callbackUrl);
            //logger.finer("baseUrl:" + baseUrl);

            String fromNumber = req.getParameter("fromNumber");
            event.setProperty("fromNumber", fromNumber);
            event.setProperty("callLeg", callLeg);
            event.setProperty("baseUrl", baseUrl);

            logger.fine("adding event to queue");
            //queue.add(event);
            PrintWriter out = resp.getWriter();
            out.println("<h1>" + message + "</h1>");
            out.println("<h1>" + baseUrl + "</h1>");
            out.println("<h1>" + body + "</h1>");


            resp.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            logger.severe(e.toString());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();

        }

        logger.finer("doPost(EXIT)");
    }

    /**
     *
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.finer("doGet(ENTRY)");

        String callLeg = req.getParameter("callLeg");
        String requestUrl = req.getRequestURL().toString();
        String requestUri = req.getRequestURI();
        String contextPath = req.getContextPath();

        logger.finer("requestUrl:" + requestUrl);
        logger.finer("requestUri:" + requestUri);
        logger.finer("contextPath:" + contextPath);

        String baseUrl = requestUrl.substring(0, requestUrl.length()
                - requestUri.length());


        callbackUrl = requestUrl + "?callLeg=outgoing"; // used for outgoing
        // calls
        logger.finer("callbackUrl:" + callbackUrl);

        logger.finer("baseUrl:" + baseUrl);

        String body = getBody(req);
        PrintWriter out = resp.getWriter();
        out.println("<h1>" + message + "</h1>");
        out.println("<h1>" + baseUrl + "</h1>");
        out.println("<h1>" + body + "</h1>");


        logger.finer("doGet(EXIT)");
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

                // System.out.println(read);
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
