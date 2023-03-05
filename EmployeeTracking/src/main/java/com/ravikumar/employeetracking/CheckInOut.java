package com.ravikumar.employeetracking;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;

@WebServlet({"/checkin","/checkout"})
public class CheckInOut extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            String path=req.getServletPath();
            System.out.println(path);

            Connection con=DatabaseConnection.getDatabase();
            HttpSession session=req.getSession();

            LocalDate date = LocalDate.now();
            LocalDateTime dateTime = LocalDateTime.now();
            LocalTime currentTime = dateTime.toLocalTime();

            //Check-in functions
            if(path.equals("/checkin")){
                ResultSet result1= (ResultSet) checkLogin(con,session,date);
                if(result1.next()){
                    req.setAttribute("check", "Check Out");
                    req.setAttribute("in",getTime(result1.getTime("Start_time").toLocalTime()));
                    req.setAttribute("out", "00:00:00");
                    RequestDispatcher rd = req.getRequestDispatcher("home.jsp");
                    rd.forward(req, res);
                }else {
                    PreparedStatement st = con.prepareStatement("INSERT INTO attendance VALUES (?,?,?,?,?)");
                    st.setInt(1, getCount(con)+1);
                    st.setInt(2, (Integer) session.getAttribute("EmployeeId"));
                    st.setDate(3, Date.valueOf(date));
                    st.setTime(4, Time.valueOf(currentTime));
                    st.setString(5, String.valueOf(currentTime));
                    int result = st.executeUpdate();

                    //update days values in payable table
                    PreparedStatement st1=null;
                    if(getDate(date).equals("01")){
                        st1=con.prepareStatement("UPDATE payable_days SET Days=1 WHERE Employee_Id=?");
                        st1.setInt(1,(Integer) session.getAttribute("EmployeeId"));
                    }else{
                        PreparedStatement st2=con.prepareStatement("SELECT * FROM payable_days WHERE Employee_Id=?");
                        st2.setInt(1, (Integer) session.getAttribute("EmployeeId"));
                        ResultSet employee_days=st2.executeQuery();
                        if(employee_days.next()) {
                            st1 = con.prepareStatement("UPDATE payable_days SET Days=? WHERE Employee_Id=?");
                            st1.setInt(1, employee_days.getInt("Days")+1);
                            st1.setInt(2, (Integer) session.getAttribute("EmployeeId"));
                        }
                    }
                    int ans=st1.executeUpdate();

                    if (result > 0 && ans>0) {
                        System.out.println("Employee Payable days updated");

                        req.setAttribute("check", "Check Out");
                        req.setAttribute("in", getTime(currentTime));
                        req.setAttribute("out", "00:00:00");
                        RequestDispatcher rd = req.getRequestDispatcher("home.jsp");
                        rd.forward(req, res);
                    }

                }
            }

            //check-out functions
            else if(path.equals("/checkout")){
                System.out.println("Inside checkout");

                ResultSet result= (ResultSet) checkLogin(con,session,date);
                if(result.next()) {
                    System.out.println("updated");
                    PreparedStatement st1 = con.prepareStatement("UPDATE attendance SET End_time=? WHERE Employee_Id=? AND Date_value=? ");
                    st1.setTime(1, Time.valueOf(currentTime));
                    st1.setInt(2, (Integer) session.getAttribute("EmployeeId"));
                    st1.setDate(3,Date.valueOf(date));
                    st1.executeUpdate();
                    req.setAttribute("check","Check In");
                    req.setAttribute("in",getTime(result.getTime("Start_time").toLocalTime()));
                    req.setAttribute("out",getTime(currentTime));
                    RequestDispatcher rd=req.getRequestDispatcher("home.jsp");
                    rd.forward(req,res);
                }
                else{
                    req.setAttribute("check","Check In");
                    req.setAttribute("in","00:00:00");
                    req.setAttribute("out","00:00:00");
                    RequestDispatcher rd=req.getRequestDispatcher("home.jsp");
                    rd.forward(req,res);
                }
            }
        } catch (Exception e) {
            req.setAttribute("check","Check In");
            req.setAttribute("in","00:00:00");
            req.setAttribute("out","00:00:00");
            RequestDispatcher rd=req.getRequestDispatcher("home.jsp");
            rd.forward(req,res);
            System.out.println(e);
        }
    }

    public static StringBuilder getTime(LocalTime currentTime){
        String[] arr=String.valueOf(currentTime).split(":");
        int seconds=Double.valueOf(arr[2]).intValue();
        StringBuilder sec=new StringBuilder();
        StringBuilder hr=new StringBuilder();
        if(seconds<=9){
            sec.append("0").append(seconds);
        }else{
            sec.append(seconds);
        }
        StringBuilder time=new StringBuilder();
        int hour=Integer.parseInt(arr[0]);
        if(hour>=12){
            if(hour>12) {
                hour = hour - 12;
            }
            hr.append(hour);
            if(hour<=9) {
                time.append("0").append(hr).append(":").append(arr[1]).append(":").append(sec).append(" PM");
            }
            else {
                time.append(hr).append(":").append(arr[1]).append(":").append(sec).append(" PM");
            }
        }else{
            if(hour<=9) {
                time.append("0").append(hour).append(":").append(arr[1]).append(":").append(sec).append(" AM");
            }
            else {
                time.append(hour).append(":").append(arr[1]).append(":").append(sec).append(" AM");
            }
        }
        return time;
    }

    //check employee already login or not
    public static Object checkLogin(Connection con,HttpSession session,LocalDate date) throws SQLException {
        System.out.println(session.getAttribute("EmployeeId"));
        PreparedStatement st=con.prepareStatement("SELECT * FROM attendance WHERE Employee_Id=? AND Date_value=?");
        st.setInt(1, (Integer) session.getAttribute("EmployeeId"));
        st.setDate(2, Date.valueOf(date));
        ResultSet result=st.executeQuery();
        return result;
    }

    //get date only
    public static String getDate(LocalDate date){
        String[] d=date.toString().split("-");
        return d[2];
    }

    //get count of attendance records
    public static int getCount(Connection con) throws SQLException {
        PreparedStatement st=con.prepareStatement("SELECT COUNT(*) AS record_count FROM attendance");
        ResultSet result=st.executeQuery();
        int value = 0;
        if(result.next()){
            value= result.getInt("record_count");
        }
        return value;
    }
}
