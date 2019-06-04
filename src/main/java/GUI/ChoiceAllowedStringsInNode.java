package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChoiceAllowedStringsInNode extends JFrame {
    private JPanel panel;
    private JList list;
    private JButton zapiszButton;
    private DefaultListModel<String> listModel;

    public ChoiceAllowedStringsInNode()
    {
        setTitle("Test");
        setSize(400,700);
        add(panel);
        listModel = new DefaultListModel<String>();
        listModel.addElement("Test");

        list.setModel(listModel);


        zapiszButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
