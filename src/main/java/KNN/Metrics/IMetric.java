package KNN.Metrics;

import Extractors.Helpers.HelperForArticle;

public interface IMetric
{
    double countDistance(HelperForArticle[] attributesLeft, HelperForArticle[] attributesRight);
}
