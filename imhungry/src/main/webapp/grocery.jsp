<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	<link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Grocery</title>
		<link rel="stylesheet" type="text/css" href="css/listPage.css" />
	</head>
	<body>
	<script>
		//login check
		var check = "<%=session.getAttribute("log")%>"
		if (check != "login")
	    {
	    	//console.log("JJ");
			location.href='login.jsp';
	    }
		</script>
		<form action="resultPage.jsp">
			<input type="hidden" id="queryStringInput" name="search" value="" />
			<input type="hidden" id="numberResultsInput" name="number" value="cache" />
			<input type="submit" id = "back_result" value="Back to Results" />
		</form>
	
		<form action="searchPage.jsp">
			<input type="submit" id = "back_search" value="Back to Search" />
		</form>
		
		<div id = "log">
			<input type= "button" onclick="logoutfunc()" id = "buttonlogin" value="Logout" />
		</div>
		<form name = "fakeform" action = "LoginServlet" method = "POST" id = "logoutForm">
			<input type = "text" id = "login3" name = "logoutformSIGNAL" value = "logoutformSIGNAL">
		</form>
	    
		<div id = "header"></div>
		<div id = "container">
			<ul id = "text"></ul>
			<script src="js/ListClient.js"></script>
			<script>
				document.getElementById("header").innerHTML = "Grocery List";
				
				var col1 = document.getElementById("container");
				
				var list = getList("Grocery").body;
				
				if(list == null || list.length === 0) col1.innerHTML = "This list is empty. Add something to see it here!" ;
				else{
					for(let i = 0;i < list.length; i++){
						var element = document.createElement("li");
						element.innerHTML += i + ". ";
						element.innerHTML += list[i].item;
						var button = document.createElement("button");
						button.innerHTML = "Remove Item";
						button.addEventListener ("click", function() {
							  removeItem("Grocery", list[i].item);
							  location.reload();
							}, false);
						element.appendChild(button);
						document.getElementById("text").appendChild(element); /*  innerHTML += list[i].item; */
						/* document.getElementById("text").innerHTML += "\n"; */ 
					}
				}
				
				function logoutfunc()
			    {
			    	location.href='searchPage.jsp';
			    	document.getElementById('logoutForm').submit();
			    }
			</script>
		</div>

		<script src="js/dropdown.js"></script>
		<script src="js/ListClient.js"></script>
		<script src="js/parseQueryString.js"></script>
		
		
		
	</body>
</html>