import KNN.Metrics.Euclidean;
import KNN.Metrics.Helpers.NGram;
import Manage.Manager;

import java.util.ArrayList;
import java.util.Arrays;

public class Main
{

    public static void main(String[] args)
    {
        Manager m = new Manager();
        ArrayList<Integer> extractorsToRun = new ArrayList<>();
        extractorsToRun.add(0);
        extractorsToRun.add(1);
        extractorsToRun.add(2);
        m.setupData(60, "PLACES", Arrays.asList("west-germany","usa","france","uk","canada","japan"), false, false, extractorsToRun);
        m.extractAttributes();
        m.normalizeAttributes();
        m.setupKNN(5, new Euclidean(new NGram(3)));
        m.runKNN();
    }
}
