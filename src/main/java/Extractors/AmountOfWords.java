package Extractors;

import DataLayer.Article;
import DataLayer.Helpers.DoubleOrString;

public class AmountOfWords implements IExtractors
{

    @Override
    public void extract(Article article)
    {
        double value = (double)article.getBody().size();
        //jakies zabezpieczenie
        article.addAttribute(new DoubleOrString(value));
    }


}
