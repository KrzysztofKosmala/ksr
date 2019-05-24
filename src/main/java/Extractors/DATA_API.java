package Extractors;

import Extractors.Helpers.DATA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class DATA_API
{

    private static final Extractors.Helpers.DATA DATA = new DATA(60);


    public static ArrayList<Article> getAllRareArticles()
    {
       return DATA.getAllArticles();
    }

    public static ArrayList<Article> getAllPreparedArticles()
    {
        return DATA.prepareArticles(DATA.getAllArticles());
    }

    public static HashMap<String, ArrayList<String>> getKeyWords()
    {
            return DATA.getKeyWords();
    }

    public static ArrayList<Article> getTrainingSet()
    {
        return DATA.getTrainingSet();
    }

    public static ArrayList<Article> getTestSet()
    {
        return DATA.getTestSet();
    }
    public static List<String> getTags()
    {
        return DATA.getTags();
    }

    public static ArrayList<String> getStopList()
    {
        return DATA.getGeneratedStopList();
    }

}
