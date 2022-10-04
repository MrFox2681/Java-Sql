package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class billsForm extends JFrame{
    private JButton carButton;
    private JButton Button2;
    private JButton clientsButton;
    private JButton dogovorButton;
    private JButton exitButton;
    private JTable table1;
    private JButton contractButton;
    private JComboBox billsStatus;
    private JPanel billsPanel;
    private JButton checkButton;
    private JButton workWithTableButton;
    private JButton accauntButton;
    private static final String url = "jdbc:mysql://localhost:3306";
    private static final String user = "root";
    private static final String password = "9522403931_mkV";
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public int outputInTable(int poz) {
        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);
            // getting Statement object to execute query
            stmt = con.createStatement();
            String payDilivery = "";
            if(poz == 1)
                payDilivery = "ожидает оплаты";
            else if (poz == 2)
                payDilivery = "обработка";
            else if (poz == 3)
                payDilivery = "оплачено";
            else if(poz == 4)
                payDilivery = "ошибка оплаты";
            try {
                if (poz == 0)
                    rs = stmt.executeQuery("SELECT * FROM avto_diller.bills");
                else
                    rs = stmt.executeQuery("SELECT * FROM avto_diller.bills " +
                            "WHERE paymentStatus = '" + payDilivery + "'");
                DefaultTableModel tblModel = new DefaultTableModel();
                tblModel.addColumn("nameProd");
                tblModel.addColumn("billNumber");
                tblModel.addColumn("contractNumber");
                tblModel.addColumn("saleDate");
                tblModel.addColumn("arrivalDate");
                tblModel.addColumn("summa");
                tblModel.addColumn("transitStatus");
                tblModel.addColumn("paymentStatus");
                tblModel.addRow(new Object[] {"Модель", "Номер счёта", "Номер контракта", "Дата продажи", "Дата поставки", "Сумма", "Статус транзита", "Статус оплаты"});
                while (rs.next()) {
                    String name = rs.getString("nameProd");
                    String bilNumber = rs.getString("contractNumber");
                    String contrNumber = rs.getString("contractNumber");
                    String sale = rs.getString("saleDate");
                    String arrival = rs.getString("arrivalDate");
                    String summaFromTable = rs.getString("summa");
                    String transit = rs.getString("transitStatus");
                    String pay = rs.getString("paymentStatus");
                    String[] DBtable = {name, bilNumber, contrNumber, sale, arrival, summaFromTable, transit, pay};
                    tblModel.addRow(DBtable);
                }
                table1.setModel(tblModel);
            }
            finally {
                rs.close();
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and result set here
            try {
                con.close();
            } catch (SQLException se) { /*can't do anything*/ }
            try {
                stmt.close();
            } catch (SQLException se) { /*can't do anything*/ }
            try {
                rs.close();
            } catch (SQLException se) { /*can't do anything*/ }
        }

        return 0;
    }
     public billsForm(long idAcc){
        this.setContentPane(billsPanel);
        this.setVisible(true);
        this.setSize(1240, 720);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        contractButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int azaz = table1.getSelectedRow();
                System.out.println(azaz);
                if (azaz != -1)
                {
                    String contactNumber = (table1.getValueAt(azaz,2)).toString();
                    contractForm info = new contractForm(contactNumber, idAcc);
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
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int poz = billsStatus.getSelectedIndex();
                System.out.println(poz);
                outputInTable(poz);
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

         workWithTableButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 workWithTableForm workForm = new workWithTableForm(idAcc);
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
