package KNN.Metrics;

import Extractors.HelperForArticle;

public class Euclidean implements Metric
{
    public double countDistance(HelperForArticle[] attributesLeft, HelperForArticle[] attributesRight)
    {
        double r=0;
        //mozna tu sprawdzac czy prawa i lewa strona sa takie same
        for(int i = 0; i<attributesLeft.length; i++)
        {
            if(attributesLeft[i] != null)
            {
                if(attributesLeft[i].isDoubleValue() && attributesRight[i].isDoubleValue())
                    r += Math.pow((attributesLeft[i].getDouble() - attributesRight[i].getDouble()), 2);
                else if(attributesLeft[i].isStringValue() && attributesRight[i].isStringValue())
                {
                    //
                }
            }


        }

        return Math.sqrt(r);
    }
}
