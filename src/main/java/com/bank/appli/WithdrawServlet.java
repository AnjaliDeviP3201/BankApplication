package com.bank.appli;



import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class WithdrawServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int amount = Integer.parseInt(request.getParameter("amount"));
        HttpSession session = request.getSession(false);
        int accountNum = (int) session.getAttribute("accountNum");

        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement check = con.prepareStatement("SELECT Ammount FROM atm WHERE AccountNum = ?");
            check.setInt(1, accountNum);
            ResultSet rs = check.executeQuery();
            if (rs.next() && rs.getInt("Ammount") >= amount) {
                PreparedStatement ps = con.prepareStatement("UPDATE atm SET Ammount = Ammount - ? WHERE AccountNum = ?");
                ps.setInt(1, amount);
                ps.setInt(2, accountNum);
                ps.executeUpdate();
                response.sendRedirect("dashboard.html");
            } else {
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<h2>Insufficient balance</h2>");
                out.println("<a href='withdraw.html'>Go Back</a>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
}
