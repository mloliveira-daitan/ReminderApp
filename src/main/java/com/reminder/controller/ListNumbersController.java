package com.reminder.controller;

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

public class ListNumbersController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        NumbersBean numbersBean = new NumbersBean();
        List<String> numberList = new ArrayList();
        numbersBean.setNumbers(numberList);

        try {
            ResourceList<PhoneNumber> userNumbers = PhoneNumber.list();

            for (PhoneNumber number : userNumbers){
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
