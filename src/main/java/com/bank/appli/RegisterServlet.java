package com.bank.appli;


import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class RegisterServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        int accountNum = Integer.parseInt(request.getParameter("accountNum"));
        int phoneNum = Integer.parseInt(request.getParameter("phoneNum"));
        int amount = Integer.parseInt(request.getParameter("amount"));
        String address = request.getParameter("address");
        int pin = Integer.parseInt(request.getParameter("pin"));
        
    	System.out.println("helllo1");
        try  {
        	
        	String url="jdbc:mysql://localhost:3306/jdbcsql";
			String dbun="root";
			String dbpwd="jooly";
			System.out.println("helllo2");
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection con = DriverManager.getConnection(url,dbun,dbpwd);
			
            PreparedStatement ps = con.prepareStatement("INSERT INTO atm (Name, AccountNum, PhoneNum, Ammount, Address, PinNum) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, name);
            ps.setInt(2, accountNum);
            ps.setInt(3, phoneNum);
            ps.setInt(4, amount);
            ps.setString(5, address);
            ps.setInt(6, pin);
            
            int rows = ps.executeUpdate();
            if (rows > 0) {
                response.sendRedirect("login.html");
            } else {
                response.sendRedirect("error.html");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("helllo3");
            response.sendRedirect("error.html");
        }
    }
}
