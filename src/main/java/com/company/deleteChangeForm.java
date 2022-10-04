package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class deleteChangeForm extends JFrame {


    private JPanel deleteChangeForm;
    private JButton backButton;
    private JButton actionButton;
    private JTable table1;



    private static final String url = "jdbc:mysql://localhost:3306";
    private static final String user = "root";
    private static final String password = "9522403931_mkV";
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public String upDateTable()
    {
        int azaz = table1.getSelectedRow();
        System.out.println(azaz);
        String VinNumber = "";
        if (azaz != -1)
        {
            VinNumber = (table1.getValueAt(azaz,3)).toString();
            System.out.println(VinNumber);
        }
        return VinNumber;
    }
     public void func(String VinNumber)
     {
         try {
             // opening database connection to MySQL server
             con = DriverManager.getConnection(url, user, password);
             // getting Statement object to execute query
             stmt = con.createStatement();
             String billStatusDilivery = "";
             System.out.println("okkkkkk");
             System.out.println(VinNumber);
             System.out.println((table1.getValueAt(table1.getSelectedRow() ,3)).toString());
             System.out.println((table1.getValueAt(table1.getSelectedRow(),4)).toString());
             System.out.println((table1.getValueAt(table1.getSelectedRow(),2)).toString());
                 if (!"".equals(VinNumber))
                 {
                     try {
                         stmt.executeUpdate("UPDATE avto_diller.products " +
                                 "SET name = '" + (table1.getValueAt(table1.getSelectedRow() ,0)).toString()
                                 +"', price = '" + (table1.getValueAt(table1.getSelectedRow() ,1)).toString()
                                 +"', contractNumber = '" + (table1.getValueAt(table1.getSelectedRow(),2)).toString()
                                 +"', vinNubmer = '" + (table1.getValueAt(table1.getSelectedRow() ,3)).toString()
                                 +"', enginePower = '" + (table1.getValueAt(table1.getSelectedRow(),4)).toString()
                                 +"', engineVolume = '" + (table1.getValueAt(table1.getSelectedRow() ,5)).toString()
                                 +"', fuel = '" + (table1.getValueAt(table1.getSelectedRow() ,6)).toString()
                                 +"', transmission = '" + (table1.getValueAt(table1.getSelectedRow()- 1,7)).toString()
                                 +"', color = '" + (table1.getValueAt(table1.getSelectedRow(),8)).toString() + "'" +
                                 "WHERE vinNubmer  = '" + VinNumber + "'");
                     }
                     finally {

                     }
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
         }
     }





    public int outputInTable(int tableNumber) {
        String VINnum = "";
        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);
            // getting Statement object to execute query
            stmt = con.createStatement();
            String billStatusDilivery = "";
            System.out.println(tableNumber);
            System.out.println("ok");
            if (tableNumber == 1)
            {
                try {
                    rs = stmt.executeQuery("SELECT *" +
                                "FROM avto_diller.products as p");
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
                    tblModel.addRow(new Object[] {"Модель", "Цена", "Номер договора", "VIN номер", "Мощность двигателя", "Объём двигателя", "Тип топлива", "Тип КПП", "Цвет"});
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
                        String[] DBtable = {field1, field2, field3, field4, field5, field6, field7, field8, field9};
                        tblModel.addRow(DBtable);
                    }
                    table1.setModel(tblModel);
                }
                finally {
                    rs.close();
                }
                System.out.println("1488");
                actionButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("in button");
                        func(upDateTable());
                    }
                });
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



    public deleteChangeForm(int poz, long idAcc) {
        this.setContentPane(deleteChangeForm);
        this.setVisible(true);
        this.setSize(640, 480);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        System.out.println(poz);
        outputInTable(poz);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                workWithTableForm a = new workWithTableForm(idAcc);
                setVisible(false);
            }
        });
    }


}
