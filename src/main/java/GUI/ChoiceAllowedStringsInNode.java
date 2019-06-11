package GUI;

import Reading.REUTERS;
import Reading.Reader;

import javax.swing.*;
import javax.xml.bind.JAXBException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

public class ChoiceAllowedStringsInNode extends JFrame {
    private JPanel window;
    private JList list;
    private JButton zapiszButton;

    private JScrollPane sp= new JScrollPane();
    private DefaultListModel<String> listModel;

    public ChoiceAllowedStringsInNode(int node)
    {

        setTitle("Dostępne w tym korzeniu");
        setSize(800,1000);
        //panel.add(list);
        //panel.add(zapiszButton);
        add(window);



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
        list.setVisibleRowCount(20);
        //add(list);
        //add(zapiszButton);

        zapiszButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        list.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER) {
                    list.setVisible(false);
                }

            }
        });
    }

}
