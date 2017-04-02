import org.junit.Before;
import org.junit.Test;
import org.sdmlib.openbank.Account;
import org.sdmlib.openbank.JsonPersistency;
import org.sdmlib.openbank.Transaction;
import org.sdmlib.openbank.User;

import java.util.Date;
import static org.junit.Assert.*;

/**
 * Created by FA on 3/28/2017.
 */
public class TransactionTest {
    Transaction trans;

    @Before
    public void setUp() throws Exception{
        // initiate transaction class
        trans = new Transaction();
    }

    @Test(expected=IllegalArgumentException.class)
    // this will test for negative value in setAmount
    // it will throw an IllegalArgumentException if the value is negative
    public void testSetAmountNegative()throws Exception{
        trans.setAmount(-5);
    }

    @Test
    // setAmount and get the amount to make sure you get the correct amount
    public void setgetAmount(){
        trans.setAmount(50.55);
        assertTrue(50.55 == trans.getAmount());
    }

    @Test
    // setDate and get the date to make sure you get the correct date
    public void setgetDate(){
        Date dt = new Date("03/19/2017");

        // set date
        trans.setDate(dt);
        assertTrue(dt == trans.getDate());
    }

    @Test(expected=IllegalArgumentException.class)
    // setDate with null to see if it will throw IllegalArgumentException
    public void setgetDateTestNULL(){
        Date dt = new Date(null);

        // set date with null
        trans.setDate(dt);
    }

    @Test
    // setTime and get the time to make sure you get the correct time
    public void setgetTime(){
        Date dt = new Date("03/19/2017 23:13:26");

        // set time
        trans.setTime(dt);
        assertTrue(dt == trans.getTime());
    }

    @Test(expected=IllegalArgumentException.class)
    // setTime with null to see if it will throw IllegalArgumentException
    public void setgetTimeTestNULL(){
        Date dt = new Date(null);

        // set date with null
        trans.setDate(dt);
    }

    @Test
    // setNote and get the note to make sure you get the correct note
    public void setgetNote(){
        String nt = "Testing Notes..";
        // set note
        trans.setNote(nt);
        assertTrue(nt == trans.getNote());
    }

    @Test
    // setNote and get the note to make sure you get the correct value
    public void setgetNoteNULL(){
        String nt = null;
        // set note
        trans.setNote(nt);
        assertTrue(nt == trans.getNote());
    }

    @Test
    // setNote and get the note to make sure you get the correct value
    public void setgetNoteEmpty(){
        String nt = " ";
        // set note
        trans.setNote(nt);
        assertTrue(nt == trans.getNote());
    }

    @Test
    //
    public void testJSON(){
       Account accnt = new Account();
       JsonPersistency json = new JsonPersistency();

        Transaction trans2 = new Transaction();
        Transaction trans3 = new Transaction();

        User usr1 = new User()
                .withName("Tom")
                .withUserID("tb12");

        accnt = new Account()
                .withOwner(usr1)
                .withAccountnum(1);

        Date dt = new Date("03/19/2017");
        Date dtime = new Date("03/19/2017 13:13:26");

        trans.setAmount(50.00);
        // set date
        trans.setDate(dt);
        // set time
        trans.setTime(dtime);
        trans.setNote("Deposit Trans 1");

        //*** Second Transaction
        dt = new Date("03/19/2017");
        dtime = new Date("03/19/2017 13:16:30");

        trans2.setAmount(20.00);
        // set date
        trans2.setDate(dt);
        // set time
        trans2.setTime(dtime);
        trans2.setNote("Deposit Trans 2");

        //*** Third Transaction
        dt = new Date("03/19/2017");
        dtime = new Date("03/19/2017 13:20:30");

        trans3.setAmount(40.00);
        // set date
        trans3.setDate(dt);
        // set time
        trans3.setTime(dtime);
        trans3.setNote("Withdraw Trans 3");


        Account accountBeforeJson = new Account().withOwner(usr1)
                .withBalance(550.00).withCreationdate(dt)
                .withCredit(trans);

        accountBeforeJson.withBalance(570.00).withCreationdate(dt);
        accountBeforeJson.withCredit(trans2);

        accountBeforeJson.withBalance(540.00).withCreationdate(dt);
        accountBeforeJson.withDebit(trans3);

        System.out.println("*********************************toJson*********************************");
        json.toJson(accountBeforeJson);

        Account accountAfterJson = json.fromJson();

        System.out.println();
        System.out.println("*******************************fromJson*********************************");
        System.out.println(accountAfterJson.toString());
        System.out.println("UserID: " + accountAfterJson.getOwner().getUserID());
        System.out.println("Name: " + accountAfterJson.getOwner().getName());
        System.out.println("Credit: Amount:" + accountAfterJson.getCredit().toString() + ". Time:" + accountAfterJson.getCredit().getDate().toString());
        System.out.println("Debit: " + accountAfterJson.getDebit().toString());

        System.out.println("Balance: " + accountAfterJson.getBalance());
       /*
        Transaction trans2 = new Transaction();

       User usr1 = new User()
                .withName("Tom")
                .withUserID("tb12");

       accnt = new Account()
                .withOwner(usr1)
                .withAccountnum(1);

       Date dt = new Date("03/19/2017");
       Date dtime = new Date("03/19/2017 13:13:26");

       trans.setAmount(50.55);
       // set date
       trans.setDate(dt);
       // set time
       trans.setTime(dtime);
       trans.setNote("TEST1");

       //*** Second Transaction
        dt = new Date("03/19/2017");
        dtime = new Date("03/19/2017 13:16:30");

        trans.setAmount(20.00);
        // set date
        trans.setDate(dt);
        // set time
        trans.setTime(dtime);
        trans.setNote("TEST Trans 2");

       json.toJson(accnt);
        //json.toJson();
*/
    }

}
