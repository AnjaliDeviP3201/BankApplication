package com.bank.appli;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class BalanceServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Integer accountNum = (Integer) session.getAttribute("accountNum");

        if (accountNum == null) {
            response.sendRedirect("login.html");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT Ammount FROM atm WHERE AccountNum = ?");
            ps.setInt(1, accountNum);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int balance = rs.getInt("Ammount");

                // Read HTML template
                InputStream is = getServletContext().getResourceAsStream("/balance_template.html");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder htmlBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    htmlBuilder.append(line).append("\n");
                }
                reader.close();

                // Replace placeholder
                String finalHtml = htmlBuilder.toString().replace("{{balance}}", String.valueOf(balance));

                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println(finalHtml);

            } else {
                response.setContentType("text/html");
                response.getWriter().println("<h2>Account not found</h2>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html");
            response.getWriter().println("<h2>Error occurred while checking balance.</h2>");
        }
    }
}
