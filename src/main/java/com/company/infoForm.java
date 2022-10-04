package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class infoForm extends JFrame{
    private JButton backButton;
    private JLabel contractNumber;
    private JLabel billNubmber;
    private JLabel clientFIO;
    private JLabel clientEmail;
    private JLabel phoneClient;
    private JLabel titleClient;
    private JLabel titleCar;
    private JLabel carModel;
    private JLabel vinCar;
    private JLabel colorCar;
    private JLabel priceCar;
    private JPanel infoPanel;
    private JLabel label1;
    private JLabel label2;
    private JLabel manufactAddress;
    private JLabel manufDirector;
    private JLabel manufactName;
    private static final String url = "jdbc:mysql://localhost:3306";
    private static final String user = "root";
    private static final String password = "9522403931_mkV";
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public int workWithTable(String VIN)
    {
        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);
            // getting Statement object to execute query
            stmt = con.createStatement();
            //Info About Car
            long idProductsGet = 0;
            long idClientGet = 0;
            long idManufactGet = 0;
            long idBillGet = 0;
            //Info About Car
            try {
                rs = stmt.executeQuery("SELECT id_products, name, vinNubmer, price, color,bill_id FROM avto_diller.products WHERE vinNubmer ='" + VIN +"'");
                while (rs.next()) {
                    idProductsGet = rs.getLong("id_products");
                    carModel.setText(rs.getString("name"));
                    vinCar.setText(rs.getString("vinNubmer"));
                    colorCar.setText(rs.getString("color"));
                    priceCar.setText(rs.getString("price"));
                    idBillGet = rs.getLong("bill_id");
                }
            }
            finally {
                rs.close();
            }
            //Info About Contract
            try {
                rs = stmt.executeQuery("SELECT contractNumver, client_id, producer_id FROM avto_diller.contracts WHERE producer_id =" + idProductsGet);
                while (rs.next()) {

                    contractNumber.setText(rs.getString("contractNumver"));
                    idClientGet = rs.getLong("client_id");
                    idManufactGet = rs.getLong("producer_id");
                }
            }
            finally {
                rs.close();
            }
            //Info About Client
            try {
                rs = stmt.executeQuery("SELECT lastname, name, patronymic, email_client, tel_number  FROM avto_diller.clients WHERE contract_id =" + idClientGet);
                while (rs.next()) {
                    String FIO = "";
                    FIO = rs.getString("lastname") + " " + rs.getString("name")+ " " + rs.getString("patronymic");
                    clientFIO.setText(FIO);
                    clientEmail.setText(rs.getString("email_client"));
                    phoneClient.setText(rs.getString("tel_number"));
                }
            }
            finally {
                rs.close();
            }
            //Info About Manufacture
            try {
                rs = stmt.executeQuery("SELECT title, director, address FROM avto_diller.producers WHERE id_producer =" + idManufactGet);
                while (rs.next()) {
                    manufactName.setText(rs.getString("title"));
                    manufDirector.setText(rs.getString("director"));
                    manufactAddress.setText(rs.getString("address"));
                }
            }
            finally {
                rs.close();
            }
            try {
                rs = stmt.executeQuery("SELECT billNumber FROM avto_diller.bills WHERE id_bills =" + idBillGet);
                while (rs.next()) {
                    billNubmber.setText(rs.getString("billNumber"));
                }
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
        return -1;
    }

    public infoForm(String VIN, long idAcc){
        this.setContentPane(infoPanel);
        this.setVisible(true);
        this.setSize(640, 480);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        System.out.println(VIN);
        workWithTable(VIN);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                avtoTablForm loginForm = new avtoTablForm(idAcc);
                setVisible(false);
            }
        });






    }
}



