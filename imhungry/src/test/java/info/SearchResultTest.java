package info;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import message.SearchResult;

public class SearchResultTest {

	@Test
	//testing with a random string
	public void twoParamInstantiation1() { 
		List<List<Info>> results = new ArrayList<List<Info>>();
		ArrayList<ArrayList<String>> imageURLs = new ArrayList<ArrayList<String>>();
		SearchResult sr = new SearchResult(results,imageURLs);
		assertEquals(results,sr.results);
		assertEquals(imageURLs,sr.imageURLs);
	}

}
