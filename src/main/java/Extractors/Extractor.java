package Extractors;



import DataLayer.Article;
import Extractors.Helpers.Normalization;

import java.util.*;

public class Extractor
{


    private final static int MAX_RANGE_OF_NORMALIZED_ATTRIBUTE = 1;
    private final static int MIN_RANGE_OF_NORMALIZED_ATTRIBUTE = 0;
    //private final static int AVAILABLE_EXTRACTORS_TO_USE = 4;

    //ELSE
    private final List<Article> articlesList;
    private final List<Integer> listOfIndexOfExtractorsToRun;

    //Extractors repository
    ArrayList<IExtractors> allExtractors = new ArrayList<>();

    Normalization normalization = new Normalization();




    public Extractor(List<Article> articles, List<Integer> listOfIndexOfExtractorsToRun)
    {
        allExtractors.add(new OccurrenceOfKeyWords());
        this.articlesList = articles;
        this.listOfIndexOfExtractorsToRun = listOfIndexOfExtractorsToRun;
        allExtractors.addAll(Arrays.asList(new OccurrenceOfKeyWords(), new AmountOfWords(), new FirstKeyWord()));
    }

    //OPCJE

    public void run()
    {
        articlesList.forEach(article ->
        {
            for(Integer i : listOfIndexOfExtractorsToRun)
            {
                allExtractors.get(i).extract(article);
            }
        });
    }


    public void normalizeVectors()
    {
        normalization.normalize(articlesList);
    }

}
