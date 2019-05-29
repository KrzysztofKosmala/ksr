package KNN.Metrics.Helpers;


public class StringAndDouble implements Comparable<StringAndDouble>
{

    private final String stringValue;
    private final double doubleValue;
    public StringAndDouble(String stringValue, double doubleValue)
    {
        this.stringValue = stringValue;
        this.doubleValue = doubleValue;
    }

    public String getStringValue()
    {
        return stringValue;
    }

    public double getDoubleValue()
    {
        return doubleValue;
    }

    @Override
    public int compareTo(StringAndDouble o)
    {
        return Double.compare(this.getDoubleValue(), o.getDoubleValue());
    }

}
