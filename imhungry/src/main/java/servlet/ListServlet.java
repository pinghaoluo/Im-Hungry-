package servlet;

import com.google.gson.Gson;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import database_manager.GroceryDataManager;
import database_manager.RecipeDataManager;
import database_manager.RestaurantDataManager;
import database_manager.HistoryDataManager;
import database_manager.OrderDataManager;
import info.Info;
import info.RecipeInfo;
import info.RestaurantInfo;
import message.Message;
import info.GroceryInfo;
import info.History;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "ListServlet", urlPatterns = "/Lists")
public class ListServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    //GET method used to fetch contents of a list
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        String listName = request.getParameter("list"); //See what list was requested
        
        String username = (String)session.getAttribute("username");
        
        PrintWriter respWriter = response.getWriter();
        Gson gson = new Gson();
        if(!listName.equals("Favorites") && !listName.equals("To Explore") && !listName.equals("Do Not Show") && !listName.equals("Grocery") && !listName.equals("Quick Access")) //Check if list is valid
        {
            respWriter.println(gson.toJson(new Message("Invalid List!")));
            respWriter.close();
            return;
        }
        if(listName.equals("Quick Access")) {
        	HistoryDataManager historyDB = new HistoryDataManager(username);
        	ArrayList<History> quickAccessList;
        	if(session.getAttribute("Quick Access") == null) { //the first time we set this list
        		//load quickAccessList from database
    			quickAccessList = historyDB.loadHistory();
    			session.setAttribute("Quick Access", quickAccessList);
        	}
        	else {
        		quickAccessList = (ArrayList<History>)session.getAttribute("Quick Access");
        	}
			List<History> historyList = quickAccessList;
        	List<String> listToDisplay = new ArrayList<String>();
        	for(int i = 0;i < historyList.size();i++){
        		listToDisplay.add(historyList.get(i).query); //add it to the head
        	}
        	respWriter.println(gson.toJson(new Message(listName,listToDisplay))); //convert to JSON before sending it to the response
            respWriter.close();
            return;
    	}
    	List<Info> list = (List<Info>)session.getAttribute(listName); //Cast stored list to correct type and
        respWriter.println(gson.toJson(new Message(listName,list))); //convert to JSON before sending it to the response
        respWriter.close();
    }

    //POST method used to add and remove items from a list
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        
        String username = (String)session.getAttribute("username"); 
        
        String reqBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator())); //Java 8 magic to collect all lines from a BufferedReadder, in this case the request.
        Gson gson = new Gson();
        PrintWriter respWriter = response.getWriter();
        try
        {
            Message reqMessage = gson.fromJson(reqBody, Message.class); //Parse outer Message object from JSON
            Message reqListAndItem = gson.fromJson((String)reqMessage.body, Message.class); //Parse inner Message object from json
            
            String listName = reqListAndItem.header;
            if(!listName.equals("Favorites") && !listName.equals("To Explore") && !listName.equals("Do Not Show") && !listName.equals("Grocery") && !listName.equals("Quick Access")) {
            	//Check validity
                throw new Exception("Invalid list name.");
            }
            if(listName.equals("Quick Access")) {
	        	String s = (String)reqListAndItem.body;
	        	System.out.println(s);
	        	s = s.replaceAll("\"", "");
	        	String[] parts = s.split(",");
	        	if(parts[1].equals("cache") || parts[2].equals("cache")) {
	        		respWriter.println(gson.toJson(new Message("Returned from other pages "+listName)));
		        	return;
	        	}
	        	System.out.println(s);
	        	s = parts[0];
	        	int numResults = Integer.parseInt(parts[1]);
	        	int radius = Integer.parseInt(parts[2]);
	        	
	        	List<History> historyList = (List<History>)session.getAttribute(listName);
	        	//Prevent adding the search term to list
	        	List<String> checkList = new ArrayList<String>();
	        	//System.out.println(historyList.size());
	        	for(int i = 0;i < historyList.size();i++) {
	        		checkList.add(historyList.get(i).query);
	        	}
	        	//If the item is not in the list or the list is empty
	        	if(!checkList.contains(s) || historyList.size() == 0) {
		        	History h = new History(s,numResults,radius);
		        	HistoryDataManager historyDB = new HistoryDataManager(username);
		        	historyDB.addToList(h);
		        	historyList.add(0, h);
		        	respWriter.println(gson.toJson(new Message("Search term successfully added "+listName)));
		        	return;
	        	}
	        	else {
	        		if(checkList.size() > 1) { //if the checkList has more than 1 item (then we can swap the currently item to the front)
	        			int index = 0;
	        			for(index = 0;index < checkList.size();index++) {
	    	        		if(checkList.get(index).equals(s)) {
	    	        			break;
	    	        		}
	    	        	}
	        			if(index != 0) { //the index is not the head
	        				Collections.swap(historyList, index, 0); //swap the values
	        			}
	        		}
	        	}
	        	respWriter.println(gson.toJson(new Message("Search term already added "+listName)));
	        	return;
            }

            if(listName.equals("Grocery")) {
            	if(reqMessage.header.equals("removeItem")) {
            		String groceryName = ((String)reqListAndItem.body).replaceAll("\"", "");
            		System.out.println(groceryName);
                	GroceryDataManager groceryDB = new GroceryDataManager(username);
                	List<GroceryInfo> list = (List<GroceryInfo>)session.getAttribute(listName);
                	groceryDB.removeFromList(groceryName);
                	list.remove(new GroceryInfo(groceryName));
                	respWriter.println(gson.toJson(new Message("Success")));
                	return;
            	}
            }
            
            //Either move the item up or down
            if(reqMessage.header.equals("moveUp") || reqMessage.header.equals("moveDown")) {
            	List<Info> list = (List<Info>)session.getAttribute(listName); //Get the requested list from session
            	String curr = ((String)reqListAndItem.body);
            	int currOrder = Integer.parseInt(curr); //the current order of the selected item
            	int toSwapOrder; //the new order of the selected item(either old order -1 or +1);
            	
            	if(reqMessage.header.equals("moveUp")){
            		if(currOrder == 0) {
            			respWriter.println(gson.toJson(new Message("No need to reorder"))); //no need to move the first item up
            			return;
            		}
            		else toSwapOrder = currOrder - 1;
            	}
            	else {
            		if(currOrder == list.size()) {
            			respWriter.println(gson.toJson(new Message("No need to reorder"))); //no need to move the last item up
            			return;
            		}
            		toSwapOrder = currOrder + 1;
            	}
            	int currIndexInList = 0; 
            	int toSwapIndexInList = 0;
            	for(int i = 0;i < list.size();i++) {
            		if(list.get(i).order == currOrder) {
            			currIndexInList = i;
            		}
            		else if(list.get(i).order == toSwapOrder) {
            			toSwapIndexInList = i;
            		}
            	}
                //Swap the order of the two items
            	System.out.println("Before: " + list.get(currIndexInList).name + ": " + list.get(currIndexInList).order);
            	System.out.println("Before: " + list.get(toSwapIndexInList).name + ": " + list.get(toSwapIndexInList).order);
            	list.get(currIndexInList).order = toSwapOrder;
            	list.get(toSwapIndexInList).order = currOrder;
            	System.out.println("After: " + list.get(currIndexInList).name + ": " + list.get(currIndexInList).order);
            	System.out.println("After: " + list.get(toSwapIndexInList).name + ": " + list.get(toSwapIndexInList).order);
            	OrderDataManager odm = new OrderDataManager(username);
            	odm.setOrder(list.get(currIndexInList));
            	odm.setOrder(list.get(toSwapIndexInList));
            	respWriter.println(gson.toJson(new Message("Success")));
            	return;
            }
            
            String infoJson = (String)reqListAndItem.body; //Get Info object of item to add/remove as a JSON string
            
            //Interact with the raw JSON to determine the type of the object via unique fields
            JsonObject info = new JsonParser().parse(infoJson).getAsJsonObject();
            Type infoType;
            if(info.has("prepTime")) infoType = RecipeInfo.class;
            else if(info.has("placeID")) infoType = RestaurantInfo.class;
            else if(info.has("item")) infoType = GroceryInfo.class;
            else throw new Exception("Unknown item type.");
            
            
        	Info item = gson.fromJson(infoJson, infoType); //Parse Info object from JSON
            List<Info> list = (List<Info>)session.getAttribute(listName); //Get the requested list from session
            //Switch on requested action
            RestaurantDataManager restaurantDB = new RestaurantDataManager(username);
        	RecipeDataManager recipeDB = new RecipeDataManager(username);
            switch(reqMessage.header)
            {
                case "addItem":
                	if(listName.equals("Grocery")) { //case for add to Grocery List
                    	GroceryDataManager groceryDB = new GroceryDataManager(username);
                    	RecipeInfo newItem = gson.fromJson(infoJson, infoType);
                    	ArrayList<String> ingredients = newItem.ingredients; 
                    	for(int i = 0; i < ingredients.size(); i++) {
                    		GroceryInfo newGrocery = new GroceryInfo(ingredients.get(i));
                    		boolean alreadyAdded = false;
                    		for(int j=0;j < list.size();j++) {
                    			GroceryInfo g = (GroceryInfo) list.get(j);
                    			if(g.item.equals(ingredients.get(i))) {
                    				alreadyAdded = true;
                    				break;
                    			}
                    		}
                    		if(!alreadyAdded) {
                    			list.add(newGrocery);
                    			groceryDB.addToList(newGrocery);
                    		}
                    	}

                    }
                    else {
                    	if(!list.contains(item)) {
                    		int maxOrder = 0;
                    		for(int i = 0; i < list.size(); i++) {
                    			int currentOrder = list.get(i).order;
                    			if(currentOrder > maxOrder) maxOrder = currentOrder;
                    		}
                    		item.order = maxOrder + 1;
                    		list.add(item);
                    		
                    		System.out.println("Current order in list: ");
                            for(int i = 0;i<list.size();i++) {
                            	System.out.println(list.get(i).name + ", " + list.get(i).order);
                            }
                            
                    		int listToAdd = 1;
                    		if(listName.equals("Favorites")) listToAdd = 1;
                    		else if(listName.equals("Do Not Show")) listToAdd = 2;
                    		else if(listName.equals("To Explore")) listToAdd = 3;
                    		if(infoType == RecipeInfo.class)
                    			recipeDB.addToList((RecipeInfo)item, listToAdd);
                    		else if(infoType == RestaurantInfo.class)
                    			restaurantDB.addToList((RestaurantInfo)item, listToAdd);
                    	}
                    }
                    respWriter.println(gson.toJson(new Message("Added to list "+ list)));
                    break;
                    
                case "removeItem":
                	if(listName.equals("Grocery")) { //case remove from Grocery List
                		list.remove(item);
                		GroceryInfo itemGroceryInfo = (GroceryInfo)item;
                    	GroceryDataManager groceryDB = new GroceryDataManager(username);
                    	groceryDB.removeFromList(itemGroceryInfo.item);
                    }
                	else {
                		int currOrder = item.order;
                        list.remove(item);
                        for(int i = 0;i<list.size();i++) {
                        	if(list.get(i).order > currOrder) {
                        		list.get(i).order -= 1; //the order of all the items that placed below the deleted one should be decremented by 1
                        	}
                        }
                        System.out.println("Current order in list: ");
                        for(int i = 0;i<list.size();i++) {
                        	System.out.println(list.get(i).name + ", " + list.get(i).order);
                        }
                        
                        int listToRemove = 1;
                		if(listName.equals("Favorites")) listToRemove = 1;
                		else if(listName.equals("Do Not Show")) listToRemove = 2;
                		else if(listName.equals("To Explore")) listToRemove = 3;
                		if(infoType == RecipeInfo.class) {
                			RecipeInfo itemRecipeInfo = (RecipeInfo)item;
                			recipeDB.removeFromList(itemRecipeInfo.spoonID, listToRemove);
                		}
                		else if(infoType == RestaurantInfo.class) {
                			RestaurantInfo itemRestaurantInfo = (RestaurantInfo)item;
                			restaurantDB.removeFromList(itemRestaurantInfo.placeID, listToRemove);
                		}
                        
                	}
                	respWriter.println(gson.toJson(new Message("Removed from list "+listName)));
                    break;
                    
                case "resetLists":
                    session.invalidate(); //Note: This is for debuggin only; the page will break if this is called and a new search is not immediately made
                    break;
                   
                default:
                    throw new Exception("Invalid action.");
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
        respWriter.close();
    }

}
