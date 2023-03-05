package com.ravikumar.employeetracking;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpSession hs= req.getSession();
        hs.setAttribute("login_check",null);
        hs.setAttribute("adminlogin_check",null);
        res.sendRedirect("login.jsp");
    }
}
