<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.ResultSet" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Manage Employee</title>
  <link rel="stylesheet" href="./CSS/home.css">
  <link rel="stylesheet" href="./CSS/admin.css">
  <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'>
</head>
<style>
  #update a{
    padding: 5px;
    border: 1px solid black;
    background-color:snow;
    color: black;
    text-decoration: none;
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
    <a class="active" href="./view">Manage Employee</a>
    <a href="./salary-employee">Employee Salary Ledger</a>
    <a href="./addDesignation.jsp">Add Designation</a>
    <a href="./addDepartment.jsp">Add Department</a>
  </div>
  <div id="admintable">
    <h2>Employee Details</h2>
    <h3 style="color: green; text-align: center">${alert}</h3>
    <table id="admin">
      <tr>
        <th>Employee Id</th>
        <th>Name</th>
        <th>Mail Id</th>
        <th>Designation</th>
        <th>Department</th>
        <th>Reporting To</th>
        <th></th>
      </tr>
      <%ResultSet result= (ResultSet) request.getAttribute("result");

      %>

      <% while (result.next()){%>
      <tr>
        <td><%= result.getString("Id")%></td>
        <td><%= result.getString("Name")%></td>
        <td><%= result.getString("Mail")%></td>
        <td><%= result.getString("Designation_Id")%></td>
        <td><%= result.getString("Team_Id")%></td>
        <td><%= result.getString("Reporting_To")%></td>
        <td id="update"><a href="./updateEmployeeDetails.jsp?id=<%=result.getString("Id")%>">Update</a></td>
      </tr>
      <%}%>
    </table>
  </div>
</body>
</html>