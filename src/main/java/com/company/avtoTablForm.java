package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class avtoTablForm extends JFrame{
    private JButton carButton;
    private JButton Button2;
    private JButton clientsButton;
    private JButton dogovorButton;
    private JButton exitButton;
    private JTable tabelAvto;
    private JButton infButton;
    private JComboBox comboBox1;
    private JPanel avtoPanel;
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
            String billStatusDilivery = "";
            if(poz == 1)
            {
                billStatusDilivery = "создано";
            }
            else if (poz == 2)
            {
                billStatusDilivery = "в пути";
            } else if (poz == 3)
            {
                billStatusDilivery = "отгружено";
            }
            System.out.println(poz);
            System.out.println("ok");
            try {
                if (poz != 0)
                    rs = stmt.executeQuery("SELECT p.*, b.transitStatus " +
                                               "FROM avto_diller.products as p " +
                                               "JOIN avto_diller.bills as b on p.bill_id = b.id_bills " +
                                               "WHERE b.transitStatus ='" + billStatusDilivery + "'");
                else
                {
                    System.out.println("ok");
                    rs = stmt.executeQuery("SELECT p.*, b.transitStatus " +
                                               "FROM avto_diller.products as p " +
                                               "JOIN avto_diller.bills as b on p.bill_id = b.id_bills");
                    System.out.println("ok");

                }
                DefaultTableModel tblModel = new DefaultTableModel();
                tblModel.addColumn("name");
                tblModel.addColumn("price");
                tblModel.addColumn("contractNumber");
                tblModel.addColumn("vinNubmer");
                tblModel.addColumn("enginePower");
                tblModel.addColumn("engineVolume");
                tblModel.addColumn("fuel");
                tblModel.addColumn("transmission");
                tblModel.addColumn("color");
                tblModel.addColumn("transitStatus");
                tblModel.addRow(new Object[] {"Модель", "Цена", "Номер договора", "VIN номер", "Мощность двигателя", "Объём двигателя", "Тип топлива", "Тип КПП", "Цвет", "Статус поставки"});
                while (rs.next()) {
                    String field1 = rs.getString("name");
                    String field2 = rs.getString("price");
                    String field3 = rs.getString("contractNumber");
                    String field4 = rs.getString("vinNubmer");
                    String field5 = rs.getString("enginePower");
                    String field6 = rs.getString("engineVolume");
                    String field7 = rs.getString("fuel");
                    String field8 = rs.getString("transmission");
                    String field9 = rs.getString("color");
                    String field10 = rs.getString("transitStatus");
                    String[] DBtable = {field1, field2, field3, field4, field5, field6, field7, field8, field9, field10};
                    tblModel.addRow(DBtable);
                }
                tabelAvto.setModel(tblModel);
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



    public avtoTablForm(long idAcc){
        this.setContentPane(avtoPanel);
        this.setVisible(true);
        this.setSize(1240, 720);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int poz = comboBox1.getSelectedIndex();
                System.out.println(poz);
                outputInTable(poz);
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


        infButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int azaz = tabelAvto.getSelectedRow();
                System.out.println(azaz);
                if (azaz != -1)
                {
                    String VINForm = (tabelAvto.getValueAt(azaz,3)).toString();
                    infoForm info = new infoForm(VINForm, idAcc);
                    setVisible(false);
                }
            }
        });
    }
}
