package com.reminder.controller;

import com.reminder.bean.NumbersBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ListNumbersController extends HttpServlet {

    /**
     *
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        NumbersBean numbersBean = new NumbersBean();
        numbersBean.setNumber("00000000");

        request.setAttribute("numbersBean", numbersBean);

        RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
        rd.forward(request, response);
    }
}
