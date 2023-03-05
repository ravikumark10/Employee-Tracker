package com.ravikumar.employeetracking;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/add")
public class AddEmployee extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try{
            Connection con= DatabaseConnection.getDatabase();

            //Insert employee details into table
            PreparedStatement st=con.prepareStatement("INSERT INTO employeedetails VALUES (?,?,?,?,?,?,?)");
            st.setInt(1,getCount(con));
            st.setString(2,req.getParameter("name"));
            st.setString(3,req.getParameter("mail"));
            st.setString(4,encryptPassword(req.getParameter("password")));

            //get designation value of employee using designation_id
            PreparedStatement st1=con.prepareStatement(("SELECT * FROM designation WHERE Name=?"));
            st1.setString(1,req.getParameter("designation"));
            ResultSet result1=st1.executeQuery();
            result1.next();
            st.setInt(5, result1.getInt("Id"));

            //get team value of employee using team_id
            PreparedStatement st2=con.prepareStatement(("SELECT * FROM department WHERE Name=?"));
            st2.setString(1,req.getParameter("team"));
            ResultSet result2=st2.executeQuery();
            result2.next();
            st.setInt(6, result2.getInt("Id"));
            st.setInt(7, Integer.parseInt(req.getParameter("reporting")));

            //Insert employee_id into payable table
            PreparedStatement st3=con.prepareStatement("INSERT INTO payable_days VALUES(?,?)");
            st3.setInt(1, getCount(con));
            st3.setInt(2,0);
            int result3=st3.executeUpdate();

            int result=st.executeUpdate();
            String value="";
            if(result>0 && result3>0){
               value="Employee added successfully";
            }
            else{
                value="Cannot able to add employee ";
            }
            req.setAttribute("alert",value);
            RequestDispatcher rd=req.getRequestDispatcher("addEmployee.jsp");
            rd.forward(req,res);
            st.close();
            con.close();
        }catch (Exception e){
            System.out.println(e);
            req.setAttribute("alert",e.getMessage());
            RequestDispatcher rd=req.getRequestDispatcher("addEmployee.jsp");
            rd.forward(req,res);
        }
    }

    public static String encryptPassword(String password)throws NoSuchAlgorithmException {
        MessageDigest m=MessageDigest.getInstance("MD5");
        m.update(password.getBytes());
        byte[] arr=m.digest();
        StringBuilder str=new StringBuilder("");
        for(int i=0;i<arr.length;i++){
            str.append(Integer.toString((arr[i] & 0xff) + 0x100, 16).substring(1));
        }
        return str.toString();
    }

    //get count of existing employees
    public static int getCount(Connection con) throws SQLException {
        PreparedStatement st=con.prepareStatement("SELECT * FROM employeedetails ORDER BY Id DESC LIMIT 1");
        ResultSet result=st.executeQuery();
        int value = 0;
        if(result.next()){
            value= result.getInt("Id");
        }
        return value+1;
    }
}
