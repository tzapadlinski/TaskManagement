package com.structure;

import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.*;
import java.util.ArrayList;

public class ServiceAccess {

    public static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/accountbase","root","h4rnas");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*public static boolean addAccount(Account account, Employee owner) throws SQLException {
        if (existingUser(account))
            return false;
        account.setOwner(owner);
        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO accounts (`accounts`.`firstName`, " +
                "`accounts`.`lastName`, `accounts`.`position`, `accounts`.`username`," +
                " `accounts`.`password`) VALUES (\""+ owner.gee, \"Pudzianowski\", \"other\", \"polska\", \"gurom\");")
        return true;
    }*/



    public static Employee accountInBase(Account checked) throws  SQLException{
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM accounts WHERE userName = \""
                + checked.getLogin() + "\" AND password = \"" + checked.getPassword() + "\" ");
        if(!resultSet.next())
            return null;
        int id = resultSet.getInt("employeeID");
        String name = resultSet.getString("firstName");
        String lastName = resultSet.getString("lastName");
        String position = resultSet.getString("position");
        String logIn = resultSet.getString("userName");
        Employee ejected = null;
        if(position.equals("manager"))
            ejected = new Manager(id, name, lastName);
        else {
            switch (position) {
                case "programmer":
                    ejected = new Worker(id, name, lastName, Position.PROGRAMISTA);
                    break;
                case "tester":
                    ejected = new Worker(id, name, lastName, Position.TESTER);
                    break;
                case "other":
                    ejected = new Worker(id, name, lastName, Position.ANALITYK);
                    break;
                default:
                    return null;
            }
        }
        return ejected;
    }

}
