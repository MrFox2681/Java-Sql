package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class workWithTableForm  extends JFrame{
    private JButton carButton;
    private JButton Button2;
    private JButton clientsButton;
    private JButton dogovorButton;
    private JButton exitButton;
    private JPanel workPanel;
    private JComboBox chooseTableComboBox;
    private JButton deleteDataButton;
    private JButton changeDataButton;
    private JButton addDataButton;
    private JButton accauntButton;


    public workWithTableForm(long idAcc){
        this.setContentPane(workPanel);
        this.setVisible(true);
        this.setSize(640, 480);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addForm addForm = new addForm(idAcc);
                setVisible(false);
            }
        });

        changeDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int poz = chooseTableComboBox.getSelectedIndex();
                if (poz != 0)
                {
                    deleteChangeForm deleteChangeForm = new deleteChangeForm(poz, idAcc);
                    setVisible(false);
                }
            }
        });





        carButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                avtoTablForm cars = new avtoTablForm(idAcc);
                setVisible(false);
            }
        });
        Button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                billsForm bills = new billsForm(idAcc);
                setVisible(false);
            }
        });
        clientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientForm client = new clientForm(idAcc);
                setVisible(false);
            }
        });
        dogovorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contractForm contract = new contractForm("", idAcc);
                setVisible(false);
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginWind loginForm = new loginWind();
                setVisible(false);
            }
        });
    }
}




