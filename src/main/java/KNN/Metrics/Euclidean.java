package KNN.Metrics;

import DataLayer.Helpers.DoubleOrString;

public class Euclidean implements IMetric
{
    //ilosc takich samych charow w obu porownywanych stringach
    public final static int N = 3;


    //trzeba to sprawdzic
    public double countDistance(DoubleOrString[] attributesLeft, DoubleOrString[] attributesRight)
    {
        double r=0;
        for(int i = 0; i<attributesLeft.length; i++)
        {
                if(attributesLeft[i].isDoubleValue() && attributesRight[i].isDoubleValue())
                    r += Math.pow((attributesLeft[i].getDouble() - attributesRight[i].getDouble()), 2);
                else if((!attributesLeft[i].isStringValue() || !attributesRight[i].isStringValue()) && i==2)
                {
                    r+= 1;
                }else if (attributesLeft[i].isStringValue() && attributesRight[i].isStringValue())
                {
                    r+= Math.pow((1 - countNGramDistance(attributesLeft[i].getString(), attributesRight[i].getString(), N)),2);
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
