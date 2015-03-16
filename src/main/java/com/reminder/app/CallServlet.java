package com.reminder.app;

import com.bandwidth.sdk.model.Call;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.logging.Logger;


public class CallServlet extends HttpServlet{
public static final Logger logger = Logger
        .getLogger(Main.class.getName());

    private static String toNumber;
    private static String fromNumber;


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
