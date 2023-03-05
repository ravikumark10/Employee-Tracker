package com.ravikumar.employeetracking;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/add-department")
public class AddDepartment extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            Connection con = DatabaseConnection.getDatabase();

            //Insert department details into department table
            PreparedStatement st = con.prepareStatement("INSERT INTO department VALUES (?,?)");
            st.setInt(1, getCount(con));
            st.setString(2, req.getParameter("department"));
            int result = st.executeUpdate();
            String value = "";
            if (result > 0) {
                value = "Department added successfully";
            } else {
                value = "Cannot able to add department ";
            }
            req.setAttribute("alert", value);
            RequestDispatcher rd = req.getRequestDispatcher("addDepartment.jsp");
            rd.forward(req, res);
            st.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
            req.setAttribute("alert", e.getMessage());
            RequestDispatcher rd = req.getRequestDispatcher("addDepartment.jsp");
            rd.forward(req, res);
        }
    }

    //get existing count of departments
    public static int getCount(Connection con) throws SQLException {
        PreparedStatement st=con.prepareStatement("SELECT * FROM department ORDER BY Id DESC LIMIT 1");
        ResultSet result=st.executeQuery();
        int value = 0;
        if(result.next()){
            value= result.getInt("Id");
        }
        return value+1;
    }
}
