package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class clientForm extends JFrame{
    private JButton carButton;
    private JButton Button2;
    private JButton dogovorButton;
    private JButton exitButton;
    private JTable table1;

    private JButton clientsButton;
    private JButton clientMbButton;
    private JComboBox comboBox1;
    private JPanel clientPanel;
    private JButton checkButton;
    private JButton workWithTableButton;
    private JButton accauntButton;


    private static final String url = "jdbc:mysql://localhost:3306";
    private static final String user = "root";
    private static final String password = "9522403931_mkV";
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    //enum('купе', 'седан', 'кроссовер', 'джип', 'кабриолет', 'Хэтчбек')






    public int outputInTable(int poz) {
        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);
            // getting Statement object to execute query
            stmt = con.createStatement();
            String bodyTypeCar = "";
            if(poz == 1)
                bodyTypeCar = "купе";
            else if (poz == 2)
                bodyTypeCar = "седан";
            else if (poz == 3)
                bodyTypeCar = "кроссовер";
            else if (poz == 4)
                bodyTypeCar = "джип";
            else if (poz == 5)
                bodyTypeCar = "кабриолет";
            else if (poz == 6)
                bodyTypeCar = "хэтчбэк";
            System.out.println(poz);
            System.out.println("ok");
            try {
                if (poz == 0)
                    rs = stmt.executeQuery("SELECT * FROM avto_diller.clients");
                else if (poz == -228)
                    rs = stmt.executeQuery("SELECT cl.* " +
                            " FROM avto_diller.clients AS cl" +
                            " JOIN avto_diller.contracts AS c on c.client_id = cl.id_client" +
                            " JOIN avto_diller.products AS p on c.product_id = p.id_products" +
                            " JOIN avto_diller.bills AS b on p.bill_id = b.id_bills" +
                            " WHERE b.saleDate <= CURDATE() - INTERVAL 4 YEAR");
                else
                    rs = stmt.executeQuery("SELECT cl.* " +
                            "FROM avto_diller.clients AS cl " +
                            "JOIN avto_diller.contracts AS c on c.client_id = cl.id_client " +
                            "JOIN avto_diller.products AS p on c.product_id = p.id_products " +
                            "WHERE p.bodyType = '" + bodyTypeCar + "'");
                DefaultTableModel tblModel = new DefaultTableModel();
                tblModel.addColumn("lastname");
                tblModel.addColumn("name");
                tblModel.addColumn("patronymic");
                tblModel.addColumn("payment");
                tblModel.addColumn("tel_number");
                tblModel.addColumn("email_client");
                tblModel.addColumn("adress");
                tblModel.addColumn("note");
                tblModel.addRow(new Object[] {"Фамилия", "Имя", "Отчество", "Условия поставки", "Банковский счёт", "Номер телефона", "Адрес", "Замектки"});
                while (rs.next()) {
                    String field1 = rs.getString("lastname");
                    String field2 = rs.getString("name");
                    String field3 = rs.getString("patronymic");
                    String field4 = rs.getString("payment");
                    String field6 = rs.getString("tel_number");
                    String field7 = rs.getString("email_client");
                    String field8 = rs.getString("adress");
                    String field9 = rs.getString("note");
                    String[] DBtable = {field1, field2, field3, field4, field6, field7, field8, field9};
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


    public clientForm(long idAcc){
        this.setContentPane(clientPanel);
        this.setVisible(true);
        this.setSize(1240, 720);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        clientMbButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int poz = -228;
                System.out.println(poz);
                outputInTable(poz);
            }
        });
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int poz = comboBox1.getSelectedIndex();
                System.out.println(poz);
                outputInTable(poz);
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
//        clientsButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                clientForm client = new clientForm(idAcc);
//                setVisible(false);
//            }
//        });
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
