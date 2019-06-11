package GUI;

import Reading.REUTERS;
import Reading.Reader;

import javax.swing.*;
import javax.xml.bind.JAXBException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

public class ChoiceAllowedStringsInNode extends JFrame {
    private JPanel panel;
    private JList list;
    private JButton zapiszButton;

    private JScrollPane sp= new JScrollPane();
    private DefaultListModel<String> listModel;

    public ChoiceAllowedStringsInNode(int node)
    {
        setTitle("Dostępne w tym korzeniu");
        setSize(400,700);
        add(panel);

        listModel = new DefaultListModel<String>();
        List<REUTERS> r = new ArrayList<>();
        {
            try
            {
                r = Reader.read().getREUTERS();
            } catch (JAXBException e)
            {
                e.printStackTrace();
            }
        }

        for(REUTERS reuter : r)
        {
            if(node == 0 && !listModel.contains(reuter.getPLACES()) && !reuter.getPLACES().equals("x"))
            {
                listModel.addElement(reuter.getPLACES());
            }
            else if(node == 1 && !listModel.contains(reuter.getTOPICS())  && !reuter.getTOPICS().equals("x"))
            {
                listModel.addElement(reuter.getTOPICS());
            }
        }

        list.setModel(listModel);

        sp.setViewportView(list);
        add(sp);
        zapiszButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

}
