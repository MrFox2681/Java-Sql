package com.company;

import javax.swing.*;
import java.awt.event.*;

public class mainmenu extends JFrame{
    private JButton carButton;
    private JButton Button2;
    private JButton clientsButton;
    private JButton dogovorButton;
    private JButton exitButton;
    private JLabel label1;
    private JLabel label2;
    private JPanel maimwinpanel;
    private JButton workWithTableButton;
    private JButton accountButton;

    public mainmenu(long idAcc){
        this.setContentPane(maimwinpanel);
        this.setVisible(true);
        this.setSize(1024, 640);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
                contractForm contract = new contractForm("",idAcc);
                setVisible(false);
            }
        });

        workWithTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                workWithTableForm workForm = new workWithTableForm(idAcc);
                setVisible(false);
            }
        });

        accountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accInfoForm accinfo = new accInfoForm(idAcc);
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

