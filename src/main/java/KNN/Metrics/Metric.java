package KNN.Metrics;

import Extractors.HelperForArticle;

public interface Metric
{
    double countDistance(HelperForArticle[] attributesLeft, HelperForArticle[] attributesRight);
}
