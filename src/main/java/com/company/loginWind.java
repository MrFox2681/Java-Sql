package com.company;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;


public class loginWind extends JFrame {
    private JPasswordField PasswordField;
    private JPanel panel1;
    private JTextField LoginField;
    private JButton loginButton;
    private JLabel errorlabel;
    private JLabel avtorizlabel;
    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:mysql://localhost:3306";
    private static final String user = "root";
    private static final String password = "9522403931_mkV";
    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;


    private static long authorization(String login, String passwordForm) {
        long idAccount = 0;
        try {


            // открытие и подключение MySQL server
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            try {
                rs = stmt.executeQuery("SELECT id " +
                        "FROM avto_diller.accaunt " +
                        "WHERE login = '" + login + "'");//SELECT запрос на получение id записи пользователя по логину
                while (rs.next())
                    idAccount = (rs.getLong(1));
            } finally {
                rs.close();
            }
            String passwordTable = "";
            try {
                rs = stmt.executeQuery("SELECT passw " +
                        "FROM avto_diller.accaunt " +
                        "WHERE id = " + idAccount);//SELECT запрос на получение пароля записи пользователя его id
                while (rs.next())
                    passwordTable = rs.getString(1);
            } finally {
                rs.close();
            }
            if (idAccount != 0) {
                if (passwordForm.equals(passwordTable)) {
                    return idAccount;
                } else {
                    return 0;
                }
            } else {
                return 0;
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

    private int checkStatus(long idAccount) {//проверка статуса учетной записи пользователя
        int typeCode = 0;
        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);
            // getting Statement object to execute query
            stmt = con.createStatement();
            // executing SELECT query
            String stateAcc = "";
            try {
                rs = stmt.executeQuery("SELECT state FROM avto_diller.accaunt WHERE id = " + idAccount);
                while (rs.next())
                    stateAcc = rs.getString("state");
            } finally {
                rs.close();
            }
            //enum('disabled', 'user', 'admin')
            if (stateAcc.equals("admin"))
                typeCode = 1;
            else if (stateAcc.equals("user"))
                typeCode = 2;
            else
                typeCode = 3;
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

        return typeCode;
    }


    public loginWind() {
        this.setContentPane(panel1);
        this.setVisible(true);
        this.setSize(640, 480);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String checkLogin = new String(LoginField.getText());
                String checkPassword = String.copyValueOf(PasswordField.getPassword());
                long idAcc = authorization(checkLogin, checkPassword);
                if (idAcc != 0) {
                    int statusType = checkStatus(idAcc);
                    mainmenu startForm;
                    adminPanelForm startAdminForm;
                    if (statusType == 1) {
                        startForm = new mainmenu(idAcc);
                        setVisible(false);
                    }
                    else if (statusType == 2) {
                        startForm = new mainmenu(idAcc);
                        setVisible(false);
                    }
                    else {
                        errorlabel.setVisible(true);
                        errorlabel.setText("Ваша учетная запись отключена ;(");
                    }
                } else {
                    errorlabel.setVisible(true);
                }

            }
        });
    }
}
