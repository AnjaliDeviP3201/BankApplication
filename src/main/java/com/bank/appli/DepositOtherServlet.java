package com.bank.appli;


import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class DepositOtherServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int targetAccount = Integer.parseInt(request.getParameter("targetAccount"));
        int amount = Integer.parseInt(request.getParameter("amount"));

        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement check = con.prepareStatement("SELECT * FROM atm WHERE AccountNum = ?");
            check.setInt(1, targetAccount);
            ResultSet rs = check.executeQuery();
            if (rs.next()) {
                PreparedStatement ps = con.prepareStatement("UPDATE atm SET Ammount = Ammount + ? WHERE AccountNum = ?");
                ps.setInt(1, amount);
                ps.setInt(2, targetAccount);
                ps.executeUpdate();
                response.sendRedirect("dashboard.html");
            } else {
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<h2>Enter valid account number</h2>");
                out.println("<a href='depositOther.html'>Go Back</a>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
}
