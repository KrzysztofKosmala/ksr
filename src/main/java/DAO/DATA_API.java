package DAO;

import DataLayer.DATA;
import DataLayer.Article;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class DATA_API
{
    List<Article> trainingSet;
    List<Article> testSet;
    ArrayList<String> stopList;
    HashMap<String, ArrayList<String>> keyWords;
    public DATA_API(List<Article> trainingSet, List<Article> testSet, ArrayList<String> stopList, HashMap<String, ArrayList<String>> keyWords)
    {
        this.trainingSet=trainingSet;
        this.testSet=testSet;
        this.stopList=stopList;
        this.keyWords=keyWords;
    }

    public  HashMap<String, ArrayList<String>> getKeyWords()
    {
            return keyWords;
    }

    public  List<Article> getTrainingSet()
    {
        return trainingSet;
    }

    public  List<Article> getTestSet()
    {
        return testSet;
    }

    public  ArrayList<String> getStopList()
    {
        return stopList;
    }

}
