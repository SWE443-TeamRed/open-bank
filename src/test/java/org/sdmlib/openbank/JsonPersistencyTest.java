package org.sdmlib.openbank;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by daniel on 3/29/17.
 */
public class JsonPersistencyTest {
    @Test
    public void test1() throws Exception {
        JsonPersistency jsonPersistency = new JsonPersistency();

        Account transactionAccount = new Account().withOwner(new User().withUserID("1235").withName("Brad"))
                .withBalance(198.50).withCreationdate("3/25/2017");

        Account accountBeforeJson= new Account().withOwner(new User().withUserID("1234").withName("Daniel"))
                .withBalance(100.50).withCreationdate("3/29/2017")
                .withCredit(new Transaction().withAmount(25.89).withDate("3/30/2017").withNote("For parking ticket").withTime("10:15AM"));

        jsonPersistency.toJson(accountBeforeJson);

        Account accountAfterJson = jsonPersistency.fromJson();

        System.out.println(accountAfterJson.toString());
        System.out.println("UserID: " + accountAfterJson.getOwner().getUserID());
        System.out.println("Name: " + accountAfterJson.getOwner().getName());
        System.out.println("Credit: " + accountAfterJson.getCredit().toString());
    }

}