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

        /*Account transactionAccount = new Account().withOwner(new User().withUserID("1235").withName("Brad"))
                .withBalance(198.50).withCreationdate("3/25/2017");*/

        Account accountBeforeJson = new Account().withOwner(new User().withUserID("1234").withName("Tina"))
                .withBalance(140.00).withCreationdate("3/29/2017")
                .withCredit(new Transaction().withAmount(100.00).withDate("3/30/2017").withNote("Deposit").withTime("10:15AM"));

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