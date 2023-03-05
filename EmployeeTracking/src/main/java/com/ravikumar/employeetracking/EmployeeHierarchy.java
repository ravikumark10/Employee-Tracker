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

@WebServlet("/employee-hierarchy")
public class EmployeeHierarchy extends HttpServlet {
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            Connection con = DatabaseConnection.getDatabase();
            HttpSession session=req.getSession();
            int employee_id= (int) session.getAttribute("EmployeeId");
            PreparedStatement st=con.prepareStatement("SELECT * FROM employeedetails WHERE Id=?");
            st.setInt(1,employee_id);
            ResultSet result=st.executeQuery();

            StringBuilder report_list=new StringBuilder();
            if(result.next()){
                int report=result.getInt("Reporting_To");
                report_list.append(result.getString("Name"));
                int i=0;
                if(report!=0) {
                    while (i < 8) {
                        st = con.prepareStatement("SELECT * FROM employeedetails WHERE Id=?");
                        st.setInt(1, report);
                        result = st.executeQuery();
                        if (result.next()) {
                            report = result.getInt("Reporting_To");
                            if (report == 0) {
                                report_list.append(" ---> ");
                                report_list.append(result.getString("Name"));
                                break;
                            } else {
                                report_list.append(" ---> ");
                                report_list.append(result.getString("Name"));
                            }
                        }
                        i++;
                    }
                }
            }

            System.out.println(report_list);

            req.setAttribute("report",report_list);
            RequestDispatcher rd=req.getRequestDispatcher("employeeHierarchy.jsp");
            rd.forward(req,res);
        }catch (Exception e){
            System.out.println(e);
            res.sendRedirect("employeeHierarchy.jsp");
        }
    }
}
