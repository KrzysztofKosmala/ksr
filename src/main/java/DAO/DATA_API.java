package DAO;

import DataLayer.DATA;
import DataLayer.Article;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class DATA_API
{

    private final DATA preparedData;

    public DATA_API(DATA preparedData)
    {
        this.preparedData = preparedData;
    }

    public  HashMap<String, ArrayList<String>> getKeyWords()
    {
            return preparedData.getKeyWords();
    }

    public  ArrayList<Article> getTrainingSet()
    {
        return preparedData.getTrainingSet();
    }

    public  ArrayList<Article> getTestSet()
    {
        return preparedData.getTestSet();
    }

    public  List<String> getTags()
    {
        return preparedData.getAllowedStringsInClassifierNode();
    }

    public  ArrayList<String> getStopList()
    {
        return preparedData.getStopList();
    }

}
