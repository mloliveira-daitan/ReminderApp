package com.reminder.app;

import com.bandwidth.sdk.model.Call;
import com.bandwidth.sdk.model.events.Event;
import com.bandwidth.sdk.model.events.EventBase;

import javax.servlet.http.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


public class Servlet extends HttpServlet{
public static final Logger logger = Logger
        .getLogger(Main.class.getName());

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
