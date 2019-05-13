package Extractors;

public class HelperForArticle
{
    private Double doubleValue;
    private String stringValue;


    HelperForArticle(){};
    HelperForArticle(Double doubleValue)
    {
        this.doubleValue = doubleValue;
    }

    HelperForArticle(String stringValue)
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
