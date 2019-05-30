package Extractors;

import DAO.DATA_API;
import DataLayer.Article;
import Extractors.Helpers.CalculationsForExtractors;
import DataLayer.Helpers.DoubleOrString;

import java.util.ArrayList;

public class FirstKeyWord implements IExtractors
{



    @Override
    public void extract(Article article, CalculationsForExtractors calculationsForExtractors)
    {
        boolean helper = true;
        for(String word : article.getBody())
        {
            if(helper)
                for (ArrayList<String> allKeyWords : calculationsForExtractors.getKeyWords().values())
                {
                    if(helper)
                        for (String keyWord : allKeyWords)
                        {
                            if(calculationsForExtractors.calculateJaccardSimilarity(word, keyWord)>0.8)
                            {
                                article.addAttribute(new DoubleOrString(keyWord));

                                helper = false;
                                break;
                            }
                        }
                }
        }
        if(helper)article.addAttribute(new DoubleOrString(""));
    }
}
