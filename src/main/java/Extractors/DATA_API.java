package Extractors;

import java.util.ArrayList;
import java.util.HashMap;

public final class DATA_API
{

    private static final DATA DATA = new DATA(60);


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

    public static ArrayList<Article> getSingleNameTrainingSet()
    {
        return DATA.getSingleNameTrainingSet();
    }

    public static ArrayList<Article> getSingleNameTestSet()
    {
        return DATA.getSingleNameTestSet();
    }

    public static ArrayList<String> getStopList()
    {
        return DATA.getGeneratedStopList();
    }

}
