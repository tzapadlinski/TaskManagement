package com.structure;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ServiceAccess {
    private ArrayList<Account> accounts;

    ServiceAccess(){
        accounts = new ArrayList<>();
    }

    //TODO disable adding accounts with same logins
    public boolean addAccount(Account account, Employee employee){
        accounts.add(account);
        return true;
    }

    public Employee accountInBase(Account checked){
        for (Account existing:accounts) {
            if(checked.equals(existing))
                return existing.getOwner();
        }
        return null;
    }

}
