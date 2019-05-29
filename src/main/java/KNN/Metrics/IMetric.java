package KNN.Metrics;

import DataLayer.Helpers.DoubleOrString;


import java.util.List;

public interface IMetric
{
    double countDistance(List<DoubleOrString> attributesLeft, List<DoubleOrString> attributesRight);
}
