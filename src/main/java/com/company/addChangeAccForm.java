package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class addChangeAccForm extends JFrame {
    private JTextField textFieldLogin;
    private JComboBox userTypeComboBox;
    private JPasswordField passwordFieldRepeat;
    private JPasswordField passwordFieldMain;
    private JButton EnterButton;
    private JPanel editAddPanel;
    private JLabel titleLabel;
    private JLabel errorLabel;
    private JButton backButton;
    private static final String url = "jdbc:mysql://localhost:3306";
    private static final String user = "root";
    private static final String SQLPassword = "9522403931_mkV";
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    private void inDB(String login, String password, int userState, int typeOperation, int rowNumber)
    {
        int typeCode = 0;
        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, SQLPassword);
            // getting Statement object to execute query
            stmt = con.createStatement();
            // executing SELECT query
            long acc_id = -1;
            try {
                rs = stmt.executeQuery("SELECT id " +
                        "FROM avto_diller.accaunt " +
                        "WHERE login = '" + login + "'");
                while (rs.next()) {
                    {
                        System.out.println(login + " " + password + "  " + userState + " " + acc_id);
                        acc_id = (rs.getLong(1));
                        System.out.println(login + " " + password + "  " + userState + " " + acc_id);
                    }
                }
            } finally {
                rs.close();
            }
            if (!checkPassword())
            {
                errorLabel.setVisible(true);
                return;
            }
            else {
                errorLabel.setVisible(false);
            }
            System.out.println(login + " " + password + "  " + userState);
            if (typeOperation == 1)
            {
                try {
                    if (userState == 1)
                        stmt.executeUpdate("INSERT INTO avto_diller.accaunt (login, passw, state) " +
                                "VALUE ('" + login + "','" + password + "'," + "'admin'" +")");
                    else if (userState == 0)
                        stmt.executeUpdate("INSERT INTO avto_diller.accaunt (login, passw) " +
                                "VALUE ('" + login + "','" + password +"')");
                    else if (userState == 2)
                        stmt.executeUpdate("INSERT INTO avto_diller.accaunt (login, passw, state) " +
                                "VALUE ('" + login + "','" + password + "'," + "'disabled'" +")");
                } finally {

                }
                //enum('disabled', 'user', 'admin')
                errorLabel.setVisible(true);
                errorLabel.setText("Запись успешно создана");
            }
            else if (typeOperation == 2)
            {
                System.out.println(login + " " + password + "  " + userState + " " + rowNumber);
                if (rowNumber == -1)
                    return;
                try {
                    if (userState == 1)
                        stmt.executeUpdate("UPDATE avto_diller.accaunt " +
                                "SET login ='" + login +"', passw = '" + password + "', state = 'admin' " +
                                "WHERE id ="  + (rowNumber + 1));
                    else if(userState == 0)
                        stmt.executeUpdate("UPDATE avto_diller.accaunt " +
                                "SET login ='" + login +"', passw = '" + password + "', state = 'user' " +
                                "WHERE id ="  + (rowNumber + 1));
                    else if (userState == 2)
                        stmt.executeUpdate("UPDATE avto_diller.accaunt " +
                                "SET login ='" + login +"', passw = '" + password + "', state = 'disabled' " +
                                "WHERE id ="  + (rowNumber + 1));
                } finally {

                }
                //enum('disabled', 'user', 'admin')
                errorLabel.setVisible(true);
                errorLabel.setText("Запись успешно изменена");
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

    private boolean checkPassword()
    {
        if (String.copyValueOf(passwordFieldMain.getPassword()).equals(String.copyValueOf(passwordFieldRepeat.getPassword())))
        {
            return true;
        }
        return false;
    }


    private void addUser()
    {
        String loginTmp = textFieldLogin.getText();
        String passwordTmp = textFieldLogin.getText();
        int typeTmp = userTypeComboBox.getSelectedIndex();
        inDB(loginTmp,passwordTmp, typeTmp, 1, -1);
    }

    private void changeUser(int rowNumber)
    {
        String loginTmp = textFieldLogin.getText();
        String passwordTmp = textFieldLogin.getText();
        int typeTmp = userTypeComboBox.getSelectedIndex();
        inDB(loginTmp,passwordTmp, typeTmp, 2, rowNumber);
    }

    public addChangeAccForm(int actionType, int rowNumber,long idAcc)
    {
        this.setContentPane(editAddPanel);
        this.setVisible(true);
        this.setSize(500, 300);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        if (actionType == 0)
        {
            EnterButton.setText("Создать запись");
            titleLabel.setText("Создание пользователя");
            EnterButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addUser();
                }
            });

        } else if (actionType == 1)
        {
            titleLabel.setText("Изменение пользователя");
            EnterButton.setText("Применить изменения");
            EnterButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    changeUser(rowNumber);
                }
            });

        }
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminPanelForm adminForm = new adminPanelForm(idAcc);
                setVisible(false);
            }
        });


    }
}
