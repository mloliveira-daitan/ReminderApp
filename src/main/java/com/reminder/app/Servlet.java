package com.reminder.app;
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


    }

    



    public void destroy()
    {
        // do nothing.
    }

}
