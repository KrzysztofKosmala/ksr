package Tests;

import DataLayer.Helpers.DoubleOrString;
import KNN.Metrics.Euclidean;
import KNN.Metrics.Helpers.NGram;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class EuclideanTest
{

    static Euclidean euclideanTest;
    static List<DoubleOrString> attributesLeftTest;
    static List<DoubleOrString> attributesRightTest;

    @BeforeClass
    public static void setup()
    {
        euclideanTest = new Euclidean(new NGram(3));

        attributesLeftTest = new ArrayList<>();
        attributesLeftTest.add(new DoubleOrString(0.5));
        attributesLeftTest.add(new DoubleOrString(0.6));
        attributesLeftTest.add(new DoubleOrString(0.7));
        attributesLeftTest.add(new DoubleOrString("string"));

        attributesRightTest = new ArrayList<>();
        attributesRightTest.add(new DoubleOrString(0.1));
        attributesRightTest.add(new DoubleOrString(0.2));
        attributesRightTest.add(new DoubleOrString(0.3));
        attributesRightTest.add(new DoubleOrString("strona"));

    }


    @Test
    public void countDistanceTest()
    {
        Assert.assertEquals(0.6928203230275509, euclideanTest.countDistance(attributesLeftTest, attributesRightTest), 0.01);
    }
}
