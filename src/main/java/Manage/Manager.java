package Manage;

import Extractors.Article;
import Extractors.DATA_API;
import Extractors.Extractor;

import java.util.List;

public class Manager
{
    Extractor extractorForSinglePlacesNameTrainingSet = new Extractor(DATA_API.getSingleNameTrainingSet(), DATA_API.getKeyWords());
    Extractor extractorForSinglePlacesNameTestSet = new Extractor(DATA_API.getSingleNameTestSet(), DATA_API.getKeyWords());

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
