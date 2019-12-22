var list = getList("Quick Access").body;
    document.getElementById("quickAccess1").innerHTML += "| Quick Access List |";
    document.getElementById("quickAccess1").innerHTML += "<br/>";
    for(let i = 0;i < list.length;i++){
    	var quickAccessItem = document.createElement("div");
    	quickAccessItem.setAttribute("class", "quickAccessItem");
    	var itemString = document.createElement("div");
    	itemString.innerHTML += "<a href = 'http://localhost:9090/resultPage.jsp?search=" + list[i] + "&number=5&radius=5&submit.x=0&submit.y=0'>" + list[i];
    	itemString.innerHTML += "&nbsp";
    	itemString.setAttribute("class", "quickAccessText");
    	quickAccessItem.appendChild(itemString);
    	
    		
    	var collage = document.createElement("div");
    	collage.setAttribute("class", "miniCollage");
    	
    	

        var searchTermURL = urlMap[i];
        console.log(searchTermURL);
        
	    for(let j = 0; j < searchTermURL.length; j++) {
	        //Create a div to hold this image
	        let imgdiv = document.createElement("div");
	        imgdiv.setAttribute("class", "imageDivq");
	        imgdiv.setAttribute("id", "image"+j);
	        //Create the img element
	        let img = document.createElement("img");
	        img.setAttribute("src", searchTermURL[j]);
	        img.setAttribute("class", "image");
	        //Add the img to the div
	        imgdiv.appendChild(img);
	        //Generate a set of randomized position, rotation angle, scaling factor, and z index
	        let x = i*75+Math.floor(Math.random()*30);
	        if(i == 0){
	        	if(j % 2 == 0) x *= -1;
	        }
	        let y = Math.floor(Math.random()*10);
	        y += 20;
	        y *= -1;
	        let rot = Math.floor(Math.random()*90)-45;
	        let scale = 0.1;
	        //let z = Math.floor(Math.random()*50);
	        //Apply a style to the element that applies the above transformations to it
	        imgdiv.setAttribute("style", "-webkit-transform: translate("+x+"%, "+y+"%) rotate("+rot+"deg) scale("+scale+");");
	        //Add the element to the collage
	        collage.appendChild(imgdiv);
	    }
	    quickAccessItem.appendChild(collage);
//	    
	    
	    document.getElementById("quickAccess2").appendChild(quickAccessItem);
    }
    
//    for(var i = 0;i < list.length;i++){
//    	if (i === 6) {
//    	    break;
//    	}
//    	var noun = list[i];
//    	noun = noun.substring(1, noun.length-1);
//    	var nounToSearch = noun;
//    	console.log(nounToSearch);
//    	if (i == 0)
//    	{
//    		document.getElementById("quickAccess2").innerHTML += "<a href = 'http://localhost:9090/resultPage.jsp?search=" + nounToSearch + "&number=5&radius=2000&submit.x=0&submit.y=0'>" + noun;
//    		document.getElementById("quickAccess2").innerHTML += "<br/>";
//    	}
//    	if (i == 1)
//		{
//    		document.getElementById("quickAccess3").innerHTML += "<a href = 'http://localhost:9090/resultPage.jsp?search=" + nounToSearch + "&number=5&radius=2000&submit.x=0&submit.y=0'>" + noun;
//    		document.getElementById("quickAccess2").innerHTML += "<br/>";
//		}
//    	if (i == 2)
//		{
//    		document.getElementById("quickAccess4").innerHTML += "<a href = 'http://localhost:9090/resultPage.jsp?search=" + nounToSearch + "&number=5&radius=2000&submit.x=0&submit.y=0'>" + noun;
//    		document.getElementById("quickAccess2").innerHTML += "<br/>";
//		}
//    	if (i == 3)
//		{
//    		document.getElementById("quickAccess5").innerHTML += "<a href = 'http://localhost:9090/resultPage.jsp?search=" + nounToSearch + "&number=5&radius=2000&submit.x=0&submit.y=0'>" + noun;
//    		document.getElementById("quickAccess2").innerHTML += "<br/>";
//		}
//    	if (i == 4)
//		{
//    		document.getElementById("quickAccess6").innerHTML += "<a href = 'http://localhost:9090/resultPage.jsp?search=" + nounToSearch + "&number=5&radius=2000&submit.x=0&submit.y=0'>" + noun;
//    		document.getElementById("quickAccess2").innerHTML += "<br/>";
//		}
//    	if (i == 5)
//		{
//    		document.getElementById("quickAccess7").innerHTML += "<a href = 'http://localhost:9090/resultPage.jsp?search=" + nounToSearch + "&number=5&radius=2000&submit.x=0&submit.y=0'>" + noun;
//    		document.getElementById("quickAccess2").innerHTML += "<br/>";
//		}
//    }