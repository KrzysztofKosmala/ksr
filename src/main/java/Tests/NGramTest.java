package Tests;

import KNN.Metrics.Helpers.NGram;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class NGramTest
{
    static NGram nGramTest;
    static String stringLeft;
    static String stringRight;

    @BeforeClass
    public static void setup()
    {
        nGramTest = new NGram(3);
        stringLeft="string";
        stringRight="strona";
    }

    @Test
    public void nGramTest()
    {
        Assert.assertEquals(0.25, nGramTest.countDistance(stringLeft,stringRight),0.01);
    }
}
