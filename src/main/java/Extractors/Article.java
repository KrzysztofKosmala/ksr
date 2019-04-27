package Extractors;

import java.util.ArrayList;

public class Article
{
    private ArrayList<String> body;
    private ArrayList<String> places;
    private ArrayList<String> title;

    //0 - ilosc wystapien slowa kluczowego
    //1 - liczba slow
    //2 -
    private double[] attributes = new double[2];
    public Article() {}



    public void addAttribute(int index, double value)
    {
        //mozna dodac sprawdzenie indexu ew. jeszcze zmienic na boolean i sprawdzac poprawne dodanie gdzies indziej
         this.attributes[index] = value;
    }

    public void showAttributes()
    {
        for(double d : attributes)
        {
            System.out.print(d + " ");
        }
        System.out.println(" ");
    }

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
