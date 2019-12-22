package message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import info.Info;

//A simple class to tie together the two responses to a search: the results themselves and the list of URLs for the collage
public class SearchResult implements Serializable
{
    public List<List<Info>> results;
    public ArrayList<ArrayList<String>> imageURLs;

    public SearchResult(List<List<Info>> results, ArrayList<ArrayList<String>> imageURLs)
    {
        this.results = results;
        this.imageURLs = imageURLs;
    }
}
