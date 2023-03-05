<%@ page import="java.sql.ResultSet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Employee Salary</title>
    <link rel="stylesheet" href="./CSS/home.css">
    <link rel="stylesheet" href="./CSS/admin.css">
    <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'>
</head>
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
%>

<div class="header">
    <h2>Employee Management</h2>
    <div id="logout">
        <i class='fa fa-sign-out'></i>
        <a href="login.jsp">Logout</a>
    </div>
</div>
<div class="sidebar">
    <a href="./adminhome.jsp">Home</a>
    <a href="./addEmployee.jsp">Add Employee</a>
    <a href="./view">Manage Employee</a>
    <a class="active" href="./salary-employee">Employee Salary Ledger</a>
    <a href="./addDesignation.jsp">Add Designation</a>
    <a href="./addDepartment.jsp">Add Department</a>
</div>
<div id="admintable">
    <h2>Employee Salary Details</h2>
    <table id="admin">
        <tr>
            <th>Employee Id</th>
            <th>Name</th>
            <th>Payable Days</th>
            <th>To be Paid</th>
        </tr>
        <% ResultSet result= (ResultSet) request.getAttribute("result");
           while(result.next()){ %>
        <tr>
            <td><%=result.getInt("ID")%></td>
            <td><%=result.getString("Name")%></td>
            <td><%=result.getInt("Days")%></td>
            <td><%=String.format("%.2f",result.getDouble("Salary"))%></td>
        </tr>
        <% } %>
    </table>
</div>
</body>
</html>