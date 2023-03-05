<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="com.ravikumar.employeetracking.DatabaseConnection" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.stream.Stream" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Add Employee</title>
  <link rel="stylesheet" href="./CSS/home.css">
  <link rel="stylesheet" type="text/css" href="./CSS/admin.css">
  <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'>
</head>
<style>
  #employeelist{
    float: right;
    border: 1px solid black;
    list-style: none;
    margin-left: 30px;
    background-color: #d6e2e6;
  }
  #employee_a{
    text-decoration: none;
    color: black;
    padding: 5px;
  }

  select{
    float: right;
    font-size: 16px;
    padding: 5px;
    width: 205px;
  }
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

  Connection con= DatabaseConnection.getDatabase();

  //Get Designation value from database
  PreparedStatement st=con.prepareStatement("SELECT * FROM designation");
  ResultSet designation=st.executeQuery();

  //Get Department value from database
  PreparedStatement st1=con.prepareStatement("SELECT * FROM department");
  ResultSet department=st1.executeQuery();

  //Get Employee Id,Name for reporting-to filter from database
  PreparedStatement st2=con.prepareStatement("SELECT Id,Name FROM employeedetails");
  ResultSet reporting=st2.executeQuery();
  List<String> employee_id=new ArrayList<>();
  while (reporting.next()){
    employee_id.add(reporting.getString("Id")+"-"+reporting.getString("Name"));
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
    <a href="./adminhome.jsp">Home</a>
    <a class="active" href="./addEmployee.jsp">Add Employee</a>
    <a href="./view">Manage Employee</a>
    <a href="./salary-employee">Employee Salary Ledger</a>
    <a href="./addDesignation.jsp">Add Designation</a>
    <a href="./addDepartment.jsp">Add Department</a>
  </div>
  <div class="approve">
    <form action="./add" method="post">
      <h3 style="text-align: center">Add Employee</h3>
      <h3 style="color: green; text-align: center">${alert}</h3>
      <label for="name">Name</label>
      <input type="text" id="name" name="name" placeholder="Name" required><br><br><br>
      <label for="mail">Mail</label>
      <input type="email" id="mail" name="mail" placeholder="Mail" required><br><br><br>
      <label for="password">Password</label>
      <input minlength="8" type="text" id="password" name="password" placeholder="Password" required><br><br><br>
      <label for="designation">Designation</label>
      <select type="text" id="designation" name="designation" required>
        <option value="Designation">Designation</option>
        <% while (designation.next()){ %>
        <option value="<%=designation.getString("Name")%>"><%=designation.getString("Name")%></option>
        <% } %>
      </select><br><br><br>
      <label for="team">Department</label>
      <select type="text" id="team" name="team" required>
        <option value="Department">Department</option>
        <% while (department.next()){ %>
        <option value="<%=department.getString("Name")%>"><%=department.getString("Name")%></option>
        <% } %>
      </select><br><br><br>
      <label for="reporting">Reporting To</label>
      <input onkeyup="display()" onfocusout="fade()" type="text" id="reporting" name="reporting" placeholder="Reporting To" required><br>
        <ul id="employeelist" style="visibility: hidden">
        <% for(int i=0;i<employee_id.size();i++){ %>
        <li><a id="employee_a" href="#"><%=employee_id.get(i)%></a></li>
        <% } %>
        </ul>
      </input><br><br><br>
      <button id="formbtn" type="submit">Add</button>
    </form>
  </div>
</body>

<script>
  var employeelist=document.getElementById("employeelist");
  function display(){
    employeelist.style.visibility="visible";
    var input, filter, ul,a, li,i,txtValue;
    input = document.getElementById("reporting");
    filter = input.value.toUpperCase();
    ul = document.getElementById("employeelist");
    li = ul.getElementsByTagName("li");
    for (i = 0; i < li.length; i++) {
      a = li[i].getElementsByTagName("a")[0];
      txtValue = a.textContent || a.innerText;
      if (txtValue.toUpperCase().indexOf(filter) > -1) {
        li[i].style.display = "";
      } else {
        li[i].style.display = "none";
      }
    }
  }
  function fade(){
    employeelist.style.visibility="hidden";
  }
</script>
</html>