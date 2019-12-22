<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
	<head>
	<link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Recipe Page</title>
		<link rel="stylesheet" type="text/css" href="css/detailedPage.css" />
	</head>
	<style>
		#display_grocery{
			font-family: 'Roboto', sans-serif;
			background-color:#ffc1ab;
			color: white;
			width:15vw;
			min-width: 12rem;
			border: 0;
			box-shadow: none;
			border-radius: 0;
			font-size:1.5rem;
			cursor: pointer;
			padding: 0;
			position: fixed;
			margin-bottom:0;
			right: 20vw;
			top:calc(20vh + 15rem);
		}
	</style>
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
	<div id = "log">
		<input type= "button" onclick="logoutfunc()" id = "buttonlogin" value="Logout" />
	</div>
	<form name = "fakeform" action = "LoginServlet" method = "POST" id = "logoutForm">
			<input type = "text" id = "login3" name = "logoutformSIGNAL" value = "logoutformSIGNAL">
		</form>
		<div class = "textinfo">
			<p id="title" style="margin-bottom: 1rem"></p>
			<p id="img"></p>
			<div class = "prepT">
				<span id ="prept1" style="font-size:2rem">Prep Time:</span>
				<span id ="prept2"></span>
			</div>
			<div class ="cookT">
				<span id="cookt1" style="font-size:2rem">Cook Time:</span>
				<span id="cookt2"></span>
			</div>
			<div class ="ingre">
				<span id="ingre1" style="font-size:2rem">Ingredients:</span>
				<div id="ingre2"></div>
			</div>
			<div class ="instr">
				<span id="instr1" style="font-size:2rem">Instructions:</span>
				<div id="instr2"></div>
			</div>
        </div>
        
     
        
        <form action = "resultPage.jsp">
            <div class = "backToResults">
                <input type="hidden" id="queryStringInput" name="search" value="" />
                <input type="hidden" id="numberResultsInput" name="number" value="cache" />
                <input type="hidden" id="radiusInput" name="radius" value="cache" />
                <button type="submit" id = "backtoresults">Back to Results</button>
            </div>
        </form>

        <form action = "recipePagePrint.jsp">
            <div class = "printableVersion">
                <input type="hidden" id="indexInput" name="i" value="">
                <button type="submit" id = "printableversion">Printable Version</button>
            </div>
        </form>

        <form onclick = "addItem(document.getElementById('dropdown').value, result);">
            <div class = "addToList">
                <button type="button" id = "addtolist">Add to List</button>
            </div> 
        </form>
        
        <form onclick = "console.log(result); addItem('Grocery', result);">
            <div class = "addToGrocery">
                <button type="button">Add to Grocery</button>
            </div>
        </form>
        
      	<!--Jump to Grocery Page  -->
		<form action="grocery.jsp">
			<input type="submit" id = "display_grocery" value="Display Grocery" />
		</form>
        
       
 

        <div class="dropDown">
            <select id = "dropdown">
                <option value="invalid">&nbsp</option>
                <option value="Favorites">Favorites</option>
                <option value="To Explore">To Explore</option>
                <option value="Do Not Show">Do Not Show</option>
            </select>
        </div>

        <script src="js/dropdown.js"></script>
        <script src="js/ListClient.js"></script>
        <script src="js/parseQueryString.js"></script>
		<script src="js/recipePage.js"></script>
		<script>
		function logoutfunc()
	    {
	    	location.href='searchPage.jsp';
	    	document.getElementById('logoutForm').submit();
	    }
		</script>
	</body>
</html>