<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.Format" %>
<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Home</title>
    <link rel="stylesheet" href="./CSS/home.css">
    <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'>
</head>
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
    %>
    <div class="header">
        <h2>Employee Management</h2>
        <div id="logout">
            <i class='fa fa-sign-out'></i>
            <a href="./logout">Logout</a>
        </div>
    </div>
    <% String check= request.getAttribute("check").toString();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Format f = new SimpleDateFormat("EEEE");
        String str = f.format(new Date());

        String in= request.getAttribute("in").toString();
        String out1= request.getAttribute("out").toString();
        session.setAttribute("home",check);
        String home="";
        if(check.equals("Check In")){
            home="checkout";
        }else{
            home="checkin";
        }
    %>

    <div class="sidebar">
      <a class="active" href="./<%=home%>">Home</a>
      <a href="./employee-attendance-view">Attendance</a>
      <a href="./employee-salary-view">Paychecks</a>
      <a href="./employee-hierarchy">Employee Position</a>
    </div>

    <div style="background-color:white" class="check-in">
        <button style="background-color:#e9e7e7" id="toggle-check" onclick="checkToggle()"><%=check%></button>
        <h3><%=sdf.format(cal.getTime())+"      "+str%></h3>
        <div>
            <h4>Office In:<%=in%></h4>
            <h4>Office Out:<%=out1%></h4>
        </div>
    </div>
</body>
<script>
        var val=document.getElementById("toggle-check").innerHTML;
        function checkToggle(){
            if(val==="Check In"){
                window.location.href='./checkin';
            }else{
                window.location.href='./checkout';
            }
        }
</script>
</html>