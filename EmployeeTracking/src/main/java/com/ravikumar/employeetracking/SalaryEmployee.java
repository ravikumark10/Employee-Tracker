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

@WebServlet("/salary-employee")
public class SalaryEmployee extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            Connection con=DatabaseConnection.getDatabase();
            PreparedStatement st= con.prepareStatement("SELECT e.Id as ID, e.Name as Name,p.Days as Days,(d.salary/30)*p.Days as Salary" +
                    " FROM employeedetails as e INNER JOIN payable_days as p ON e.Id=p.Employee_Id " +
                    "INNER JOIN designation as d ON d.Id=e.Designation_Id");
            ResultSet result=st.executeQuery();
            System.out.println(result);
            req.setAttribute("result",result);
            RequestDispatcher rd=req.getRequestDispatcher("salaryEmployee.jsp");
            rd.forward(req,res);
        }catch (Exception e){
            res.sendRedirect("salaryEmployee.jsp");
            System.out.println(e);
        }
    }
}

