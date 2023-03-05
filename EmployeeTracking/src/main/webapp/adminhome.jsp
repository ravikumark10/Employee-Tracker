<%@ page import="java.sql.Connection" %>
<%@ page import="com.ravikumar.employeetracking.DatabaseConnection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.sql.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>admin home</title>
    <link rel="stylesheet" href="./CSS/home.css">
    <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'>
    <script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
</head>
<style>
    .dashboard1{
        margin-top:200px ;
        margin-left: 400px;
        margin-right: 700px;
        text-align: center;
        border: 1px solid black;
        padding: 20px;
        border-radius: 5px;
        box-shadow:5px 5px 5px 5px #7F7F7F;
        display: flex;
    }
    .dashboard{
        border: 1px solid black;
        padding: 10px;
        margin: 10px;
    }
    .count-value{
        font-size:30px ;
    }
</style>
<body>
<%
        Object adminlogin_check= session.getAttribute("adminlogin_check");
        if(adminlogin_check==null){
            response.sendRedirect("login.jsp");
        }
    response.setContentType("text/html");
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
    Connection con= DatabaseConnection.getDatabase();

    //count total number of employees
    PreparedStatement st=con.prepareStatement("SELECT COUNT(*) AS count_employee FROM employeedetails");
    ResultSet employee=st.executeQuery();
    employee.next();

    //count today check-in employees
    LocalDate date=LocalDate.now();
    PreparedStatement st1=con.prepareStatement("SELECT COUNT(*) AS present_employee_count FROM attendance WHERE Date_value=?");
    st1.setDate(1, Date.valueOf(date));
    ResultSet present_employee=st1.executeQuery();
    present_employee.next();
%>
  <div class="header">
    <h2>Employee Management</h2>
      <div id="logout">
          <i class='fas fa-power-off'></i>
          <a href="./logout">Logout</a>
      </div>
  </div>
  <div class="sidebar">
    <a class="active" href="./adminhome.jsp">Home</a>
    <a href="./addEmployee.jsp">Add Employee</a>
    <a href="./view">Manage Employee</a>
    <a href="./salary-employee">Employee Salary Ledger</a>
    <a href="./addDesignation.jsp">Add Designation</a>
    <a href="./addDepartment.jsp">Add Department</a>
  </div>
<div style="background-color:white" class="dashboard1">
    <div class="dashboard">
        <h3></i>Number Of Employees</h3>
        <h3 class="count-value"><i class='fas fa-user-friends'></i>&nbsp;&nbsp;<%=employee.getInt("count_employee")%></h3>
    </div>
    <div class="dashboard">
        <h3>Present Employees</h3>
        <h3 class="count-value"><i class='fas fa-user-clock'></i>&nbsp;&nbsp;<%=present_employee.getInt("present_employee_count")%></h3>
    </div>
</div>
</body>
</html>