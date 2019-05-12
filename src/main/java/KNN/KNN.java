package KNN;

import Extractors.Article;
import KNN.Metrics.Metric;

import java.util.List;

public class KNN
{
    //sasiedzi
    private final int k;
    //ilosc elementow z kazdego miejsca ma byc użytych do inicjalizacji przestrzeni
    private final int n;
    //sposob licznie odleglosci miedzy vectorami cech
    private final Metric metric;

    private final List<Article> trainingSet;
    private final List<Article> testSet;
    private List<Article> startSet;

    public KNN(int k, int n, Metric metric, List<Article> test, List<Article> training)
    {
        this.k = k;
        this.n = n;
        this.metric = metric;
        this.testSet = test;
        this.trainingSet = training;
    }

    private void setStartSet()
    {

    }





}
