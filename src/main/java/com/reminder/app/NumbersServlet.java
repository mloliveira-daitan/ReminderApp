package com.reminder.app;

import com.bandwidth.sdk.BandwidthClient;
import com.bandwidth.sdk.model.PhoneNumber;
import com.bandwidth.sdk.model.ResourceList;
import com.reminder.bean.NumbersBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NumbersServlet extends HttpServlet {

    private static String userId = System.getenv("BANDWIDTH_USER_ID");// = "u-m6vtffypexjt3k64ecumycy";
    private static String apiToken = System.getenv("BANDWIDTH_API_TOKEN");// =  "t-tlq3f7nk2w5fjxre7zdmirq" ;
    private static String apiSecret = System.getenv("BANDWIDTH_API_SECRET");// = "buh23662yqwejlzohuqzpkouao22wirmhlrmgnq";
//
//    heroku config:set BANDWIDTH_USER_ID='u-m6vtffypexjt3k64ecumycy'
//    heroku config:set BANDWIDTH_API_TOKEN='t-tlq3f7nk2w5fjxre7zdmirq'
//    heroku config:set BANDWIDTH_API_SECRET='buh23662yqwejlzohuqzpkouao22wirmhlrmgnq'

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        NumbersBean numbersBean = new NumbersBean();
        List<String> numberList = new ArrayList();
        numbersBean.setNumbers(numberList);

        try {
            BandwidthClient.getInstance().setCredentials(userId, apiToken, apiSecret);
        } catch (Exception e){

            System.out.println("\n\nERROCLIENT" + e.toString());
            numbersBean.setNumbersError(e.toString());
        }

        try {
            ResourceList<PhoneNumber> userNumbers = PhoneNumber.list();

            for (PhoneNumber number : userNumbers){
                numberList.add(number.getNumber());
            }

        } catch (Exception e) {
            e.printStackTrace();
            numbersBean.setNumbersError(e.toString());

            System.err.println("\n\nERROR::: \n\n" + e.toString());
        }

        request.setAttribute("numbersBean", numbersBean);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/start.jsp");
        rd.forward(request, response);
    }
}
