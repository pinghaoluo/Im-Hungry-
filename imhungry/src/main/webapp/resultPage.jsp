<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head profile="http://www.w3.org/2005/10/profile">
<link rel="icon" 
      type="image/png" 
      href="/somewhere/myicon.png" />
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="css/resultPage.css" />
	<link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>
	<meta charset="UTF-8">
	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/js/bootstrap.min.js"></script>
	<link rel="shortcut icon" href="">
	<title>Results Page</title>

<style>

.pagination{
	position: absolute;
	top: 320px;
	left: 30%;
}
</style>
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
<div id = "log">
		<input type= "button" onclick="logoutfunc()" id = "buttonlogin" value="Logout" />
	</div>
	<form name = "fakeform" action = "LoginServlet" method = "POST" id = "logoutForm">
			<input type = "text" id = "login3" name = "logoutformSIGNAL" value = "logoutformSIGNAL">
		</form>
	<div id = "header">Results</div>
	<div id = "collage"></div>
	<form action="listPage.jsp">
        <div class="dropDown">
            <select id = "dropdown" name="list">
                <option value="invalid">&nbsp</option>
                <option value="Favorites">Favorites</option>
                <option value="To Explore">To Explore</option>
                <option value="Do Not Show">Do Not Show</option>
            </select>
        </div>
   	 	<input type="submit" id = "manage_list" value="Manage List" />
	</form>
	
	<form action="searchPage.jsp">
   	 	<input type="submit" id = "back_search" value="Back to Search" />
	</form>
	
	
	<!--Jump to Grocery Page  -->
	<form action="grocery.jsp">
		<input type="submit" id = "display_grocery" value="Display Grocery" />
	</form>


	<div class = "sub_header1">Restaurants</div>
	<div class = "sub_header2">Recipes</div>
	<div id = "container">
		<div id = "column1">
		</div>
		<div id = "column2">
		</div>
	</div>
	<div id="quickAccess">
		<div id = "quickAccess1"></div>
		<div id = "quickAccess2"></div>
	</div>
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="js/jquery.easyPaginate.js"></script>
    <script src="js/dropdown.js"></script>
    <script src="js/parseQueryString.js"></script>
    <script src="js/ListClient.js"></script>
    <script src="js/jquery.twbsPagination.js"></script>
    <script>
   
        var query = parseQuery(window.location.search);
        
        var goToHell = query.search;
        goToHell += ",";
        goToHell += query.number;
        goToHell += ",";
        goToHell += query.radius;
        
        //Add the search term to quick access list
        addItem("Quick Access", goToHell);
        
        //Have to replace '+'s with ' 's before displaying name to user
        document.getElementById("header").innerHTML = 'Results for ' + query.search.replace(/\+/g, ' ');
        var results;
        var urlMap;
        //To reduce server overhead and improve performance, the page will only search from the server if it was arrived at from the search page
        //or if a list was modified on the last page. Otherwise, it'll load the results from localStorage (much faster).
        if(query.number == "cache") {
            results = JSON.parse(localStorage.getItem("searchResults"));
            urlMap = JSON.parse(localStorage.getItem("urlMap"));
        }
        else {
            var xhttp = new XMLHttpRequest();
            xhttp.open("GET", "/Search?search=" + query.search + "&number=" + query.number + "&radius=" + query.radius, false);
            xhttp.send();
            var response = JSON.parse(xhttp.response);
            results = response.body.results;
            urlMap = response.body.imageURLs;
        }        
        
        //Store results in local storage
        window.localStorage.setItem("search", query.search);
        window.localStorage.setItem("searchResults", JSON.stringify(results));
        window.localStorage.setItem("urlMap", JSON.stringify(urlMap));
        
        console.log(urlMap);

        //Assemble the collage
        var collage = document.getElementById("collage");
        var mainCollage = urlMap[0];
        console.log(mainCollage);
        for(let i = 0; i < mainCollage.length; i++) {
            //Create a div to hold this image
            let imgdiv = document.createElement("div");
            imgdiv.setAttribute("class", "imageDiv");
            imgdiv.setAttribute("id", "image"+i);
            //Create the img element
            let img = document.createElement("img");
            img.setAttribute("src", mainCollage[i]);
            img.setAttribute("class", "image");
            //Add the img to the div
            imgdiv.appendChild(img);
            //Generate a set of randomized position, rotation angle, scaling factor, and z index
            let x = 2*(i%5-1)*20+Math.floor(Math.random()*30);
            let y = 2*(i%2)*50+Math.floor(Math.random()*30)-20;
            let rot = Math.floor(Math.random()*90)-45;
            let scale = Math.random()*0.2+0.9;
            let z = Math.floor(Math.random()*50);
            //Apply a style to the element that applies the above transformations to it
            imgdiv.setAttribute("style", "-webkit-transform: translate("+x+"%, "+y+"%) rotate("+rot+"deg) scale("+scale+");" +
                "-ms-transform: translate("+x+"%, "+y+"%) rotate("+rot+"deg) scale("+scale+");" +
                "transform: translate("+x+"%, "+y+"%) rotate("+rot+"deg) scale("+scale+");" +
                "z-index:"+z+";");
            //Add the element to the collage
            collage.appendChild(imgdiv);
        }
    </script>

	<script>
	
	    $(function () {
	        window.pagObj = $('#column1').twbsPagination({
	            totalPages: Math.ceil(results[0].length/3),
	            visiblePages: 5,
	            onPageClick: function (event, page) {
	                console.info(page + ' 1(from options)');
	                
	              //First, populate restaurant results
		            var col1 = document.getElementById("column1");
	              
	                var pageNum = Math.ceil(results[0].length/3);
		          	
		          	var integerPart = 3;
		          	var decimalPart = results[0].length % 3;
		          	var counter = 0;
		          	
		          	if(counter == 0){
		          		
		          		console.log("Removal got called.");
		            	$('#column1 .Res_section1').remove();
		            	$('#column1 .Res_section2').remove();
		            	$('#column1 .divider').remove();
		            	$('#column1 .Res_section3').remove();
		            	$('#column1 .Res_section4').remove();
		            	$('#column1 .Res_section5').remove();
		            	$('#column1 .item').remove(); 
	            	}
		          	
		            for(let i = (page-1)*integerPart; i < results[0].length; i++) {
		            	
		            	console.log("page number:" + page);
		            	
		            	console.log("I am here.");
		            	
		            	counter++;
		            	
		                //Create each sub section for the entry and populate it with data and attributes
		                let sec1 = document.createElement("div");
		                sec1.setAttribute("class", "Res_section1");
		                sec1.innerHTML = results[0][i].name;

		                let sec2 = document.createElement("div");
		                sec2.setAttribute("class", "Res_section2");
		                for(let j = 0; j < 5; j++) {
		                    if(j < results[0][i].rating) sec2.innerHTML += '⭐';
		                    else sec2.innerHTML += '☆';
		                }

		                let divider = document.createElement("div");
		                divider.setAttribute("class", "divider");

		                let sec3 = document.createElement("div");
		                sec3.setAttribute("class", "Res_section3");
		                sec3.innerHTML = results[0][i].driveTimeText + " away";

		                let sec4 = document.createElement("div");
		                sec4.setAttribute("class", "Res_section4");
		                sec4.innerHTML = results[0][i].address;

		                let sec5 = document.createElement("div");
		                sec5.setAttribute("class", "Res_section5");
		                sec5.innerHTML = results[0][i].priceLevel;

		                //Create the actual entry element and set the previous subsections to be its children
		                let res = document.createElement("div");
		                res.setAttribute("class","item");
		                res.setAttribute("id","Res_item" + i);
		                //Sets the onclick so that you can navigate to the proper detailed page.
		                res.setAttribute("onclick","window.location='restaurantPage.jsp?i="+i+"'");
		                res.setAttribute("style","cursor:pointer;");
		                res.appendChild(sec1);
		                res.appendChild(sec2);
		                res.appendChild(divider);
		                res.appendChild(sec3);
		                res.appendChild(sec4);
		                res.appendChild(sec5);

		                //Add the entry to the proper place on the page
		                col1.appendChild(res);
		                
		                if(counter == integerPart)
		            		break;
		                /* if(decimalPart != 0 && page = pageNum && counter == decimalPart)
		                	break; */
		            }
		            
		            //create empty items for the last page
		            if(page == pageNum && decimalPart != 0){
		            	
		            	var newCount = 3 - decimalPart;
		            	
		            	for(let k = 0; k < newCount; k++){
		            	
			                //Create each sub section for the entry and populate it with data and attributes
			                let sec1 = document.createElement("div");
			                sec1.setAttribute("class", "Res_section1");
			                sec1.setAttribute("visibility", "hidden");
			                
	
			                let sec2 = document.createElement("div");
			                sec2.setAttribute("class", "Res_section2");
			                sec2.setAttribute("visibility", "hidden");
			                
	
			                let divider = document.createElement("div");
			                divider.setAttribute("class", "divider");
			                divider.setAttribute("visibility", "hidden");
	
			                let sec3 = document.createElement("div");
			                sec3.setAttribute("class", "Res_section3");
			                sec3.setAttribute("visibility", "hidden");
			                
	
			                let sec4 = document.createElement("div");
			                sec4.setAttribute("class", "Res_section4");
			                sec4.setAttribute("visibility", "hidden");
			                
	
			                let sec5 = document.createElement("div");
			                sec5.setAttribute("class", "Res_section5");
			                sec5.setAttribute("visibility", "hidden");
			                
	
			                //Create the actual entry element and set the previous subsections to be its children
			                let res = document.createElement("div");
			                res.setAttribute("class","item");
			                res.style.visibility = "hidden";
			                
			          
			                res.appendChild(sec1);
			                res.appendChild(sec2);
			                res.appendChild(divider);
			                res.appendChild(sec3);
			                res.appendChild(sec4);
			                res.appendChild(sec5);
	
			                //Add the entry to the proper place on the page
			                col1.appendChild(res);
		            	}
		            	
		            }
		            
		            
	            }
	        }).on('page', function (event, page) {
	            console.info(page + ' (from event listening)');
	            
	        
	        });
	    });
	
	
	</script>
	<script>
	
    $(function () {
        window.pagObj = $('#column2').twbsPagination({
            totalPages: Math.ceil(results[1].length/3),
            visiblePages: 5,
            onPageClick: function (event, page) {
                console.info(page + ' 2(from options)');
                
              //First, populate restaurant results
	            var col2 = document.getElementById("column2");
              
                var pageNum = Math.ceil(results[1].length/3);
	          	
	          	var integerPart = 3;
	          	var decimalPart = results[1].length % 3;
	          	var counter2 = 0;
	          	
	          	if(counter2 == 0){
	          		
	          		console.log("Removal2 got called.");
	            	$('#column2 .Res_section1').remove();
	            	$('#column2 .Res_section2').remove();
	            	$('#column2 .divider').remove();
	            	$('#column2 .Res_section3').remove();
	            	$('#column2 .Res_section4').remove();
	            	/* $('#column2 .Res_section5').remove(); */
	            	$('#column2 .item').remove(); 
            	}
	          	
	          	console.log("results[1].length" + results[1].length);
	          	
	            for(let i = (page-1)*integerPart; i < results[1].length; i++) {
	            	
	            	console.log("I am here2.");
	            	console.log("i-value: " + i);
	            	
	            	counter2++;
	            	
	            	//Same process as above, but for recipe results
	                /* var col2 = document.getElementById("column2"); */
	                /* for(let i = 0; i < results[1].length; i++) { */
	                    let sec1 = document.createElement("div");
	                    sec1.setAttribute("class", "Rec_section1");
	                    sec1.innerHTML = results[1][i].name;

	                    let sec2 = document.createElement("div");
	                    sec2.setAttribute("class", "Rec_section2");
	                    for(let j = 0; j < 5; j++) {
	                        if(j < results[1][i].rating) sec2.innerHTML += '⭐';
	                        else sec2.innerHTML += '☆';
	                    }

	                    let divider = document.createElement("div");
	                    divider.setAttribute("class", "divider");

	                    let sec3 = document.createElement("div");
	                    sec3.setAttribute("class", "Rec_section3");
	                    sec3.innerHTML = results[1][i].prepTime + " min prep time";

	                    let sec4 = document.createElement("div");
	                    sec4.setAttribute("class", "Rec_section4");
	                    sec4.innerHTML = results[1][i].cookTime + " min cook time";

	                    let res = document.createElement("div");
	                    res.setAttribute("class","item");
	                    res.setAttribute("id","Rec_item" + i);
	                    res.setAttribute("onclick","window.location='recipePage.jsp?i="+i+"'");
	                    res.setAttribute("style","cursor:pointer;");
	                    res.appendChild(sec1);
	                    res.appendChild(sec2);
	                    res.appendChild(divider);
	                    res.appendChild(sec3);
	                    res.appendChild(sec4);

	                    col2.appendChild(res);
	                    
	                    if(counter2 == 3){
	                    	console.log("I should break");
		            		break;
	                    }
	            }
	            
        //create empty items for the last page
        if(page == pageNum && decimalPart != 0){
        	
        	var newCount = 3 - decimalPart;
        	
        	for(let k = 0; k < newCount; k++){
        	
                //Create each sub section for the entry and populate it with data and attributes
                let sec1 = document.createElement("div");
                sec1.setAttribute("class", "Res_section1");
                sec1.setAttribute("visibility", "hidden");
                

                let sec2 = document.createElement("div");
                sec2.setAttribute("class", "Res_section2");
                sec2.setAttribute("visibility", "hidden");
                

                let divider = document.createElement("div");
                divider.setAttribute("class", "divider");
                divider.setAttribute("visibility", "hidden");

                let sec3 = document.createElement("div");
                sec3.setAttribute("class", "Res_section3");
                sec3.setAttribute("visibility", "hidden");
                

                let sec4 = document.createElement("div");
                sec4.setAttribute("class", "Res_section4");
                sec4.setAttribute("visibility", "hidden");
                

                //Create the actual entry element and set the previous subsections to be its children
                let res = document.createElement("div");
                res.setAttribute("class","item");
                res.style.visibility = "hidden";
                
          
                res.appendChild(sec1);
                res.appendChild(sec2);
                res.appendChild(divider);
                res.appendChild(sec3);
                res.appendChild(sec4);

                //Add the entry to the proper place on the page
                col2.appendChild(res);
        	}
        }
        	
        }
        }).on('page', function (event, page) {
            console.info(page + ' (from event listening)');
            
        
        });
    });
	
	
	
    function logoutfunc()
    {
    	location.href='searchPage.jsp';
    	document.getElementById('logoutForm').submit();
    }
	
	</script>
	<script src="js/quickAccess.js"></script>

</body>
</html>