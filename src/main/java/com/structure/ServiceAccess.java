package com.structure;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ServiceAccess {
	
    private ArrayList<Account> accounts;

    ServiceAccess(){
        accounts = new ArrayList<>();
    }

    public boolean addAccount(Account account, Employee owner){
        if (existingUser(account))
            return false;
        account.setOwner(owner);
        accounts.add(account);
        return true;
    }

    private boolean existingUser(Account checked){
        for (Account existing : accounts) {
            if (existing.getLogin().equals(checked.getLogin()))
                return true;
        }
        return false;
    }

    public Employee accountInBase(Account checked){
        for (Account existing : accounts) {
            if(checked.equals(existing))
                return existing.getOwner();
        }
        return null;
    }

}
