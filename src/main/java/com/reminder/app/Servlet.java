package com.reminder.app;
import com.bandwidth.sdk.*;
import com.bandwidth.sdk.model.Call;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;



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

    public void init() throws ServletException
    {
        // Do required initialization
        message = "Hello World";
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException
    {
        // Set response content type
        response.setContentType("text/html");

        // Actual logic goes here.
        PrintWriter out = response.getWriter();
        out.println("<h1>" + message + "</h1>");
        out.println("<h1>" + request.getRequestURI() + "</h1>");
        out.println("<h1>" + request.getRequestURL() + "</h1>");
        try {
            Call call = Call.create("", "");
        }
        catch (Exception e){
            System.out.println(e);
        }

    }





    public void destroy()
    {
        // do nothing.
    }

}
