package KNN.Metrics;

import DataLayer.Helpers.DoubleOrString;
import KNN.Metrics.Helpers.IDistanceBetweenStrings;

import java.util.List;

public class Euclidean implements IMetric
{
    private IDistanceBetweenStrings wayToCountDistanceBetweenStrings;

    public Euclidean(IDistanceBetweenStrings howToCountDistanceBetweenStringsIfOccurs)
    {
        this.wayToCountDistanceBetweenStrings = howToCountDistanceBetweenStringsIfOccurs;
    }

    public double countDistance(List<DoubleOrString> attributesLeft, List<DoubleOrString> attributesRight)
    {
        double r=0;
        for(int i = 0; i<attributesLeft.size(); i++)
        {
                if(attributesLeft.get(i).isDoubleValue())
                {
                    r += Math.pow(attributesLeft.get(i).getDouble() - attributesRight.get(i).getDouble(), 2);
                }else if(attributesLeft.get(i).isStringValue())
                {
                    r += Math.pow( 1 - wayToCountDistanceBetweenStrings.countDistance(attributesLeft.get(i).getString(), attributesRight.get(i).getString()), 2);
                }
        }
        return Math.sqrt(r);
    }
}
