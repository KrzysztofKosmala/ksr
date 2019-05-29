package KNN;

import DataLayer.Article;
import KNN.Metrics.Helpers.StringAndDouble;
import KNN.Metrics.IMetric;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class KNN
{
    //sasiedzi
    private final int k;

    //sposob licznie odleglosci miedzy vectorami cech
    private final IMetric metric;

    private final List<Article> trainingSet;
    private final List<Article> testSet;
    private final List<String> tags;
    private final String nameOfTheNode;


    public KNN(int k, IMetric metric, List<Article> test, List<Article> training, String nameOfTheNode, List<String> tags)
    {
        this.k = k;
        this.metric = metric;
        this.testSet = test;
        this.trainingSet = training;
        this.tags = tags;
        this.nameOfTheNode = nameOfTheNode;
    }

 /*   private void setStartSet()
    {
        HashMap<String,Integer> countedTags = new HashMap<>();
        for(String tag : tags)
        {
            int i =0;
            for(Article article : trainingSet)
            {
                if(tag.equals(article.getPlaces()))
                    i++;
            }
            countedTags.put(tag,i);
            *//*startSet.add(trainingSet.get(ThreadLocalRandom.current().nextInt(0, trainingSet.size() + 1)));*//*
        }

        int minValue =  Collections.min(countedTags.entrySet(), Comparator.comparingInt(Entry::getValue)).getValue();

        int n,i;
        Article article;
        for(String tag : tags)
        {
            i=0;
            while(i<minValue)
            {
                n = ThreadLocalRandom.current().nextInt(0, trainingSet.size() + 1);
                article=trainingSet.get(n);

                if(tag.equals(article.getPlaces()))
                {
                    startSet.add(article);
                    i++;
                }
            }
        }


    }*/

    public void run()
    {
        if(nameOfTheNode.equals("PLACES"))
        {

            int i=0;
            for(Article article : trainingSet)
            {

                if(predict(article).equals(article.getPlaces()))
                    i++;

            }System.out.println("Poprawynych rozpoznan: "+ ((i*100)/testSet.size()) +"%");

        }else if(nameOfTheNode.equals("TOPICS"))
        {

            int i=0;
            for(Article article : trainingSet)
            {

                if(predict(article).equals(article.getTopic()))
                    i++;

            }System.out.println("Poprawynych rozpoznan: "+ ((i*100)/testSet.size()) +"%");

        }
    }

    private String predict(Article articleToPredict)
    {
        PriorityQueue<StringAndDouble> neighbours = new PriorityQueue<>();
        if(nameOfTheNode.equals("PLACES"))
        {
            for(Article articleInTrainingSet : trainingSet)
            {
                neighbours.add(new StringAndDouble(articleInTrainingSet.getPlaces(), metric.countDistance(articleToPredict.getAttributes(), articleInTrainingSet.getAttributes())));
            }
        }else if(nameOfTheNode.equals("TOPICS"))
            {
                for(Article articleInTrainingSet : trainingSet)
                {
                    neighbours.add(new StringAndDouble(articleInTrainingSet.getTopic(), metric.countDistance(articleToPredict.getAttributes(), articleInTrainingSet.getAttributes())));
                }
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
                .max(Comparator.comparing(Entry::getValue))
                .get()
                .getKey();
    }
}
