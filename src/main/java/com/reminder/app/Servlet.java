package com.reminder.app;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class Servlet extends HttpServlet{

    protected void service (HttpServletRequest request,
                            HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<body>");
        out.println("SERVLET");
        out.println("</body>");
        out.println("</html>");
    }

}
