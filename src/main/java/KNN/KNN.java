package KNN;

import Extractors.Article;
import KNN.Metrics.Metric;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class KNN
{
    //sasiedzi
    private final int k;

    //sposob licznie odleglosci miedzy vectorami cech
    private final Metric metric;

    private final List<Article> trainingSet;
    private final List<Article> testSet;
    private List<Article> startSet = new ArrayList<>();

    public KNN(int k, int sizeOfStartSet, Metric metric, List<Article> test, List<Article> training)
    {
        this.k = k;
        this.metric = metric;
        this.testSet = test;
        this.trainingSet = training;
        /*setStartSet(sizeOfStartSet);*/
    }

/*    private void setStartSet(int sizeOfStartSet)
    {
        for(int i=0;i <sizeOfStartSet;i++)
        {

            startSet.add(trainingSet.get(ThreadLocalRandom.current().nextInt(0, trainingSet.size() + 1)));
        }

    }*/

    public void show()
    {
        double percent=0;
        int i=0;
        for(Article article : testSet)
        {

            if(predictPlace(article).equals(article.getPlaces()))
                i++;

        }System.out.println("Poprawynych rozpoznan: "+ ((i*100)/testSet.size()) +"%");
    }


    private String predictPlace(Article articleToPredict)
    {
        PriorityQueue<HelperForKNN> neighbours = new PriorityQueue<>();
        for(Article articleInStartSet : trainingSet)
        {
            neighbours.add(new HelperForKNN(articleInStartSet.getPlaces(),metric.countDistance(articleToPredict.getAttributes(), articleInStartSet.getAttributes())));
        }

        List<String> result = new ArrayList<>();
        for(int i =0; i<k; i++)
        {
            result.add(neighbours.poll().getStringValue());
        }

        return result.stream()
                .collect(Collectors.groupingBy(w -> w, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .get()
                .getKey();
    }
}
