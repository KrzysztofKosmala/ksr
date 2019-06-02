package Extractors;

import DataLayer.Article;
import DataLayer.Helpers.DoubleOrString;
import Extractors.Helpers.CalculationsForExtractors;

public class FrequencyOfKeyWords implements IExtractors
{
    @Override
    public void extract(Article article, CalculationsForExtractors calculationsForExtractors)
    {
        article.addAttribute(new DoubleOrString( calculationsForExtractors.countFrequencyOfKeyWords(article.getBody())) );
    }
}
