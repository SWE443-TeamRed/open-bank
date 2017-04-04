package org.sdmlib.openbank;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by daniel on 3/29/17.
 */
public class JsonPersistencyTest {

    /*
        Persistancy test
        User's account is persisted as is, without any tran
     */
    @Test
    public void test1() throws Exception {
        JsonPersistency jsonPersistency = new JsonPersistency();


        Account accountBeforeJson = new Account()
                .withOwner(new User()
                        .withUserID("1234").withName("Tina"))
                .withBalance(140.00)
                .withCreationdate(new Date())
                .withCredit(new Transaction()
                        .withAmount(100.00)
                        .withDate(new Date())
                        .withNote("Deposit"));

        System.out.println("*********************************toJson*********************************");
        jsonPersistency.toJson(accountBeforeJson);

        Account accountAfterJson = jsonPersistency.fromJson();

        System.out.println();
        System.out.println("*******************************fromJson*********************************");
        System.out.println(accountAfterJson.toString());
        System.out.println("UserID: " + accountAfterJson.getOwner().getUserID());
        System.out.println("Name: " + accountAfterJson.getOwner().getName());
        System.out.println("Credit: " + accountAfterJson.getCredit().toString());
    }
}