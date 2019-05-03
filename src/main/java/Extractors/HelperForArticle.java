package Extractors;

public class HelperForArticle
{
    private Double doubleValue;
    private String stringValue;


    HelperForArticle(Double doubleValue)
    {
        this.doubleValue = doubleValue;
    }

    public HelperForArticle(String stringValue)
    {
        this.stringValue = stringValue;
    }

    boolean isDoubleValue()
    {
        return doubleValue!=null;
    }

    boolean isStringValue()
    {
        return stringValue!=null;
    }


    void setDoubleValue(Double doubleValue)
    {
        this.doubleValue = doubleValue;
    }

    void setStringValue(String stringValue)
    {
        this.stringValue = stringValue;
    }

    public Double getDouble()
    {
        return doubleValue;
    }

    public String getString()
    {
        return stringValue;
    }

}
