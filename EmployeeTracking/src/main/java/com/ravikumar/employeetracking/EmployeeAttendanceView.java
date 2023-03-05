package com.ravikumar.employeetracking;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@WebServlet("/employee-attendance-view")
public class EmployeeAttendanceView extends HttpServlet {
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            HttpSession session=req.getSession();
            Connection con = DatabaseConnection.getDatabase();
            PreparedStatement st=con.prepareStatement("SELECT * FROM attendance WHERE (Date_value BETWEEN ? AND ?) AND Employee_Id=?");
            st.setString(1, String.valueOf(getWeekDate().get(0)));
            st.setString(2, String.valueOf(getWeekDate().get(6)));
            st.setInt(3, (Integer) session.getAttribute("EmployeeId"));
            ResultSet result=st.executeQuery();

            List<String> dateDay=getDateDay();
            List<String> attendance_report=getReport(result);
            req.setAttribute("dateDay",dateDay);
            req.setAttribute("attendance_report",attendance_report);
            RequestDispatcher rd=req.getRequestDispatcher("employeeAttendanceView.jsp");
            rd.forward(req,res);
        }catch (Exception e){
            System.out.println(e);
            res.sendRedirect("employeeAttendanceView.jsp");
        }
    }

    //get date first day of week
    public static List<LocalDate> getWeekDate(){

        Calendar cal = Calendar.getInstance();

        Calendar first = (Calendar) cal.clone();
        first.add(Calendar.DAY_OF_WEEK,first.getFirstDayOfWeek() - first.get(Calendar.DAY_OF_WEEK));

        Calendar last = (Calendar) first.clone();
        last.add(Calendar.DAY_OF_YEAR, 6);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(df.format(first.getTime()));
        LocalDate end = LocalDate.parse(df.format(last.getTime()));
        List<LocalDate> totalDates = new ArrayList<>();
        while (!start.isAfter(end)) {
            totalDates.add(start);
            start = start.plusDays(1);
        }
        return totalDates;
    }

    //get attendance with dates and status
    public static List<String> getReport(ResultSet result) throws SQLException {
        List<Date> result_date=new ArrayList<>();
        while(result.next()){
            result_date.add(result.getDate("Date_value"));
        }
        System.out.println(result_date);
        LocalDate date=LocalDate.now();
        List<LocalDate> Week_dates=getWeekDate();
        List<String> attendance_report=new ArrayList<>();

        attendance_report.add("Weekend");
        for(int i=1;i<Week_dates.size()-1;i++){
            if (Week_dates.get(i).isBefore(date)){
                if(result_date.contains(Date.valueOf(Week_dates.get(i)))){
                    attendance_report.add("Present");
                }else{
                    attendance_report.add("Absent");
                }
            } else if (Week_dates.get(i).equals(date)) {
                if(result_date.contains(Date.valueOf(Week_dates.get(i)))){
                    attendance_report.add("Present");
                }else{
                    attendance_report.add("------");
                }
            }else{
                attendance_report.add("------");
            }
        }
        attendance_report.add("Weekend");

        return attendance_report;
    }

    //date and day separate from week dates
    public static List<String> getDateDay(){
        List<LocalDate> Week_dates=getWeekDate();
        List<String> dateDay=new ArrayList<>();
        String[] days={"SUN","MON","TUE","WED","THU","FRI","SAT"};
        int i=0;
        for(LocalDate d:Week_dates){
            dateDay.add(days[i]+" "+String.valueOf(d).split("-")[2]);
            i++;
        }
        return dateDay;
    }
}
