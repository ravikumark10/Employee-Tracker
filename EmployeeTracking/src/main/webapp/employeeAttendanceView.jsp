<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Attendance</title>
    <link rel="stylesheet" href="./CSS/home.css">
    <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'>

</head>
<style>
    .attendance{
        list-style: none;
    }
</style>
<body>
<%
    Object login_check= session.getAttribute("login_check");
    if(login_check==null){
        response.sendRedirect("login.jsp");
    }
    response.setContentType("text/html");
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    String check= session.getAttribute("home").toString();
    String home="";
    if(check.equals("Check In")){
        home="checkout";
    }else{
        home="checkin";
    }
%>
<div class="header">
    <h2>Employee Management</h2>
    <div id="logout">
        <i class='fa fa-sign-out'></i>
        <a href="./logout">Logout</a>
    </div>
</div>
<div class="sidebar">
    <a href="./<%=home%>">Home</a>
    <a class="active" href="./employee-attendance-view">Attendance</a>
    <a href="./employee-salary-view">Paychecks</a>
    <a href="./employee-hierarchy">Employee Position</a>
</div>
<%
    List<String> attendanceReport= (List<String>) request.getAttribute("attendance_report");
    List<String> dateDay= (List<String>) request.getAttribute("dateDay");
%>
    <div style="background-color:white" class="check-in">
        <h3>Attendance Report</h3>
        <div>
            <ul class="attendance">
            <% for(int i=0;i<7;i++){ %>
                <li><%=dateDay.get(i)%> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="attendance-value"><%=attendanceReport.get(i)%></span></li><br>
            <%}%>
            </ul>
        </div>
    </div>
</body>
<script>
    var value=document.getElementsByClassName("attendance")[0];
    for(var i=0;i<7;i++) {
        var value2 = value.getElementsByClassName("attendance-value")[i];
        if (value2.innerHTML === "Weekend") {
            value2.style.color = "orange";
        } else if (value2.innerHTML === "Present") {
            value2.style.color = "green";
        } else if (value2.innerHTML === "Absent") {
            value2.style.color = "red";
        }
    }
    // var val=document.getElementById("toggle-check").innerHTML;
    // function homeNavigation() {
    //     if (val === "Check In") {
    //         window.location.href = './checkout';
    //     } else {
    //         window.location.href = './checkin';
    //     }
    // }
</script>
</html>
