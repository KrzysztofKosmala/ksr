package KNN.Metrics;

import Extractors.HelperForArticle;

public class Euclidean implements Metric
{
    //ilosc takich samych charow w obu porownywanych stringach
    public final static int N = 3;


    //trzeba to sprawdzic
    public double countDistance(HelperForArticle[] attributesLeft, HelperForArticle[] attributesRight)
    {
        double r=0;
        //mozna tu sprawdzac czy prawa i lewa strona sa takie same ale nie wiadomo po co
        for(int i = 0; i<attributesLeft.length; i++)
        {
                if(attributesLeft[i].isDoubleValue() && attributesRight[i].isDoubleValue())
                    r += Math.pow((attributesLeft[i].getDouble() - attributesRight[i].getDouble()), 2);
                else if((!attributesLeft[i].isStringValue() || !attributesRight[i].isStringValue()) && i==2)
                {
                    r+= 1;
                }else if (attributesLeft[i].isStringValue() && attributesRight[i].isStringValue())
                {
                    r+= Math.pow(countNGramDistance(attributesLeft[i].getString(), attributesRight[i].getString(), N),2);
                }
        }
        return Math.sqrt(r);
    }

    //dystans miedzy Stringami nie moze byc liczony w zwykly euklidesowy sposob i trzeba zastosowac metode n-Gram
    //oblicza ngram (przyklad 3.71 w pliku wykladowym dolaczonym do projektu)
    private double countNGramDistance(String left, String right, int n)
    {
        int grams=0;
        double distance=1.0;
        if(left.length()>=n)
        {
            for(int i =0; i<left.length() - n + 1; i++)
            {
                String helper = left.substring(i,i+n);
                if(right.contains(helper))
                    grams++;
            }
            distance = grams / (findMax(left.length(), right.length()) - n + 1.0);
        }

        return distance;
    }

    private int findMax(int left, int right)
    {
      return ( left > right ) ? left : right;
    }
}
