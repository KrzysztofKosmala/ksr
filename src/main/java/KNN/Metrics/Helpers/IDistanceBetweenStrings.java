package KNN.Metrics.Helpers;

public interface IDistanceBetweenStrings
{
    double countDistance(String left, String right);

    default int findMax(int left, int right)
    {
        return ( left > right ) ? left : right;
    }
}
