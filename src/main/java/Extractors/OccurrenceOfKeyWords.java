package Extractors;

import DataLayer.Article;
import Extractors.Helpers.CalculationsForExtractors;
import DataLayer.Helpers.DoubleOrString;

//wystepowanie slow kluczowego ktore musi byc podobne w stopniu minimum 0.8(JaccardSimilarity) do slowa kluczowego zapisanego w keyWordsMap
public class OccurrenceOfKeyWords implements IExtractors
{


    @Override
    public void extract(Article article)
    {

            double value = (double) CalculationsForExtractors.countKeyWordsInArticle(article);

            article.addAttribute(new DoubleOrString(value));

    }


}
