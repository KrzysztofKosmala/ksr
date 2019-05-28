package DataLayer.Helpers;

public class DoubleOrString
{
    private Double doubleValue;
    private String stringValue;

    public DoubleOrString(Double doubleValue)
    {
        this.doubleValue = doubleValue;
    }

    public DoubleOrString(String stringValue)
    {
        this.stringValue = stringValue;
    }

    public boolean isDoubleValue()
    {
        return doubleValue!=null;
    }

    public boolean isStringValue()
    {
        return stringValue!=null;
    }

    public Double getDouble()
    {
        return doubleValue;
    }

    public String getString()
    {
        return stringValue;
    }

    public void setDoubleValue(Double doubleValue)
    {
        this.doubleValue = doubleValue;
    }

    public void setStringValue(String stringValue)
    {
        this.stringValue = stringValue;
    }
}
