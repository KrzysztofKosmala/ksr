package Manage;

import Extractors.DATA_API;
import Extractors.Extractor;

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


}
