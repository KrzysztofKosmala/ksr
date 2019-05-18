package Manage;

import Extractors.DATA_API;
import Extractors.Extractor;
import KNN.KNN;
import KNN.Metrics.Euclidean;

public class Manager
{
    private Extractor extractorForSinglePlacesNameTrainingSet = new Extractor(DATA_API.getTrainingSet(), DATA_API.getKeyWords());
    private Extractor extractorForSinglePlacesNameTestSet = new Extractor(DATA_API.getTestSet(), DATA_API.getKeyWords());

    public void extractAttributes()
    {
        extractorForSinglePlacesNameTestSet.run();
        extractorForSinglePlacesNameTrainingSet.run();
    }

    public void normalizeAttributes()
    {
        extractorForSinglePlacesNameTrainingSet.normalizeVectors();
        extractorForSinglePlacesNameTestSet.normalizeVectors();
    }

    public void runKNN()
    {
        KNN knn = new KNN(5,100,new Euclidean(),DATA_API.getTestSet(), DATA_API.getTrainingSet());
        knn.show();
    }

}
