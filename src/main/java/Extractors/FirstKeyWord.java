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
       article.addAttribute( new DoubleOrString(calculationsForExtractors.findFirstKeyWord(article.getBody())) );
    }
}
