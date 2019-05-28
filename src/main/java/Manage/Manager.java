package Manage;

import DAO.DATA_API;
import Extractors.Extractor;
import KNN.KNN;
import KNN.Metrics.Euclidean;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Manager
{
    private int[] extractors = { 1, 2, 3};
    private int[] metrics = { 1, 2, 3};

    private Extractor extractorForTrainingSet = new Extractor(DATA_API.getTrainingSet(), Arrays.stream(extractors).boxed().collect(Collectors.toList()));
    private Extractor extractorForTestSet = new Extractor(DATA_API.getTestSet(), Arrays.stream(extractors).boxed().collect(Collectors.toList()));

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

    public void runKNN()
    {
        KNN knn = new KNN(5,new Euclidean(),DATA_API.getTestSet(), DATA_API.getTrainingSet(), DATA_API.getTags());
        knn.show();
    }

}
