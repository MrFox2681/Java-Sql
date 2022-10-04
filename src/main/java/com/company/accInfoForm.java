package com.company;

import javax.swing.*;
import java.sql.*;

public class accInfoForm extends JFrame{
    private JButton carButton;
    private JButton Button2;
    private JButton clientsButton;
    private JButton dogovorButton;
    private JButton workWithTableButton;
    private JButton accauntButton;
    private JButton exitButton;
    private JButton changePasswordButton;
    private JButton изменениеЗаписейButton;
    private JPasswordField actualPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField repeatNewPasswordField;
    private JLabel accName;
    private JPanel accInfoPanel;
    private JLabel errorMessage;
    private static final String url = "jdbc:mysql://localhost:3306";
    private static final String user = "root";
    private static final String SQLPassword = "9522403931_mkV";
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    private String takeLogin(long idACC)
    {
        String login = "error";
        int typeCode = 0;
        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, SQLPassword);
            // getting Statement object to execute query
            stmt = con.createStatement();
            // executing SELECT query
            try {
                rs = stmt.executeQuery("SELECT login " +
                        "FROM avto_diller.accaunt " +
                        "WHERE id  = " + idACC);
                while (rs.next()) {
                    {

                        login = (rs.getString(1));

                    }
                }
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
        }
        return login;
    }


    public accInfoForm(long idAcc) {
        this.setContentPane(accInfoPanel);
        this.setVisible(true);
        this.setSize(640, 480);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        accName.setText(takeLogin(idAcc));
    }
}
