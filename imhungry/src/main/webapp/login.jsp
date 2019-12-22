<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Page</title>
<link rel="stylesheet" type="text/css" href="css/searchPage.css" />
<link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>
</head>
<body>
<div id = "header">I'm Hungry </div>
<div class = "b">
	<form name = "myform" action = "LoginServlet" method = "POST">
	<div class = "loginform" id ="login" >
			Username 
			<br>
			<input type = "text" id = "login1" name = "username" value =${param.username!=null? param.username : ''}> 
			<span style="color: red;font-weight:bold">${uerror!=null? uerror : ''}</span><br />
			<!--  what's submitted is value, didn't write it here because it's by default blank. -->
			<input type = "text" id = "login3" name = "logoutformSIGNAL" value = "kk">
			<br>
			Password 
			<br>
			<input type = "password" id = "login2" name = "pw" value =${param.pw!=null? param.pw : ''}>
			<span style="color: red;font-weight:bold">${perror!=null? perror : ''}</span><br />
			<div id = "but">
			<br>
			<input type = "submit" id = "buttonlogin" name="login" value ="Login" />
			<input type= "button" onclick="location.href='signup.jsp';" id = "buttonlogin" value="Register" />
			</div>
			</div>
			</form>
</div>

</body>
</html>
