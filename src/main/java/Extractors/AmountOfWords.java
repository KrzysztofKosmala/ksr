package Extractors;

import DataLayer.Article;
import DataLayer.Helpers.DoubleOrString;
import Extractors.Helpers.CalculationsForExtractors;

public class AmountOfWords implements IExtractors
{

    @Override
    public void extract(Article article, CalculationsForExtractors calculationsForExtractors)
    {
        double value = (double)article.getBody().size();
        //jakies zabezpieczenie
        article.addAttribute(new DoubleOrString(value));
    }


}
