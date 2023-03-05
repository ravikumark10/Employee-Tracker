<%@ page import="java.sql.ResultSet" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Salary</title>
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
  <a href="./employee-attendance-view">Attendance</a>
  <a class="active" href="./employee-salary-view">Paychecks</a>
  <a href="./employee-hierarchy">Employee Position</a>
</div>
<% ResultSet salaryDetails= (ResultSet) request.getAttribute("salaryDetails");
%>
<div style="background-color:white" class="check-in">
  <h3>Salary Details</h3>
  <div>
    <h4>Payable Days: <%=salaryDetails.getInt("DAYS")%></h4>
    <h4>To Be Received: <%=String.format("%.2f",salaryDetails.getDouble("Salary"))%></h4>
  </div>
</div>
</body>
</html>