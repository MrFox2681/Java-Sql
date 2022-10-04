package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class adminPanelForm  extends JFrame{
    private JTable accountTabel;
    private JPanel adminPanel;
    private JButton deleteButton;
    private JButton changeButton;
    private JButton addButton;
    private JButton carButton;
    private JButton Button2;
    private JButton clientsButton;
    private JButton dogovorButton;
    private JButton exitButton;
    private JButton workWithTableButton;
    private JButton deleteAllTableButton;
    private JButton creatTabelsButton;
    private JPasswordField toChangeDBPasswordField;


    private static final String url = "jdbc:mysql://localhost:3306";
    private static final String user = "root";
    private static final String password = "9522403931_mkV";
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public void createTBL(){
        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);
            // getting Statement object to execute query
            stmt = con.createStatement();
            // executing SELECT query
            rs = stmt.executeQuery("create table accaunt\n" +
                    "(\n" +
                    "    id    int unsigned auto_increment\n" +
                    "        primary key,\n" +
                    "    login varchar(30)                                       not null,\n" +
                    "    passw varchar(20)                                       not null,\n" +
                    "    state enum ('disabled', 'user', 'admin') default 'user' not null,\n" +
                    "    constraint loginUN\n" +
                    "        unique (login)\n" +
                    ");\n" +
                    "\n" +
                    "create table producers\n" +
                    "(\n" +
                    "    id_producer int unsigned auto_increment\n" +
                    "        primary key,\n" +
                    "    title       varchar(30)  not null,\n" +
                    "    address     varchar(140) not null,\n" +
                    "    director    varchar(50)  not null,\n" +
                    "    accountant  varchar(50)  not null,\n" +
                    "    productMark varchar(30)  not null,\n" +
                    "    bankDetails varchar(20)  not null\n" +
                    ");\n" +
                    "\n" +
                    "create table contracts\n" +
                    "(\n" +
                    "    id_contract    int unsigned auto_increment\n" +
                    "        primary key,\n" +
                    "    contractNumver varchar(12)            not null,\n" +
                    "    dataZakl       date                   not null,\n" +
                    "    shortText      varchar(60) default '' not null,\n" +
                    "    payment        varchar(20)            null,\n" +
                    "    peculiarities  varchar(50) default '' not null,\n" +
                    "    client_id      int unsigned           not null,\n" +
                    "    producer_id    int unsigned           not null,\n" +
                    "    summa          varchar(9)             not null,\n" +
                    "    product_id     int unsigned           not null,\n" +
                    "    constraint contracts_client_id_uindex\n" +
                    "        unique (client_id),\n" +
                    "    constraint contracts_contractNumver_uindex\n" +
                    "        unique (contractNumver),\n" +
                    "    constraint contracts_producer_id_uindex\n" +
                    "        unique (producer_id),\n" +
                    "    constraint product_id\n" +
                    "        unique (product_id),\n" +
                    "    constraint contracts_producers_id_producer_fk\n" +
                    "        foreign key (producer_id) references producers (id_producer)\n" +
                    ");\n" +
                    "\n" +
                    "create table clients\n" +
                    "(\n" +
                    "    id_client    int unsigned auto_increment\n" +
                    "        primary key,\n" +
                    "    lastname     varchar(30)             not null,\n" +
                    "    name         varchar(30)             not null,\n" +
                    "    patronymic   varchar(30)  default '' not null,\n" +
                    "    payment      varchar(20)             not null,\n" +
                    "    naming       varchar(30)             null,\n" +
                    "    tel_number   varchar(12)             not null,\n" +
                    "    email_client varchar(30)             not null,\n" +
                    "    adress       varchar(140)            not null,\n" +
                    "    note         varchar(250) default '' not null,\n" +
                    "    contract_id  int unsigned            not null,\n" +
                    "    constraint clients_contracts_client_id_fk\n" +
                    "        foreign key (id_client) references contracts (client_id)\n" +
                    "            on update cascade on delete cascade\n" +
                    ");\n" +
                    "\n" +
                    "create index contract_id\n" +
                    "    on clients (contract_id);\n" +
                    "\n" +
                    "create table products\n" +
                    "(\n" +
                    "    id_products     int unsigned auto_increment\n" +
                    "        primary key,\n" +
                    "    name            varchar(40)                                                         not null,\n" +
                    "    price           varchar(9)                                                          not null,\n" +
                    "    contractNumber  varchar(12)                                                         not null,\n" +
                    "    vinNubmer       varchar(17)                                                         not null,\n" +
                    "    enginePower     varchar(6)                                                          not null,\n" +
                    "    engineVolume    varchar(8)                                                          not null,\n" +
                    "    fuel            varchar(20) default 'Бензин'                                        not null,\n" +
                    "    transmission    varchar(10)                                                         not null,\n" +
                    "    fuelConsumption float                                                               not null,\n" +
                    "    maxSpeed        varchar(3)                                                          not null,\n" +
                    "    trunkVolume     varchar(4)                                                          not null,\n" +
                    "    color           varchar(20)                                                         not null,\n" +
                    "    bodyType        enum ('купе', 'седан', 'кроссовер', 'джип', 'кабриолет', 'хэтчбэк') not null,\n" +
                    "    bill_id         int unsigned                                                        not null,\n" +
                    "    constraint VIN\n" +
                    "        unique (vinNubmer),\n" +
                    "    constraint products_bill_id_uindex\n" +
                    "        unique (bill_id),\n" +
                    "    constraint products_contractNumber_uindex\n" +
                    "        unique (contractNumber),\n" +
                    "    constraint products_ibfk_1\n" +
                    "        foreign key (id_products) references contracts (product_id)\n" +
                    "            on update cascade on delete cascade\n" +
                    ");\n" +
                    "\n" +
                    "create table bills\n" +
                    "(\n" +
                    "    id_bills       int unsigned auto_increment\n" +
                    "        primary key,\n" +
                    "    nameProd       varchar(40)                                                                                not null,\n" +
                    "    billNumber     varchar(20)                                                                                not null,\n" +
                    "    contractNumber varchar(12)                                                                                not null,\n" +
                    "    saleDate       date                                                                                       not null,\n" +
                    "    arrivalDate    date                                                                                       null,\n" +
                    "    summa          varchar(9)                                                        default '0'              not null,\n" +
                    "    transitStatus  enum ('создано', 'в пути', 'отгружено')                           default 'создано'        not null,\n" +
                    "    paymentStatus  enum ('ожидает оплаты', 'обработка', 'оплачено', 'ошибка оплаты') default 'ожидает оплаты' not null,\n" +
                    "    constraint bills_contractNumber_uindex\n" +
                    "        unique (contractNumber),\n" +
                    "    constraint bills_products_bill_id_fk\n" +
                    "        foreign key (id_bills) references products (bill_id)\n" +
                    ");\n");
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try { con.close(); } catch(SQLException se) { /*can't do anything*/ }
                try { stmt.close(); } catch(SQLException se) { /*can't do anything*/ }
                    try { rs.close(); } catch(SQLException se) { /*can't do anything*/ }
                    }
    }


    public void dropDB(){
        try {

            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);
            // getting Statement object to execute query
            stmt = con.createStatement();
            // executing SELECT query

            rs = stmt.executeQuery("DROP DATABASE avto_diller");

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try { con.close(); } catch(SQLException se) { /*can't do anything*/ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything*/ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything*/ }
        }
    }



    public int outputInTable() {
        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);
            // getting Statement object to execute query
            stmt = con.createStatement();
                 try {

                    rs = stmt.executeQuery("SELECT * FROM avto_diller.accaunt");

                DefaultTableModel tblModel = new DefaultTableModel();
                tblModel.addColumn("login");
                tblModel.addColumn("passw");
                tblModel.addColumn("contractNumber");
                tblModel.addRow(new Object[] {"Логин", "Пароль", "Тип аккаунта"});
                while (rs.next()) {
                    String field1 = rs.getString("login");
                    String field2 = rs.getString("passw");
                    String field3 = rs.getString("state");
                    String[] DBtable = {field1, field2, field3};
                    tblModel.addRow(DBtable);
                }
                accountTabel.setModel(tblModel);
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
private void deleteRowFromDB(String loginToDel)
{
    try {
        long idToDelet = -1;
        // opening database connection to MySQL server
        con = DriverManager.getConnection(url, user, password);
        // getting Statement object to execute query
        stmt = con.createStatement();
        try {
            stmt.executeUpdate("DELETE FROM avto_diller.accaunt " +
                    "WHERE login = '" + loginToDel + "','" + "'");
        }
        finally {

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
    public adminPanelForm(long idAcc){
        this.setContentPane(adminPanel);
        this.setVisible(true);
        this.setSize(1024, 640);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        outputInTable();
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int azaz = accountTabel.getSelectedRow();
                System.out.println(azaz);
                if (azaz != -1)
                {
                    addChangeAccForm cars = new addChangeAccForm(1, azaz, idAcc);
                    setVisible(false);
                }
            }
        });


        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addChangeAccForm cars = new addChangeAccForm(0, -1, idAcc);
                setVisible(false);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int azaz = accountTabel.getSelectedRow();
                System.out.println(azaz);
                if (azaz != -1)
                {
                    String loginFromForm = (accountTabel.getValueAt(azaz,1)).toString();
                    deleteRowFromDB(loginFromForm);
                    outputInTable();
                }
            }
        });

        creatTabelsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               String passwordToChange = String.copyValueOf(toChangeDBPasswordField.getPassword());
               if (passwordToChange.equals("aZaZhahaTeNoV14882"))
                   createTBL();
            }
        });

        deleteAllTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String passwordToChange = String.copyValueOf(toChangeDBPasswordField.getPassword());
                if (passwordToChange.equals("aZaZhahaTeNoV14882"))
                    createTBL();
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
                workWithTableForm workWithTableForm = new workWithTableForm(idAcc);
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
