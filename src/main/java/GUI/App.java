package GUI;

import KNN.Metrics.IMetric;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends JFrame  {
    private JPanel panel;
    private JComboBox meticBox;
    private JComboBox stringComparingBox;
    private JComboBox nodeBox;
    private JCheckBox occurrenceOfKeyWordsCheckBox;
    private JCheckBox amountOfWordsCheckBox;
    private JCheckBox firstKeyWordCheckBox;
    private JCheckBox FrequencyOfKeyWordsCheckBox;
    private JCheckBox allKeyWordsCheckBox;
    private JButton runButton;
    private JButton selectButton;
    private JCheckBox generateKeyWordsCheckBox;
    private JCheckBox generateStopListCheckBox;
    private JSlider amountOfKeyWordsSlider;
    private JLabel sliderValues;
    private JTextField textField1;
    private DefaultComboBoxModel<String> listOfMetrics;
    private DefaultComboBoxModel<String> listOfStringComparingOptions;
    private DefaultComboBoxModel<String> listOfNodes;


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
        listOfNodes.addElement("TOPIC");

        nodeBox.setModel(listOfNodes);

        if(generateStopListCheckBox.isSelected())
        {

        }


        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChoiceAllowedStringsInNode choice = new ChoiceAllowedStringsInNode();
                choice.setVisible(true);

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
    }

    public IMetric getMetricChoice ()
    {
        if (meticBox.getSelectedIndex()==0)
        {
            // to do
            // metoda po wybraniu opcji z listy do stringow
//            return new Euclidean();
            return null;
        }
        if (meticBox.getSelectedIndex()==1)
        {
            return null;
        }
        if (meticBox.getSelectedIndex()==2)
        {
            return null;
        }
        else
            return null;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }


}
