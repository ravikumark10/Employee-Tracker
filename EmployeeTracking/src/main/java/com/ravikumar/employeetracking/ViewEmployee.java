package com.ravikumar.employeetracking;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
@WebServlet("/view")
public class ViewEmployee extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            Connection con= DatabaseConnection.getDatabase();
            PreparedStatement st=con.prepareStatement("SELECT * FROM employeedetails");
            ResultSet result=st.executeQuery();
            req.setAttribute("result",result);
            RequestDispatcher rd = req.getRequestDispatcher("viewEmployee.jsp");
            rd.forward(req, res);
        }catch (Exception e){
            System.out.println(e);
            res.sendRedirect("viewEmployee.jsp");
        }
    }
}
