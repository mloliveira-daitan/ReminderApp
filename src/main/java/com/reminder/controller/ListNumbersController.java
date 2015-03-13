package com.reminder.controller;

import com.bandwidth.sdk.BandwidthClient;
import com.bandwidth.sdk.BandwidthConstants;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListNumbersController extends HttpServlet {

    /**
     *
     *
     *
     *
     */
    private String requestUrl = BandwidthConstants.BANDWIDTH_API_ENDPOINT+BandwidthConstants.API_VERSION;
/*
    private static String userId = "u-jbg4qvzfcs6hpnq2mah7ona";
    private static String apiToken =  "t-4iozqelr2fdj7amxjgz2o3y" ;
    private static String apiSecret = "grkw7yze7dy4w7lpt2jdifnxhrhcmpjd7ft2xca";
*/
    private static String userId = "u-m6vtffypexjt3k64ecumycy";
    private static String apiToken =  "t-tlq3f7nk2w5fjxre7zdmirq" ;
    private static String apiSecret = "buh23662yqwejlzohuqzpkouao22wirmhlrmgnq";


    String strNumbers = "";


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //BandwidthClient.getInstance().setEndpointandVersion("https://api.dev.catapult.inetwork.com", "v1");
        //System.out.println(strNumbers);
        NumbersBean numbersBean = new NumbersBean();
        List<String> numberList = new ArrayList();
        numbersBean.setNumbers(numberList);

        BandwidthClient.getInstance().setCredentials(userId, apiToken, apiSecret);

        try {
            ResourceList<PhoneNumber> userNumbers = PhoneNumber.list();

            for (PhoneNumber number : userNumbers){
                //numbersBean.add(number.getNumber());
                numberList.add(number.getNumber());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("numbersBean", numbersBean);

        RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
        rd.forward(request, response);
    }
}
