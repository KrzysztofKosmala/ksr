package Extractors.Helpers;

import DataLayer.Article;
import DataLayer.Helpers.DoubleOrString;

import java.util.List;

public class Normalization
{
    private int SIZE_OF_ATTRIBUTES;
    private final static int MAX_RANGE_OF_NORMALIZED_ATTRIBUTE = 1;
    private final static int MIN_RANGE_OF_NORMALIZED_ATTRIBUTE = 0;
    private MinMax[] MIN_MAX_VALUES_FOR_EACH_ATTRIBUTE;

    public void normalize(List<Article> articles)
    {
        SIZE_OF_ATTRIBUTES=articles.get(0).getAttributes().size();
        MIN_MAX_VALUES_FOR_EACH_ATTRIBUTE = new MinMax[SIZE_OF_ATTRIBUTES];


        for(Article article : articles)
        {
            int i = 0;
            for(DoubleOrString dos : article.getAttributes())
            {
                if(dos.isDoubleValue())
                {
                    if(MIN_MAX_VALUES_FOR_EACH_ATTRIBUTE[i].getMin() > dos.getDouble())
                        MIN_MAX_VALUES_FOR_EACH_ATTRIBUTE[i].setMin(dos.getDouble());

                    if(MIN_MAX_VALUES_FOR_EACH_ATTRIBUTE[i].getMax() < dos.getDouble())
                        MIN_MAX_VALUES_FOR_EACH_ATTRIBUTE[i].setMax(dos.getDouble());
                }
                i++;
            }
        }

        for(Article article : articles)
        {
            List<DoubleOrString> helper = article.getAttributes();
            for(int i = 0; i<helper.size(); i++)
            {
                if(helper.get(i).isDoubleValue())
                    article.setAttribute(i,normalizeAttribute(helper.get(i), i));
            }
        }
    }

    private Double normalizeAttribute(DoubleOrString value, int index)
    {
        return ((value.getDouble() - MIN_MAX_VALUES_FOR_EACH_ATTRIBUTE[index].getMin())
                / (MIN_MAX_VALUES_FOR_EACH_ATTRIBUTE[index].getMax() - MIN_MAX_VALUES_FOR_EACH_ATTRIBUTE[index].getMin()))
                * (MAX_RANGE_OF_NORMALIZED_ATTRIBUTE - MIN_RANGE_OF_NORMALIZED_ATTRIBUTE) + MIN_RANGE_OF_NORMALIZED_ATTRIBUTE;
    }


}
