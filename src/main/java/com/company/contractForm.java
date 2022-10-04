package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.*;

public class contractForm extends JFrame {
    private JButton carButton;
    private JButton Button2;
    private JButton clientsButton;
    private JButton dogovorButton;
    private JButton exitButton;
    private JTable table1;
    private JButton sortLowButton;
    private JButton clearButton;
    private JPanel contractPanel;
    private JButton sortUpButton;
    private JButton workWithTableButton;
    private JButton accauntButton;

    private static final String url = "jdbc:mysql://localhost:3306";
    private static final String user = "root";
    private static final String password = "9522403931_mkV";
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public int outputInTable(String contrNum) {
        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);
            // getting Statement object to execute query
            stmt = con.createStatement();

            try {
                if (contrNum.equals(""))
                    rs = stmt.executeQuery("SELECT * FROM avto_diller.contracts");
                else if (contrNum.equals("-1")) {
                    rs = stmt.executeQuery("SELECT * FROM avto_diller.contracts ORDER BY summa");
                } else if (contrNum.equals("-2")) {
                    rs = stmt.executeQuery("SELECT * FROM avto_diller.contracts ORDER BY summa DESC");
                } else
                    rs = stmt.executeQuery("SELECT * FROM avto_diller.contracts WHERE contractNumver = '" + contrNum + "'");
                DefaultTableModel tblModel = new DefaultTableModel();
                tblModel.addColumn("contractNumver");
                tblModel.addColumn("dataZakl");
                tblModel.addColumn("shortText");
                tblModel.addColumn("peculiarities");
                tblModel.addColumn("client_id");
                tblModel.addColumn("producer_id");
                tblModel.addColumn("summa");
                tblModel.addColumn("product_id");
                tblModel.addRow(new Object[]{"Номер контракта", "Дата заказа", "Краткий текст договора", "Условия поставки", "id клиента", "id поставщика", "Сумма заказа", "id товара"});
                while (rs.next()) {
                    String field1 = rs.getString("contractNumver");
                    String field2 = rs.getString("dataZakl");
                    String field3 = rs.getString("shortText");
                    String field4 = rs.getString("peculiarities");
                    String field5 = rs.getString("client_id");
                    String field6 = rs.getString("producer_id");
                    String field7 = rs.getString("summa");
                    String field8 = rs.getString("product_id");
                    String[] DBtable = {field1, field2, field3, field4, field5, field6, field7, field8};
                    tblModel.addRow(DBtable);

                }
                table1.setModel(tblModel);
            } finally {
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

    public contractForm(String contrNum, long idAcc) {
        this.setContentPane(contractPanel);
        this.setVisible(true);
        this.setSize(1240, 720);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        if (contrNum.equals(""))
            outputInTable("");
        else
            outputInTable(contrNum);


        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputInTable("");
            }
        });


        sortUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputInTable("-2");
            }
        });

        sortLowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputInTable("-1");
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
