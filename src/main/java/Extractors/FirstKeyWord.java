package Extractors;

import DAO.DATA_API;
import DataLayer.Article;
import Extractors.Helpers.CalculationsForExtractors;
import DataLayer.Helpers.DoubleOrString;

import java.util.ArrayList;
import java.util.List;

public class FirstKeyWord implements IExtractors
{



    @Override
    public void extract(Article article, CalculationsForExtractors calculationsForExtractors)
    {
        boolean helper = true;
        for(String word : article.getBody())
        {

                    if(helper)
                        for(String keyWord : calculationsForExtractors.getKeyWords())
                        {
                            if(calculationsForExtractors.calculateJaccardSimilarity(word, keyWord)>0.8)
                            {
                                article.addAttribute(new DoubleOrString(keyWord));

                                helper = false;
                                break;
                            }
                        }

        }
        if(helper)article.addAttribute(new DoubleOrString(""));
    }
}
