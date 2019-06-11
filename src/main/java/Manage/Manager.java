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

    private DATA_API dataApi;
    private KNN knn;
    private Extractor extractor;

    private String nameOfTheNodeWhichWillBeClassifier;
    private List<String> allowedStringsInClassifierNode;


    public void setupData(int percentOfTrainingSet, String nameOfTheNode, List<String> allowedStrings, boolean generateKeyWords, boolean generateStopList, List<Integer> listOfIndexOfExtractorsToRun, int amountOfKeyWords)
    {
        DATA tempCalculations = new DATA(percentOfTrainingSet, nameOfTheNode, allowedStrings, generateKeyWords, generateStopList, amountOfKeyWords);
        dataApi = new DATA_API(tempCalculations.getTrainingSet(),tempCalculations.getTestSet(),tempCalculations.getStopList(),tempCalculations.getListOfKeyWords());
        nameOfTheNodeWhichWillBeClassifier=nameOfTheNode;
        allowedStringsInClassifierNode=allowedStrings;

        extractor = new Extractor(listOfIndexOfExtractorsToRun, dataApi);

    }

    public DATA_API getDataApi()
    {
        return dataApi;
    }

    public void extractAttributes()
    {
        extractor.run();

    }

    public void normalizeAttributes()
    {
        extractor.normalizeVectors();
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
