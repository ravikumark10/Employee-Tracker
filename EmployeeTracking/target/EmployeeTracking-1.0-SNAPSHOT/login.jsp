<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en" >
<head>
  <meta charset="UTF-8">
  <title>Login</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
  <link rel="stylesheet" href="./CSS/login.css">
</head>
<body>
<div class="wrapper">
  <div class="inner-warpper text-center">
    <h2 class="title">Login to your account</h2>
    <form action="./login" id="formvalidate" method="post">
      <div class="input-group">
        <label class="palceholder" for="Name">User Name</label>
        <input class="form-control" name="Mail" id="Name" type="text" placeholder="" required/>
        <span class="lighting"></span>
      </div>
      <div class="input-group">
        <label class="palceholder" for="Password">Password</label>
        <input class="form-control" name="Password" id="Password" type="password" placeholder="" required/>
        <span class="lighting"></span>
      </div>
      <div>
        <h3 style="color: red">${message}</h3>
      </div>
      <button type="submit" id="login">Login</button>
    </form>
  </div>
</div>
<script src='https://code.jquery.com/jquery-2.2.4.min.js'></script>
<script src='https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.15.0/jquery.validate.min.js'>
</script><script  src="./JS/login.js"></script>
</body>
</html>
