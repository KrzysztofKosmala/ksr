package KNN.Metrics;

import Extractors.Helpers.HelperForArticle;

public interface Metric
{
    double countDistance(HelperForArticle[] attributesLeft, HelperForArticle[] attributesRight);
}
