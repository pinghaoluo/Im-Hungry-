package servlet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import database_manager.GroceryDataManager;
import database_manager.RecipeDataManager;
import database_manager.RestaurantDataManager;
import info.*;

import info.History;
import message.Message;
import message.SearchResult;

import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Servlet implementation class SearchResult
 */
@WebServlet(name = "SearchServlet", urlPatterns = "/Search")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String GOOGLE_MAPS_API_PREFIX = "https://maps.googleapis.com/maps/api";
	private static String MAPS_API_KEY = "";
	private static final String TOMMY_TROJAN_LOC = "34.0205663,-118.2876355";

	private static final String SPOONACULAR_RECIPE_API_PREFIX = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes";
	private static String SPOONACULAR_RAPID_API_KEY = "";

	private static String GOOGLE_CX_API_KEY = "";
	private static final String GOOGLE_CX_ENGINE = "006259848917093276777:-embwltvk-s";
	private static final int IMAGE_COLLAGE_NUM = 10;


	public SearchServlet() {
		if(MAPS_API_KEY.equals("") || SPOONACULAR_RAPID_API_KEY.equals("")) {
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader("constants.txt"));
				MAPS_API_KEY = reader.readLine();
				SPOONACULAR_RAPID_API_KEY = reader.readLine();
				GOOGLE_CX_API_KEY = MAPS_API_KEY;
				reader.close();
			}
			catch (Exception e) {}
		}
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		ArrayList<Info> favoritesList, doNotShowList, toExploreList;
		ArrayList<Info> groceryList;

		String username = (String)session.getAttribute("username");  
		
		RestaurantDataManager restaurantDB = new RestaurantDataManager(username);
		RecipeDataManager recipeDB = new RecipeDataManager(username);
		GroceryDataManager groceryDB = new GroceryDataManager(username);
		if(session.isNew() || session.getAttribute("Favorites") == null) {
			favoritesList = new ArrayList<>();
			favoritesList.addAll(restaurantDB.loadRestaurants(1));
			favoritesList.addAll(recipeDB.loadRecipes(1));
			doNotShowList = new ArrayList<>();
			doNotShowList.addAll(restaurantDB.loadRestaurants(2));
			doNotShowList.addAll(recipeDB.loadRecipes(2));
			toExploreList = new ArrayList<>();
			toExploreList.addAll(restaurantDB.loadRestaurants(3));
			toExploreList.addAll(recipeDB.loadRecipes(3));
			groceryList = groceryDB.loadGrocery();

			session.setAttribute("Favorites", favoritesList);
			session.setAttribute("To Explore", toExploreList);
			session.setAttribute("Do Not Show", doNotShowList);
			session.setAttribute("Grocery", groceryList);
		}
		else {
			
			favoritesList = (ArrayList<Info>) session.getAttribute("Favorites");
			toExploreList = (ArrayList<Info>) session.getAttribute("To Explore");
			doNotShowList = (ArrayList<Info>) session.getAttribute("Do Not Show");
			groceryList = (ArrayList<Info>) session.getAttribute("Grocery");

		}

        //From previous page, extract parameters
        String userSearch = request.getParameter("search");
        int numResults = Integer.parseInt(request.getParameter("number"));
        int radius = Integer.parseInt(request.getParameter("radius"));  //radius in meters

        PrintWriter out = response.getWriter();

        //Set up variables to store return value
        boolean success = true;
        String errorMsg = "";

        //get lists
        ArrayList<RecipeInfo> recipeList = recipeSearch(userSearch, numResults, doNotShowList, favoritesList);
        ArrayList<RestaurantInfo> restaurantList = restaurantSearch(userSearch, numResults, radius, doNotShowList, favoritesList);
        
        //A Hash-map to store all the urls from the quick access list
        ArrayList<ArrayList<String>> urlMaps = new ArrayList<ArrayList<String>>();
        
        List<History> historyList = (List<History>)session.getAttribute("Quick Access");
        
        //Get the url lists from the prior search term
        for(int i = 0;i<historyList.size();i++) {
        	String searchQuery = historyList.get(i).query;
        	searchQuery = searchQuery.replaceAll("\"", "");
        	
        	ArrayList<String> urlList = getImageURLs(searchQuery);
        	urlMaps.add(urlList);
        }
        
        //ArrayList<String> urlList = getImageURLs(userSearch);

        //return content
        if (!success){
            //create error message
            out.println(errorMsg);

        } else {
            //create success message
            List<Info> castedRecipeList = (ArrayList<Info>)(Object)recipeList;
            List<Info> castedRestaurantList = (ArrayList<Info>)(Object)restaurantList;
            //Stick them into a 2D array
            List<List<Info>> results = new ArrayList<>();
            results.add(castedRestaurantList);
            results.add(castedRecipeList);
            //Put together the Search Result and Message object, convert to Json, and reply.
            out.println(new Gson().toJson(new Message("Success",new SearchResult(results, urlMaps))));
        }
		out.close();
    }

	//Sends a "GET" request to the specified API URL and obtains the result as a String.
	public static String getJSONResponse(String url) {
		try {
			URL requestURL = new URL(url);
			HttpURLConnection con = (HttpURLConnection) requestURL.openConnection();
			//HTTP header for authorization of Spoonacular recipe API
			con.setRequestProperty("X-RapidAPI-Key", SPOONACULAR_RAPID_API_KEY);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			//use StringBuffer to read JSON response line by line
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			return response.toString();
		} catch(Exception e) {}
		return null;
	}

	//Given a String query and number of results expected, return an ArrayList of RecipeInfo. The function
	//uses 2 Spoonacular's recipe APIs and refer to Do Not Show List and Favorites List.
	public ArrayList<RecipeInfo> recipeSearch(String query, int numResults, List<Info> doNotShowList, List<Info> favoritesList){
		ArrayList<RecipeInfo> recipes = new ArrayList<RecipeInfo>();
		//assuming the worst possible case (all items in Do Not Show List appear in result) to query sufficient
		//amount of recipe information
		String recipeSearchURL = SPOONACULAR_RECIPE_API_PREFIX + "/search?number="
				+ (numResults + doNotShowList.size()) + "&query=" + query.replaceAll("\\s+","%20");
		//extract relevant part of the JSON response
		JsonArray recipesJSON = new JsonParser().parse(getJSONResponse(recipeSearchURL)).getAsJsonObject()
				.get("results").getAsJsonArray();

		for(int i = 0 ; i < recipesJSON.size(); i++){
			JsonObject currentRecipe = recipesJSON.get(i).getAsJsonObject();
			//initialize current RecipeInfo object that will be added. Detail information requires another
			//request based on the recipe's unique recipe ID
			RecipeInfo recipe = new RecipeInfo(currentRecipe.get("title").getAsString(), 0,
					currentRecipe.get("id").getAsInt(), 30, 30, new ArrayList<String>(), new ArrayList<String>(), "");

			//use recipe ID to make another request for detail information
			String recipeDetailURL = SPOONACULAR_RECIPE_API_PREFIX + "/" + recipe.spoonID +"/information";
			JsonObject recipeDetailJSON;
			//very occasionally, recipe does not have detail information and is skipped
			try {
				recipeDetailJSON = new JsonParser().parse(getJSONResponse(recipeDetailURL)).getAsJsonObject();
			} catch(NullPointerException e) {
				continue;
			}
			//Spoonacular Score is a score out of 100
			recipe.rating = (int)(recipeDetailJSON.get("spoonacularScore").getAsDouble() / 100 * 5);

			try {
				//not all recipes have preparation time data
				recipe.prepTime = recipeDetailJSON.get("preparationMinutes").getAsInt();
			} catch(Exception e) {}

			try {
				//not all recipes have cook time data
				recipe.cookTime = recipeDetailJSON.get("cookingMinutes").getAsInt();
			} catch(Exception e) {}

			JsonArray ingredientsJSON = recipeDetailJSON.get("extendedIngredients").getAsJsonArray();
			for(int j = 0; j < ingredientsJSON.size(); j++) {
				recipe.ingredients.add(ingredientsJSON.get(j).getAsJsonObject()
						.get("name").getAsString());
			}

			//Most recipe data include instructions divided into steps. When the field "analyzedInstructions"
			//does not exist, try to obtain "steps" which is one string with all instructions.
			JsonArray analyzedInstructions = recipeDetailJSON.get("analyzedInstructions").getAsJsonArray();
            JsonArray instructionsJSON;
			if(analyzedInstructions.size() > 0) {
			    instructionsJSON = analyzedInstructions.get(0).getAsJsonObject().get("steps").getAsJsonArray();
                for(int j = 0; j < instructionsJSON.size(); j++) {
                    recipe.instructions.add("" + (j + 1) + ". " + instructionsJSON.get(j).getAsJsonObject()
                            .get("step").getAsString());
                }
            }
			else if (!recipeDetailJSON.get("instructions").toString().equals("null")) {
			    recipe.instructions.add(recipeDetailJSON.get("instructions").getAsString());
            }
            else {
			    recipe.instructions.add("Instructions weren't found for this recipe, sorry!");
            }

			try {
				recipe.imageURL = recipeDetailJSON.get("image").getAsString();
			} catch(Exception e) {
				recipe.imageURL = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6c/No_image_3x4.svg/1024px-No_image_3x4.svg.png";
			}
			recipes.add(recipe);
		}
		//remove all items in Do Not Show List that appear in the result
		for(Info doNotShowInfo : doNotShowList) {
			recipes.remove(doNotShowInfo);
		}

		Collections.sort(recipes);  //sort RecipeInfo in ascending order based on preparation time

		//move recipes in Favorites List to the top
		for(int i = recipes.size() - 1; i > 0; i--) {
			if(favoritesList.contains(recipes.get(i))) {
				recipes.add(0, recipes.get(i));
				i++;
				recipes.remove(i);
			}
		}
		
		//remove extra items
		while(recipes.size() > numResults) {
			recipes.remove(recipes.size() - 1);
		}

		return recipes;
	}


	//Given a String query and number of results expected, return an ArrayList of RestaurantInfo. The function
	//uses several Google Maps APIs and refer to Do Not Show List and Favorites List.
	public ArrayList<RestaurantInfo> restaurantSearch(String query, int numResults, int radius, List<Info> doNotShowList, List<Info> favoritesList) {
		ArrayList<RestaurantInfo> restaurants = new ArrayList<RestaurantInfo>();
		String searchURL = GOOGLE_MAPS_API_PREFIX + "/place/nearbysearch/json?location=" + TOMMY_TROJAN_LOC
				+"&radius=" + (radius * 1609) + "&type=restaurant&keyword=" + query.replaceAll("\\s+","%20") + "&key="
				+ MAPS_API_KEY;
		//extract relevant part of the JSON response
		JsonArray places = new JsonParser().parse(getJSONResponse(searchURL)).getAsJsonObject()
				.get("results").getAsJsonArray();

		//assuming the worst possible case (all items in Do Not Show List appear) to encapsulate sufficient
		//amount of restaurant information from the response
		for(int i = 0; i < numResults + doNotShowList.size(); i++) {
			if(i >= places.size()) break;
			JsonObject currentPlace = places.get(i).getAsJsonObject();
			int priceLevel = 0;
			try {
				priceLevel = currentPlace.get("price_level").getAsInt();
			} catch(Exception e) {}
			restaurants.add(new RestaurantInfo(currentPlace.get("name").getAsString(),
					(int)currentPlace.get("rating").getAsDouble(), currentPlace.get("place_id").getAsString(),
					currentPlace.get("vicinity").getAsString(), priceLevel, "No Drive Time Available", 0, "No phone number available", "No website available"));
		}

		//remove all items in Do Not Show List that appear in the result
		for(Info doNotShowInfo : doNotShowList) {
			restaurants.remove(doNotShowInfo);
		}
		
		getDriveTimes(restaurants);
		getPhoneAndURL(restaurants);

		Collections.sort(restaurants);  //sort RestaurantInfo in ascending order based on drive time

		//move restaurants in Favorites List to the top
		for(int i = restaurants.size() - 1; i > 0; i--) {
			if(favoritesList.contains(restaurants.get(i))) {
				restaurants.add(0, restaurants.get(i));
				i++;
				restaurants.remove(i);
			}
		}

		//remove extra items
		while(restaurants.size() > numResults) {
			restaurants.remove(restaurants.size() - 1);
		}
		
    	return restaurants;
	}

	//Create a request using place_id of all RestaurantInfo and send one request to obtain all drive times.
	public void getDriveTimes(ArrayList<RestaurantInfo> restaurants) {
		if(restaurants.isEmpty()) return;
		String driveTimeURL = GOOGLE_MAPS_API_PREFIX + "/distancematrix/json?units=imperial&origins="
				+ TOMMY_TROJAN_LOC + "&destinations=";
		//concatenate the request URL to make use of the Distance Matrix API, obtaining drive times of multiple
		//destinations in one request
		for(int i = 0; i < restaurants.size(); i++) {
			driveTimeURL += "place_id:" + restaurants.get(i).placeID + "%7C";
		}
		driveTimeURL += "&key=" + MAPS_API_KEY;

		//extract relevant part of the JSON response
		JsonArray driveTimes = new JsonParser().parse(getJSONResponse(driveTimeURL)).getAsJsonObject().get("rows").getAsJsonArray().get(0).getAsJsonObject().get("elements").getAsJsonArray();
		//modify respective RestaurantInfo objects, store drive time data
		for(int i = 0; i < restaurants.size(); i++) {
			JsonObject durationJSON = driveTimes.get(i).getAsJsonObject().get("duration").getAsJsonObject();
			restaurants.get(i).driveTimeText = durationJSON.get("text").getAsString();
			restaurants.get(i).driveTimeValue = durationJSON.get("value").getAsInt();
		}
	}

	//A separate request is needed to get detailed information including phone and URL.
	public void getPhoneAndURL(ArrayList<RestaurantInfo> restaurants) {
		for(RestaurantInfo restaurant : restaurants) {
			String detailURL = GOOGLE_MAPS_API_PREFIX + "/place/details/json?placeid="
					+ restaurant.placeID + "&fields=formatted_phone_number,website&key=" + MAPS_API_KEY;
			//extract main body of the JSON response
			JsonObject detailsJSON = new JsonParser().parse(getJSONResponse(detailURL)).getAsJsonObject().get("result").getAsJsonObject();
			//modify each RestaurantInfo objects, store phone number and URL
			try {
				restaurant.phone = detailsJSON.get("formatted_phone_number").getAsString();
				restaurant.url = detailsJSON.get("website").getAsString();
			} catch(Exception e) {}
		}
	}

	//Given a String query, return an ArrayList of String storing URLs for images to present in the collage. The
	//function uses Google Custom Search API. The search engine is configured to search for images.
	public ArrayList<String> getImageURLs(String query){
		ArrayList<String> images = new ArrayList<String>();

		String imageSearchURL = "https://www.googleapis.com/customsearch/v1?key=" + GOOGLE_CX_API_KEY
				+ "&num=" + IMAGE_COLLAGE_NUM + "&cx=" + GOOGLE_CX_ENGINE + "&q=food%20" + query.replaceAll("\\s+","%20")
				+ "&alt=json&searchType=image";
		//extract relevant of the JSON response
		JsonArray imagesJSON;
		try {
			imagesJSON = new JsonParser().parse(getJSONResponse(imageSearchURL)).getAsJsonObject()
					.get("items").getAsJsonArray();
		} catch(Exception e) {
			return images;
		}
		//store URL of this image to the list
		for(int i = 0; i < imagesJSON.size(); i++) {
			images.add(imagesJSON.get(i).getAsJsonObject().get("link").getAsString());
		}

		return images;
	}

}
