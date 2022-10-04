package com.company;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class addForm extends JFrame {
    private JButton backButton;
    private JTextField modelTextField;
    private JButton addButton;
    private JComboBox bodyTypeComboBox;
    private JComboBox deliverStatusComboBox;
    private JComboBox payStatusComboBox;
    private JTextField priceTextField;
    private JTextField contractTextField;
    private JTextField VINTextField;
    private JTextField powerTextField;
    private JTextField volumeTextField;
    private JTextField fuelTextField;
    private JTextField KPPTextField;
    private JTextField fuelRashTextField;
    private JTextField maxSpeedTextField;
    private JTextField volumeBagTextField;
    private JTextField colorTextField;
    private JTextField lastTextField;
    private JTextField nameTextField;
    private JTextField OtchTextField;
    private JTextField bankTextField;
    private JTextField phoneTextField;
    private JTextField emailTextField;
    private JTextField addressClientTextField;
    private JTextField noteTextField;
    private JTextField dataTextField;
    private JTextField shortTextField;
    private JTextField specialTextField;
    private JTextField billTextField;
    private JTextField paymentDataTextField;
    private JTextField deliveryDataTextField;
    private JPanel addPanel;
    private JTextField addressTextField;
    private JTextField directorTextField;
    private JTextField nameManTextField;
    private JTextField buhTextField;
    private JTextField paymentTextField;
    private JTextField markTextField;


    private static final String url = "jdbc:mysql://localhost:3306";
    private static final String user = "root";
    private static final String password = "9522403931_mkV";
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public void inputInDB() {
        long idAll=-1;
        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);
            // getting Statement object to execute query
            stmt = con.createStatement();
            try {
                stmt.executeUpdate("INSERT INTO avto_diller.producers (title, address, director, accountant, productMark, bankDetails) " +
                        "VALUE ('" + nameManTextField.getText() + "','" + addressTextField.getText() + "','" + directorTextField.getText()
                        + "','" + buhTextField.getText() + "','" + markTextField.getText() + "','" + paymentTextField.getText() + "')");
            } finally {

            }
            try {

                rs = stmt.executeQuery("SELECT id_producer FROM avto_diller.producers");
                while (rs.next()) {
                    idAll = rs.getLong(1);
                }
            }
            finally {
                rs.close();
            }
            try {
                stmt.executeUpdate("INSERT INTO avto_diller.contracts (contractNumver, dataZakl, shortText, peculiarities, client_id, producer_id, summa, product_id) " +
                        "VALUE ('" + contractTextField.getText() + "','" + dataTextField.getText() + "','" + shortTextField.getText()
                        + "','" + specialTextField.getText() + "'," + idAll + "," +  idAll + ",'" + priceTextField.getText() + "'," + idAll + ")");
            } finally {

            }
            try {
                stmt.executeUpdate("INSERT INTO avto_diller.clients (lastname, name, patronymic, payment, tel_number, email_client, adress, note, contract_id) " +
                        "VALUE ('" + lastTextField.getText() + "','" + nameTextField.getText() + "','" + OtchTextField.getText()
                        + "','" + bankTextField.getText() + "','" + phoneTextField.getText() + "','" + emailTextField.getText()
                        + "','" + addressClientTextField.getText()+ "','" + noteTextField.getText() + "'," + idAll +  ")");
            } finally {

            }
            //enum('ожидает оплаты', 'обработка', 'оплачено', 'ошибка оплаты')
            try {
                stmt.executeUpdate("INSERT INTO avto_diller.products (name, price, contractNumber, vinNubmer, enginePower, engineVolume, fuel, transmission, fuelConsumption, maxSpeed, trunkVolume, color, bodyType, bill_id) " +
                        "VALUE ('" + modelTextField.getText() + "','" + priceTextField.getText() + "','" + contractTextField.getText()
                        + "','" + VINTextField.getText() + "','" + powerTextField.getText() + "','" + volumeTextField.getText()
                        + "','" + fuelTextField.getText() + "','" + KPPTextField.getText() + "','" + fuelRashTextField.getText()
                        + "','" + maxSpeedTextField.getText() + "','" + volumeBagTextField.getText() + "','" + colorTextField.getText()
                        + "','" + bodyTypeComboBox.getSelectedItem().toString() + "'," + idAll + ")");
            } finally {

            }
            try {
                stmt.executeUpdate("INSERT INTO avto_diller.bills (nameProd, billNumber, contractNumber, saleDate, arrivalDate, summa, transitStatus, paymentStatus) " +
                        "VALUE ('" + modelTextField.getText() + "','" + billTextField.getText() + "','" + contractTextField.getText()
                        + "','" + paymentDataTextField.getText() + "','" + deliveryDataTextField.getText() + "','" + priceTextField.getText()
                        + "','" + deliverStatusComboBox.getSelectedItem().toString() + "','" + payStatusComboBox.getSelectedItem().toString() + "')");
            } finally {

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
    }


    public addForm(long idAcc){
        this.setContentPane(addPanel);
        this.setVisible(true);
        this.setSize(1800, 1000);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputInDB();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                workWithTableForm workWithTableForm = new workWithTableForm(idAcc);
                setVisible(false);
            }
        });
    }
}
