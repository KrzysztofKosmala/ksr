package Extractors;

import Extractors.Helpers.HelperForArticle;

//wystepowanie slow kluczowego ktore musi byc podobne w stopniu minimum 0.8(JaccardSimilarity) do slowa kluczowego zapisanego w keyWordsMap
public class OccurrenceOfKeyWords implements IExtractors
{
    private static double minValueOfAttribute = 0.0;
    private static double maxValueOfAttribute = 0.0;

    @Override
    public void extract(Article article)
    {

            double value = (double)countKeyWordsInArticle(article);

            setNewMinMaxValueOfAttributeIfIsNeeded(value);

            article.addAttribute(new HelperForArticle(value));

    }

    @Override
    public void setNewMinMaxValueOfAttributeIfIsNeeded(double value)
    {

        if(isBiggerThanCurrentMaxValue(value))
            maxValueOfAttribute = value;

        if(isLessThanCurrentMinValue(value))
            minValueOfAttribute = value;

    }

    @Override
    public boolean isBiggerThanCurrentMaxValue(double value)
    {
        return value < minValueOfAttribute;
    }
    @Override
    public boolean isLessThanCurrentMinValue(double value)
    {
        return value > minValueOfAttribute;
    }
}
