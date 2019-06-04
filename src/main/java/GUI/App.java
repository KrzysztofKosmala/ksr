package GUI;

import KNN.Metrics.Euclidean;
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
    private JCheckBox ex1CheckBox;
    private JCheckBox ex2CheckBox;
    private JCheckBox ex3CheckBox;
    private JCheckBox ex4CheckBox;
    private JCheckBox ex5CheckBox;
    private JButton uruchomButton;
    private JButton wybierzButton;
    private JCheckBox genrujSłowaKluczoweCheckBox;
    private JCheckBox generujStopListęCheckBox;
    private JSlider amountOfKeyWordsSlider;
    private JLabel sliderValues;
    private JTextField textField1;
    private DefaultComboBoxModel<String> listModel;


    public App()  {


        setTitle("KSR");
        setSize(930,700);
        add(panel);
        listModel = new DefaultComboBoxModel<String>();
        listModel.addElement("Euclidean");
        listModel.addElement("City");
        listModel.addElement("Chebyshev");

        meticBox.setModel(listModel);



        if(generujStopListęCheckBox.isSelected())
        {

        }


        uruchomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        wybierzButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChoiceAllowedStringsInNode choice = new ChoiceAllowedStringsInNode();
                choice.setVisible(true);

            }
        });


        genrujSłowaKluczoweCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(genrujSłowaKluczoweCheckBox.isSelected())
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
