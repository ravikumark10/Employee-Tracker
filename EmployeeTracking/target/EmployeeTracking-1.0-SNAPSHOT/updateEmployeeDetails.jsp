<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="com.ravikumar.employeetracking.DatabaseConnection" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Update Employee</title>
  <link rel="stylesheet" href="./CSS/home.css">
  <link rel="stylesheet" type="text/css" href="./CSS/admin.css">
  <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'>
</head>
<style>
  .approve{
    text-align: center;
    display: block;
    margin-top: 60px;
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

  //get employee details from database with specified employee id
  int id=Integer.parseInt(request.getParameter("id"));
  Connection con= DatabaseConnection.getDatabase();
  PreparedStatement st=con.prepareStatement("SELECT * FROM employeedetails WHERE Id=?");
  st.setInt(1,id);
  ResultSet employee_details=st.executeQuery();
  employee_details.next();

  //get designation value from database with specified designation id
  PreparedStatement st1=con.prepareStatement("SELECT * FROM designation WHERE Id=?");
  st1.setInt(1,employee_details.getInt("Designation_Id"));
  ResultSet designation=st1.executeQuery();
  designation.next();

  //get department value from database with specified department id
  PreparedStatement st2=con.prepareStatement("SELECT * FROM department WHERE Id=?");
  st2.setInt(1,employee_details.getInt("Team_Id"));
  ResultSet department=st2.executeQuery();
  department.next();
%>

<div class="header">
  <h2>Employee Management</h2>
  <div id="logout">
    <i class='fa fa-sign-out'></i>
    <a href="./logout">Logout</a>
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
<div class="approve">
  <form action="./update-employee-details" method="POST">
    <h3 style="text-align: center">Update Employee Details</h3>
    <label for="id">Employee Id</label>
    <input type="text" id="id" name="id" value="<%=employee_details.getString("Id")%>" required><br><br><br>
    <label for="name">Name</label>
    <input type="text" id="name" name="name" value="<%=employee_details.getString("Name")%>" required><br><br><br>
    <label for="mail">Mail</label>
    <input type="email" id="mail" name="mail" value="<%=employee_details.getString("Mail")%>" required><br><br><br>
    <label for="designation">Designation</label>
    <input type="text" id="designation" name="designation" value="<%=designation.getString("Name")%>" required><br><br><br>
    <label for="team">Department</label>
    <input type="text" id="team" name="team" value="<%=department.getString("Name")%>" required><br><br><br>
    <label for="reporting">Reporting To</label>
    <input type="text" id="reporting" name="reporting" value="<%=employee_details.getInt("Reporting_To")%>" required><br><br><br>
    <button id="formbtn" type="submit">Update</button>
  </form>
</div>
</body>
</html>