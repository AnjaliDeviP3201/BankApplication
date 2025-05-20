package com.bank.appli;



import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class DepositOwnServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int amount = Integer.parseInt(request.getParameter("amount"));
        HttpSession session = request.getSession(false);
        int accountNum = (int) session.getAttribute("accountNum");

        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE atm SET Ammount = Ammount + ? WHERE AccountNum = ?");
            ps.setInt(1, amount);
            ps.setInt(2, accountNum);
            ps.executeUpdate();
            response.sendRedirect("dashboard.html");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
}
