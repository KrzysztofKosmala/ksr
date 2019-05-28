package KNN.Metrics;

import DataLayer.Helpers.DoubleOrString;

public interface IMetric
{
    double countDistance(DoubleOrString[] attributesLeft, DoubleOrString[] attributesRight);
}
