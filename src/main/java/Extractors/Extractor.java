package Extractors;



import DAO.DATA_API;
import DataLayer.Article;
import Extractors.Helpers.CalculationsForExtractors;
import Extractors.Helpers.Normalization;

import java.util.*;

public class Extractor
{


    private final static int MAX_RANGE_OF_NORMALIZED_ATTRIBUTE = 1;
    private final static int MIN_RANGE_OF_NORMALIZED_ATTRIBUTE = 0;
    //private final static int AVAILABLE_EXTRACTORS_TO_USE = 4;

    //ELSE

    private final List<Integer> listOfIndexOfExtractorsToRun;
    private CalculationsForExtractors calculationsForExtractors;
    //Extractors repository
    ArrayList<IExtractors> allExtractors = new ArrayList<>();
    DATA_API data_api;
    Normalization normalization = new Normalization();




    public Extractor(List<Integer> listOfIndexOfExtractorsToRun, DATA_API data_api)
    {
        calculationsForExtractors= new CalculationsForExtractors(data_api);
        allExtractors.add(new OccurrenceOfKeyWords());
        this.data_api = data_api;
        this.listOfIndexOfExtractorsToRun = listOfIndexOfExtractorsToRun;
        allExtractors.addAll(Arrays.asList(new OccurrenceOfKeyWords(), new AmountOfWords(), new FirstKeyWord()));
    }

    //OPCJE

    public void run()
    {
        data_api.getTestSet().forEach(article ->
        {
            for(Integer i : listOfIndexOfExtractorsToRun)
            {
                allExtractors.get(i).extract(article, calculationsForExtractors);
            }
        });

        data_api.getTrainingSet().forEach(article ->
        {
            for(Integer i : listOfIndexOfExtractorsToRun)
            {
                allExtractors.get(i).extract(article, calculationsForExtractors);
            }
        });
    }


    public void normalizeVectors()
    {
        normalization.normalize(data_api.getTestSet());
        normalization.normalize(data_api.getTrainingSet());
    }

}
