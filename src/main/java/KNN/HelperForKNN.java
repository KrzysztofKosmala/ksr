package KNN;



public class HelperForKNN implements Comparable<HelperForKNN>
{

    private final String stringValue;
    private final double doubleValue;
    HelperForKNN(String stringValue, double doubleValue)
    {
        this.stringValue = stringValue;
        this.doubleValue = doubleValue;
    }

    String getStringValue()
    {
        return stringValue;
    }

    double getDoubleValue()
    {
        return doubleValue;
    }

    @Override
    public int compareTo(HelperForKNN o)
    {
        return Double.compare(this.getDoubleValue(), o.getDoubleValue());
    }

}
