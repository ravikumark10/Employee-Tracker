package com.ravikumar.employeetracking;

import com.ravikumar.employeetracking.DatabaseConnection;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res){
        try {
            boolean login_check,adminlogin_check;
            String mail = req.getParameter("Mail");
            String password = req.getParameter("Password");
            if (mail.equals("ravikumar") && password.equals("12345")) {
                adminlogin_check=true;
                HttpSession hs=req.getSession();
                hs.setAttribute("adminlogin_check",adminlogin_check);
                res.sendRedirect("adminhome.jsp");
            }
            else {
                Connection con = DatabaseConnection.getDatabase();
                PreparedStatement st = con.prepareStatement("SELECT * FROM employeedetails WHERE Mail=? AND Password=?");
                st.setString(1, req.getParameter("Mail"));
                st.setString(2, encryptPassword(req.getParameter("Password")));
                ResultSet result = st.executeQuery();


                if (result.next()) {
                    login_check=true;
                    HttpSession hs=req.getSession();
                    hs.setAttribute("login_check",login_check);
                    hs.setAttribute("EmployeeId",result.getInt("Id"));
                    res.sendRedirect("checkout");
                } else {
                    req.setAttribute("message","Incorrect Username or Password");
                    RequestDispatcher rd=req.getRequestDispatcher("login.jsp");
                    rd.forward(req,res);
                }
                st.close();
                con.close();
            }
            }catch(Exception e){
                System.out.println(e);
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
}
