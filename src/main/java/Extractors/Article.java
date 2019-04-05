package Extractors;

import java.util.ArrayList;

public class Article
{
    private ArrayList<String> body;
    private ArrayList<String> places;
    private ArrayList<String> title;

    public Article() {}

    public ArrayList<String> getBody()
    {
        return body;
    }

    public void setBody(ArrayList<String> body)
    {
        this.body = body;
    }

    public ArrayList<String> getPlaces()
    {
        return places;
    }

    public void setPlaces(ArrayList<String> places)
    {
        this.places = places;
    }

    public ArrayList<String> getTitle()
    {
        return title;
    }

    public void setTopics(ArrayList<String> title)
    {
        this.title = title;
    }
}
