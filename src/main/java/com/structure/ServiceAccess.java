package com.structure;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.sql.Connection;

public class ServiceAccess {

    public static Connection connection =DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/sonoo","root","h4rnas");
    public static boolean addAccount(Account account, Employee owner){
        if (existingUser(account))
            return false;
        account.setOwner(owner);
        accounts.add(account);
        return true;
    }

    private static boolean existingUser(Account checked){
        for (Account existing : accounts) {
            if (existing.getLogin().equals(checked.getLogin()))
                return true;
        }
        return false;
    }

    public static Employee accountInBase(Account checked){
        for (Account existing : accounts) {
            if(checked.equals(existing))
                return existing.getOwner();
        }
        return null;
    }

}
