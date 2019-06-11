package GUI;

import DAO.DATA_API;
import Extractors.IExtractors;
import KNN.Metrics.Chebyshev;
import KNN.Metrics.City;
import KNN.Metrics.Euclidean;
import KNN.Metrics.Helpers.IDistanceBetweenStrings;
import KNN.Metrics.Helpers.NGram;
import KNN.Metrics.Helpers.Niewiadomski;
import KNN.Metrics.IMetric;
import Manage.Manager;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App extends JFrame  {
    private JPanel panel;
    private JComboBox meticBox;
    private JComboBox stringComparingBox;
    private JComboBox nodeBox;
    private JCheckBox occurrenceOfKeyWordsCheckBox;
    private JCheckBox amountOfWordsCheckBox;
    private JCheckBox firstKeyWordCheckBox;
    private JCheckBox frequencyOfKeyWordsCheckBox;
    private JCheckBox allKeyWordsCheckBox;
    private JButton runButton;
    private JButton selectButton;
    private JCheckBox generateKeyWordsCheckBox;
    private JCheckBox generateStopListCheckBox;
    private JSlider amountOfKeyWordsSlider;
    private JLabel sliderValues;
    private JSlider nSlider;
    private JLabel nValue;
    private JSlider knnSlider;
    private JSlider percentSlider;
    private JLabel knnValue;
    private JLabel percentValue;
    private JTextField textField1;
    private DefaultComboBoxModel<String> listOfMetrics;
    private DefaultComboBoxModel<String> listOfStringComparingOptions;
    private DefaultComboBoxModel<String> listOfNodes;

    private List<String> strings;
    public App()  {


        setTitle("KSR");
        setSize(930,700);
        add(panel);
        listOfMetrics = new DefaultComboBoxModel<String>();
        listOfMetrics.addElement("Euclidean");
        listOfMetrics.addElement("City");
        listOfMetrics.addElement("Chebyshev");

        meticBox.setModel(listOfMetrics);


        listOfStringComparingOptions = new DefaultComboBoxModel<String>();
        listOfStringComparingOptions.addElement("NGram");
        listOfStringComparingOptions.addElement("Niewiadomski");

        stringComparingBox.setModel(listOfStringComparingOptions);


        listOfNodes = new DefaultComboBoxModel<String>();
        listOfNodes.addElement("PLACES");
        listOfNodes.addElement("TOPICS");

        nodeBox.setModel(listOfNodes);




        runButton.addActionListener(e -> new Thread(() ->
        {
            Manager m = new Manager();
            m.setupData(percentSlider.getValue(), getNodeChoice(), strings, generateKeyWordsCheckBox.isSelected(), generateStopListCheckBox.isSelected(), getExtractorsToRun(), amountOfKeyWordsSlider.getValue());
            m.extractAttributes();
            m.normalizeAttributes();
            m.setupKNN(knnSlider.getValue(), getMetricChoice());
            m.runKNN();
            Result result = new Result();
        }).start());

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChoiceAllowedStringsInNode choice = new ChoiceAllowedStringsInNode(nodeBox.getSelectedIndex());
                choice.setVisible(true);
                setList(choice.getChoosenList());

            }
        });


        generateKeyWordsCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(generateKeyWordsCheckBox.isSelected())
                {
                    amountOfKeyWordsSlider.setEnabled(true);

                }
                else
                    amountOfKeyWordsSlider.disable();
            }
        });

        amountOfKeyWordsSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                sliderValues.setText(String.valueOf(amountOfKeyWordsSlider.getValue()));
            }
        });

        nSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                nValue.setText(String.valueOf(nSlider.getValue()));
            }
        });


        percentSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                percentValue.setText(String.valueOf(percentSlider.getValue()));
            }
        });

        knnSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                knnValue.setText(String.valueOf(knnSlider.getValue()));
            }
        });
    }

    public IDistanceBetweenStrings getStringComparingBoxResault()
    {
        if(stringComparingBox.getSelectedIndex()==0)
            return new NGram(getNSliderResoult());
        else if (stringComparingBox.getSelectedIndex()==1)
            return new Niewiadomski(getNSliderResoult());
        else
            return new NGram(getNSliderResoult());
    }


    private List<Integer> getExtractorsToRun()
    {
            List<Integer> extractorsToRun = new ArrayList<>();
            if(occurrenceOfKeyWordsCheckBox.isSelected())
                extractorsToRun.add(0);
            if(amountOfWordsCheckBox.isSelected())
                extractorsToRun.add(1);
            if(firstKeyWordCheckBox.isSelected())
                extractorsToRun.add(2);
            if(frequencyOfKeyWordsCheckBox.isSelected())
                extractorsToRun.add(3);
            if(allKeyWordsCheckBox.isSelected())
                extractorsToRun.add(4);


        return extractorsToRun;
    }

    public int getNSliderResoult()
    {
        return nSlider.getValue();
    }

    public String getNodeChoice()
    {
        if(nodeBox.getSelectedIndex()==0)
            return "PLACES";
        else if(nodeBox.getSelectedIndex()==1)
            return "TOPICS";
        else
            return "PLACES";
    }

    public IMetric getMetricChoice ()
    {
        if (meticBox.getSelectedIndex()==0)
        {
            return new Euclidean(getStringComparingBoxResault());
        }
        if (meticBox.getSelectedIndex()==1)
        {
            return new City(getStringComparingBoxResault());
        }
        if (meticBox.getSelectedIndex()==2)
        {
            return new Chebyshev(getStringComparingBoxResault());
        }
        else
            return new Euclidean(getStringComparingBoxResault());
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public void setList(List<String> listOfStrings)
    {
        strings=listOfStrings;
    }
}
