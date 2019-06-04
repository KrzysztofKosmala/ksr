import GUI.App;
import KNN.Metrics.Chebyshev;
import KNN.Metrics.City;
import KNN.Metrics.Euclidean;
import KNN.Metrics.Helpers.NGram;
import Manage.Manager;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Main
{

    public static void main(String[] args)
    {
        Manager m = new Manager();
//        ArrayList<Integer> extractorsToRun = new ArrayList<>();
//        extractorsToRun.add(0);
//        extractorsToRun.add(1);
//        extractorsToRun.add(2);
//        extractorsToRun.add(3);
//        extractorsToRun.add(4);
//        m.setupData(60, "PLACES", Arrays.asList("west-germany", "usa", "france","uk","canada","japan"), false, false, extractorsToRun,40);
//        m.extractAttributes();
//        m.normalizeAttributes();
//        m.setupKNN(5, new Euclidean(new NGram(3)));
//        m.runKNN();

        App main = new App();
        main.setVisible(true);


        main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
