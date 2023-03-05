
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add Designation</title>
    <link rel="stylesheet" href="./CSS/home.css">
    <link rel="stylesheet" type="text/css" href="./CSS/admin.css">
    <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'>
</head>
<style>
    .approve{
        text-align: center;
        display: block;
        margin-top: 200px;
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
        <a href="./logout">Logout</a>
    </div>
</div>
<div class="sidebar">
    <a href="./adminhome.jsp">Home</a>
    <a href="./addEmployee.jsp">Add Employee</a>
    <a href="./view">Manage Employee</a>
    <a href="./salary-employee">Employee Salary Ledger</a>
    <a class="active" href="./addDesignation.jsp">Add Designation</a>
    <a href="./addDepartment.jsp">Add Department</a>
</div>
<div class="approve">
    <form action="./add-designation" method="post">
        <h3 style="text-align: center">Add Designation</h3>
        <h3 style="color: green; text-align: center">${alert}</h3>
        <label for="designation">Designation</label>
        <input type="text" id="designation" name="designation" placeholder="Designation" required><br><br><br>
        <label for="salary">Salary</label>
        <input type="number" id="salary" name="salary" placeholder="Salary" required><br><br><br>
        <button id="formbtn" type="submit">Add</button>
    </form>
</div>
</body>
</html>