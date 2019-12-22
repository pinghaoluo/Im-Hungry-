<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/searchPage.css" />
<link href="https://afeld.github.io/emoji-css/emoji.css" rel="stylesheet">
<link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>
<meta charset="UTF-8">
<title>Search Page</title>
</head>
<body>
		<!--Jump to Grocery Page  -->
	<form action="grocery.jsp">
		<input type="submit" id = "display_grocery" value="Display Grocery" />
	</form>

	
	<div id = "header">I'm Hungry </div>
	<div id = "format">
		<form action = "resultPage.jsp" method = "GET">
			<input type = "text" name = "search" id = "search" placeholder = "Enter Food" required />
			<div id = hover_format>
				<input type = "number" name = "number" id = "number" value = "5" min= "1" />
				<div id = "hover_text">
					Number of items to show in results
				</div>
			</div>
			<div id = hover_format1>
				<input type = "number" name = "radius" id = "radius" value = "5" min= "1" />
				<div id = "hover_text1">
					Restaurant search radius
				</div>
			</div>
		
			<br>
			<input type = "image" src="resources/grumpy.png" onmousedown="sadToHappy()" onmouseleave="happyToSad()" name = "submit" id ="submit" value = "Feed Me!" />
			
		</form>
		<form name = "fakeform" action = "LoginServlet" method = "POST" id = "logoutForm">
			<input type = "text" id = "login3" name = "logoutformSIGNAL" value = "logoutformSIGNAL">
		</form>
	</div>
	<div id = "nlog">
		<input type= "button" onclick="location.href='login.jsp';" id = "buttonlogin" value="Login" />
		<input type= "button" onclick="location.href='signup.jsp';" id = "buttonlogin" value="Sign Up" />
	</div>
	<div id = "log">
		<input type= "button" onclick="logoutfunc()" id = "buttonlogin" value="Logout" />
	</div>
	
	
	<div id="quickAccess">
		<div id = "quickAccess1"></div>
		<div id = "quickAccess2"></div>
	</div>
<script src="js/ListClient.js"></script>
<script>
var list = getList("Quick Access").body;
document.getElementById("quickAccess1").innerHTML += "| Quick Access List |";
document.getElementById("quickAccess1").innerHTML += "<br/>";
for(let i = 0;i < list.length;i++){
	if (i === 6) {
	    break;
	}
	var noun = list[i];
	var displaynoun = noun;
	var prefix = "■ - ";
	displaynoun = prefix.concat(noun);
	var itemString = document.getElementById("quickAccess2");
	itemString.innerHTML += "<a href = 'http://localhost:9090/resultPage.jsp?search=" + noun + "&number=5&radius=5&submit.x=0&submit.y=0'>" + displaynoun;
	itemString.innerHTML += "</br>";
}
</script>
<script>

    //Functions to switch emoji states
    function happyToSad()
    {
        document.getElementById("submit").src = "resources/grumpy.png";
    }
    function sadToHappy()
    {
        document.getElementById("submit").src = "resources/smile.png";
    }
    // login function
    function logoutfunc()
    {
    	location.href='searchPage.jsp';
    	document.getElementById('logoutForm').submit();
    }
    
    //login check
    var check = "logout";
    var check = "<%=session.getAttribute("log")%>"
    //alert(check);
    if (check == "login")
    {
    	//console.log("JJ");
    	var nlog = document.getElementById("nlog");
    	nlog.setAttribute("style","visibility:hidden;");
    	var log = document.getElementById("log");
    	log.setAttribute("style","visibility:visible;");
    }
    else
    {
    	//console.log("KK");
    	var nlog = document.getElementById("nlog");
    	nlog.setAttribute("style","visibility:visible;");
    	var log = document.getElementById("log");
    	log.setAttribute("style","visibility:hidden;");
    }
    
</script>

</body>
</html>