package KNN.Metrics;

import DataLayer.Helpers.DoubleOrString;
import KNN.Metrics.Helpers.IDistanceBetweenStrings;

import java.util.List;

public class Chebyshev implements IMetric
{
    private IDistanceBetweenStrings wayToCountDistanceBetweenStrings;

    public Chebyshev(IDistanceBetweenStrings howToCountDistanceBetweenStringsIfOccurs)
    {
        this.wayToCountDistanceBetweenStrings = howToCountDistanceBetweenStringsIfOccurs;
    }

    @Override
    public double countDistance(List<DoubleOrString> attributesLeft, List<DoubleOrString> attributesRight)
    {
        double distance=0;
        for(int i = 0; i<attributesLeft.size(); i++)
        {
            if(attributesLeft.get(i).isDoubleValue())
            {
                distance = Math.max(distance, Math.abs(attributesLeft.get(i).getDouble() - attributesRight.get(i).getDouble()));
            }else if(attributesLeft.get(i).isStringValue())
            {
                distance = Math.max(distance, wayToCountDistanceBetweenStrings.countDistance(attributesLeft.get(i).getString(), attributesRight.get(i).getString()));
            }
        }
        return distance;
    }
}
