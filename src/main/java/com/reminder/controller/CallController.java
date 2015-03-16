package com.reminder.controller;

import com.bandwidth.sdk.BandwidthClient;
import com.bandwidth.sdk.model.Call;
import com.bandwidth.sdk.model.PhoneNumber;
import com.bandwidth.sdk.model.ResourceList;
import com.reminder.bean.CallBean;
import com.reminder.bean.NumbersBean;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CallController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CallBean callBean = new CallBean();

        HttpSession session = request.getSession();
        String callId = (String) session.getAttribute("callid");

        try {
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
            /*callBean.setTranscriptionsEnabled(call.getTranscriptionsEnabled());
            callBean.setTranscriptions(call.getTranscriptions());
            callBean.setRecordingsEnabled(call.getRecordingsEnabled());*/
            callBean.setRecordings(call.getRecordings());
            callBean.setEvents(call.getEvents());
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("callBean", callBean);

        RequestDispatcher rd=request.getRequestDispatcher("call_id.jsp");
        rd.forward(request, response);
    }
}
