package Manage;

import DAO.DATA_API;
import DataLayer.Article;
import DataLayer.DATA;
import Extractors.Extractor;
import KNN.KNN;
import KNN.Metrics.Euclidean;
import KNN.Metrics.IMetric;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Manager
{
    private int[] extractors = { 0, 1, 2};
    private DATA_API dataApi;
    private KNN knn;
    private Extractor extractorForTrainingSet;
    private Extractor extractorForTestSet;
    private String nameOfTheNodeWhichWillBeClassifier;
    private List<String> allowedStringsInClassifierNode;


    public void setupData(int percentOfTrainingSet, String nameOfTheNode, List<String> allowedStrings, boolean generateKeyWords, boolean generateStopList)
    {
        dataApi = new DATA_API(new DATA(percentOfTrainingSet, nameOfTheNode, allowedStrings, generateKeyWords, generateStopList));
        nameOfTheNodeWhichWillBeClassifier=nameOfTheNode;
        allowedStringsInClassifierNode=allowedStrings;
        extractorForTestSet = new Extractor(dataApi.getTestSet(), Arrays.stream(extractors).boxed().collect(Collectors.toList()));
        extractorForTrainingSet = new Extractor(dataApi.getTrainingSet(), Arrays.stream(extractors).boxed().collect(Collectors.toList()));
    }

    public void extractAttributes()
    {
        extractorForTestSet.run();
        extractorForTrainingSet.run();
    }

    public void normalizeAttributes()
    {
        extractorForTrainingSet.normalizeVectors();
        extractorForTestSet.normalizeVectors();
    }

    public void setupKNN(int k, IMetric metric)
    {
        knn = new KNN(k, metric, dataApi.getTestSet(), dataApi.getTrainingSet(), nameOfTheNodeWhichWillBeClassifier, allowedStringsInClassifierNode);
    }

    public void runKNN()
    {
        knn.run();
    }

}
