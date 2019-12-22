<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sign Up</title>
<link rel="stylesheet" type="text/css" href="css/searchPage.css" />
<link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>
</head>
<body>
<div id = "header">I'm Hungry </div>
<div class = "b">
	<form name = "myform" action = "SignupServlet" method = "POST">
			<div class = "signupform" id ="signup" >
			Username<br><input type = "text" id ="signup1" name = "username" value =${param.username!=null? param.username : ''}> 
			<span style="color: red;font-weight:bold">${uerror!=null? uerror : ''}</span><br />
			<br>
			<!--  what's submitted is value, didn't write it here because it's by default blank. -->
			Password<br> <input type = "password" id ="signup2" name = "pw" value =${param.pw!=null? param.pw : ''}>
			<span style="color: red;font-weight:bold">${perror!=null? perror : ''}</span><br />
			<br>
			Re-enter Password <br><input type = "password" id ="signup3" name = "pw2" value =${param.imageurl!=null? param.imageurl : ''}>
			<span style="color: red;font-weight:bold">${ierror!=null? ierror : ''}</span><br />
			<br>
			<input type= "button" onclick="location.href='login.jsp';" id = "buttonlogin" value="Back To Login" />
			<input type = "submit" id = "buttonlogin" name="submit" value ="Sign Up"/>
			</div>
			</form>
</div>

</body>
</html>
