package com.bank.appli;



import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	System.out.println("hello1");
        try (Connection con = DBConnection.getConnection()) {
        	int accountNum = Integer.parseInt(request.getParameter("accountNum"));
            int pinNum = Integer.parseInt(request.getParameter("pinNum"));
            PreparedStatement ps = con.prepareStatement("SELECT * FROM atm WHERE AccountNum = ?");
            ps.setInt(1, accountNum);
            ResultSet rs = ps.executeQuery();
            System.out.println("hello2");
            if (rs.next()) {
                int dbPin = rs.getInt("PinNum");
                if (dbPin == pinNum) {
                    HttpSession session = request.getSession();
                    session.setAttribute("accountNum", accountNum);
                    response.sendRedirect("dashboard.html");
                } else {
                    response.sendRedirect("invalidPassword.html");
                }
            } else {
                response.sendRedirect("invalidUsername.html");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
}
