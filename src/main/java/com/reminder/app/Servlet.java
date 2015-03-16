package com.reminder.app;

import com.bandwidth.sdk.AppPlatformException;
import com.bandwidth.sdk.BandwidthClient;
import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.model.Call;
import com.bandwidth.sdk.model.events.Event;
import com.bandwidth.sdk.model.events.EventBase;
import com.reminder.bean.CallBean;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


public class Servlet extends HttpServlet{
public static final Logger logger = Logger
        .getLogger(Main.class.getName());

   /* private static String userId = "u-jbg4qvzfcs6hpnq2mah7ona";
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

    //BandwidthClient.getInstance().setCredentials(userId, apiToken, apiSecret);
    //Call call = Call.create(toNumber, fromNumber);
    private String message;
    private String callbackUrl;

    public void init() throws ServletException
    {
        //BandwidthClient.getInstance().setEndpointandVersion("https://api.dev.catapult.inetwork.com", "v1");
        BandwidthClient.getInstance().setCredentials(userId, apiToken, apiSecret);
        // Do required initialization

        message = "Hello World";
    }


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        logger.finer("doPost(ENTRY)");

        try {
            String body = getBody(req);
            logger.finest(body);
            Event event = (Event) EventBase.createEventFromString(body);

            String callId = event.getProperty("callId");
            Call call = Call.get(callId);

            if ( event.getEventType().toString().equals("answer")){
                System.out.println("SENDING GATHER");

                sendGather(call);
            }
            else if(event.getEventType().toString().equals("gather")){
                System.out.println("RECEIVING GATHER");
                String inputDigit = event.getProperty("digits");

                if (inputDigit.equals("1")){
                    call.hangUp();

                }
                else if (inputDigit.equals("2")){
                    call.stopSentence();
                    call.speakSentence("Your appointment location is around the corner, at 3 PM");

                }
                else {
                    call.stopSentence();
                    sendGather(call);
                }

            }
            else if (event.getEventType().toString().equals("speak")){
                if (event.getProperty("state").toString().equals("PLAYBACK_STOP") ){
                    call.hangUp();
                }
            }

            CallBean callBean = new CallBean();

            callBean.setId(call.getId());
            callBean.setState(call.getState());
            callBean.setDirection(call.getDirection());
            callBean.setTo(call.getTo());
            callBean.setFrom(call.getFrom());
            callBean.setStartTime(call.getStartTime());
            callBean.setActiveTime(call.getActiveTime());
            callBean.setEndTime(call.getEndTime());
            callBean.setChargeableDuration(call.getChargeableDuration());
            callBean.setCallbackUrl(call.getCallbackUrl());


            req.setAttribute("callBean", callBean);

            RequestDispatcher rd=req.getRequestDispatcher("call_id.jsp");
            rd.forward(req, resp);

            String callLeg = req.getParameter("callLeg");
            String requestUrl = req.getRequestURL().toString();
            String requestUri = req.getRequestURI();
            String contextPath = req.getContextPath();
            callbackUrl = requestUrl + "?callLeg=outgoing"; // used for outgoing
            String baseUrl = requestUrl.substring(0, requestUrl.length()
                    - requestUri.length()) + contextPath;

            String fromNumber = req.getParameter("fromNumber");
            event.setProperty("fromNumber", fromNumber);
            event.setProperty("callLeg", callLeg);
            event.setProperty("baseUrl", baseUrl);

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

        String body = getBody(req);

        String answered = req.getParameter("eventType");

        System.out.println("@GET +" + body + "EVENTTYPE: " + answered + "\n");
        CallBean callBean = new CallBean();
        Event event = null;
        try {
            event = (Event) EventBase.createEventFromString(body);
            String callId = event.getProperty("callId");
            Call call = Call.get(callId);
            callBean.setId(call.getId());
            callBean.setState(call.getState());
            callBean.setDirection(call.getDirection());
            callBean.setTo(call.getTo());
            callBean.setFrom(call.getFrom());
            callBean.setStartTime(call.getStartTime());
            callBean.setActiveTime(call.getActiveTime());
            callBean.setEndTime(call.getEndTime());
            callBean.setChargeableDuration(call.getChargeableDuration());
            callBean.setCallbackUrl(call.getCallbackUrl());

            req.setAttribute("callBean", callBean);

            RequestDispatcher rd=req.getRequestDispatcher("call_id.jsp");
            rd.forward(req, resp);

        } catch (AppPlatformException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }



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


    private void sendGather(Call call) {
        Map<String, Object> gatherParams = new HashMap<String, Object>();
        gatherParams.put("maxDigits", "1");

        Map<String, Object> promptParams = new HashMap<String, Object>();
        String reminderSentence = "Hello! This is the appointment reminder app from Bandwidth." +
                " Your appointment is scheduled to Wednesday at 3 PM. . ." + "Please press 1 to end this call. " +
                "Press 2 to receive directions or press 3 to repeat this menu.";
        promptParams.put("sentence", reminderSentence);
        promptParams.put("voice", "kate");
        promptParams.put("gender", "female");
        promptParams.put("locale", "en_US");

        try {
            call.createGather(gatherParams, promptParams);
        }
        catch (Exception e){
            System.out.println("Exception: " + e.toString());
        }
    }
}
