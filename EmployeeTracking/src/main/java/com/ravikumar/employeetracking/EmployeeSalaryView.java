package com.ravikumar.employeetracking;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/employee-salary-view")
public class EmployeeSalaryView extends HttpServlet {
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            Connection con = DatabaseConnection.getDatabase();
            HttpSession session=req.getSession();
            PreparedStatement st=con.prepareStatement("SELECT p.Days AS DAYS,(d.salary/30)*DAYS AS Salary FROM employeedetails e,designation d,payable_days p WHERE e.Id=? AND d.Id=e.Designation_Id AND p.Employee_Id=?");
            st.setInt(1, (Integer) session.getAttribute("EmployeeId"));
            st.setInt(2, (Integer) session.getAttribute("EmployeeId"));
            ResultSet result=st.executeQuery();
            if(result.next()){
                req.setAttribute("salaryDetails",result);
                RequestDispatcher rd=req.getRequestDispatcher("employeeSalaryView.jsp");
                rd.forward(req,res);
            }
        }catch (Exception e){
            System.out.println(e);
            res.sendRedirect("employeeSalaryView.jsp");
        }
    }
}
