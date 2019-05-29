import KNN.Metrics.Euclidean;
import KNN.Metrics.Helpers.NGram;
import Manage.Manager;

import java.util.Arrays;

public class Main
{

    public static void main(String[] args)
    {
        Manager m = new Manager();

        m.setupData(60, "PLACES", Arrays.asList("west-germany","usa","france","uk","canada","japan"), true, true);
        m.extractAttributes();
        m.normalizeAttributes();
        m.setupKNN(5, new Euclidean(new NGram(3)));
        m.runKNN();
    }
}
