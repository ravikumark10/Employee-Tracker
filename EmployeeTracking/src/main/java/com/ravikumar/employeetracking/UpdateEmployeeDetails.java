package com.ravikumar.employeetracking;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
@WebServlet("/update-employee-details")
public class UpdateEmployeeDetails extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try{
            Connection con=DatabaseConnection.getDatabase();
            PreparedStatement st=con.prepareStatement("UPDATE employeedetails SET Id=?,Name=?,Mail=?,Designation_Id=?,Team_Id=?,Reporting_To=? WHERE Id=?");
            st.setInt(1, Integer.parseInt(req.getParameter("id")));
            st.setString(2,req.getParameter("name"));
            st.setString(3,req.getParameter("mail"));

            PreparedStatement st1=con.prepareStatement("SELECT * FROM designation WHERE Name=?");
            st1.setString(1,req.getParameter("designation"));
            ResultSet designation=st1.executeQuery();

            System.out.println(designation.next());

            PreparedStatement st2=con.prepareStatement("SELECT * FROM department WHERE Name=?");
            st2.setString(1,req.getParameter("team"));
            ResultSet department=st2.executeQuery();
            System.out.println(department.next());

            st.setInt(4,designation.getInt("Id"));
            st.setInt(5,department.getInt("Id"));
            st.setInt(6, Integer.parseInt(req.getParameter("reporting")));
            st.setInt(7, Integer.parseInt(req.getParameter("id")));

            int update_result=st.executeUpdate();
            String value="";
            if(update_result>0){
                value="Employee details updated Successfully";
            }else{
                value="Cannot able to update employee details";
            }
            req.setAttribute("alert",value);
//            RequestDispatcher rd=req.getRequestDispatcher("viewEmployee.jsp");
//            rd.forward(req,res);
            res.sendRedirect("view");
        }catch (Exception e){
            System.out.println(e);
            res.sendRedirect("view");
        }
    }
}
