package DataLayer;

import DataLayer.Helpers.DoubleOrString;

import java.util.ArrayList;
import java.util.List;

public class Article
{
    private ArrayList<String> body;
    private String places;
    private String topic;
    //CECHY
    // moga byc w stringu i gdy sa potrzebne moga byc pardowane na double
    //0 - ilosc wystapien slow kluczowych
    //1 - liczba slow
    //2 - pierwsze słowo kluczowe wystepujace w tekscie(String)
    //3 - numer indexu w ktorym znajduje sie pierwsze znalezione slowo kluczowe
    private ArrayList<DoubleOrString> attributes = new ArrayList<>();

    public Article() {}



    public void addAttribute(DoubleOrString value)
    {
        //mozna dodac sprawdzenie indexu ew. jeszcze zmienic na boolean i sprawdzac poprawne dodanie gdzies indziej
         this.attributes.add(value);
    }

    public void showAttributes()
    {
        for(DoubleOrString d : attributes)
        {

                if (d.isStringValue()) System.out.print(d.getString() + " ");
                else if (d.isDoubleValue()) System.out.print(d.getDouble() + " ");

        }
        System.out.println(" ");
    }


    public void setAttribute(int index, Double value)
    {
        attributes.get(index).setDoubleValue(value);
    }

    public void setAttribute(int index, String value)
    {
        attributes.get(index).setStringValue(value);
    }
    public List<DoubleOrString> getAttributes()
    {
        return attributes;
    }

    public ArrayList<String> getBody()
    {
        return body;
    }

    public void setBody(ArrayList<String> body)
    {
        this.body = body;
    }

    public String getPlaces()
    {
        return places;
    }

    public void setPlaces(String places)
    {
        this.places = places;
    }

    public String getTopic()
    {
        return topic;
    }

    public void setTopic(String topic)
    {
        this.topic = topic;
    }
}
