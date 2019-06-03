package Extractors;

import DataLayer.Article;
import DataLayer.Helpers.DoubleOrString;
import Extractors.Helpers.CalculationsForExtractors;

public class AllKeyWords implements IExtractors
{

    @Override
    public void extract(Article article, CalculationsForExtractors calculationsForExtractors)
    {
        article.addAttribute(new DoubleOrString( calculationsForExtractors.getAllKeyWordsWhichOccursInPassedBody(article.getBody())));
    }
}
