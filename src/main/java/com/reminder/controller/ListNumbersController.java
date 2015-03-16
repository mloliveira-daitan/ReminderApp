package com.reminder.controller;

import com.bandwidth.sdk.BandwidthClient;
import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.model.PhoneNumber;
import com.bandwidth.sdk.model.ResourceList;
import com.reminder.bean.NumbersBean;
import org.apache.http.util.EntityUtils;

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


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //BandwidthClient.getInstance().setEndpointandVersion("https://api.dev.catapult.inetwork.com", "v1");
        //System.out.println(strNumbers);
        NumbersBean numbersBean = new NumbersBean();
        List<String> numberList = new ArrayList();
        numbersBean.setNumbers(numberList);

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
