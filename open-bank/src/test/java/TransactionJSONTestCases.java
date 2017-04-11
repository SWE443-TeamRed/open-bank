package java;
import org.junit.Before;
import org.junit.Test;
import org.sdmlib.openbank.Transaction;
import org.sdmlib.openbank.TransactionTypeEnum;
import static org.junit.Assert.*;
import java.util.Date;

/**
 * Created by FA on 4/10/2017.
 */
public class TransactionJSONTestCases {
    Transaction trans;

    @Before
    public void setUp() throws Exception{
        // initiate transaction class
        trans = new org.sdmlib.openbank.Transaction();
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
        trans.setCreationdate(dt);
        assertTrue(dt == trans.getCreationdate());
    }

    @Test(expected=IllegalArgumentException.class)
    // setDate with null to see if it will throw IllegalArgumentException
    public void setgetDateTestNULL(){
        Date dt = new Date(null);

        // set date with null
        trans.setCreationdate(dt);
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

    @Test(expected=IllegalArgumentException.class)
    // setTrans Type with null should throws IllegalArgumentException exception
    public void setNullTransType(){
        // set type
        trans.setTransType(null);

    }

    @Test
    // setTrans Type and get the type to make sure you get the correct type
    public void setgetTransTypeWithdraw(){
        // set type
        trans.setTransType(org.sdmlib.openbank.TransactionTypeEnum.Withdraw);

        assertTrue(org.sdmlib.openbank.TransactionTypeEnum.Withdraw == trans.getTransType());
    }

    @Test
    // setTrans Type and get the type to make sure you get the correct type
    public void setgetTransTypeDeposit(){
        // set type
        trans.setTransType(org.sdmlib.openbank.TransactionTypeEnum.Deposit);

        assertTrue(org.sdmlib.openbank.TransactionTypeEnum.Deposit == trans.getTransType());
    }

    // JSON Test Case
    @Test(expected=NullPointerException.class)
    // set user with null should throws NullPointerException exception
    public void testtoJsonWithNULL() {
        Account accnt = new Account();
        JsonPersistency json = new JsonPersistency();

        Transaction trans = new Transaction();

        User usr1 = null;
        /*
        new User()

                .withName("tina")
                .withUserID("tina1");
*/
        accnt = new Account()
                .withOwner(usr1)
                .withAccountnum(1);

        Date dt = new Date("03/19/2017");
        Date dtime = new Date("03/19/2017 13:13:26");

        trans.setAmount(50.00);
        // set date
        trans.setCreationdate(dt);
        // set time
        //trans.setTime(dtime);
        trans.setNote("Deposit Trans 1");


        Account accountBeforeJson = new Account().withOwner(usr1)
                .withBalance(550.00).withCreationdate(dt)
                .withCredit(trans);

        accountBeforeJson.withBalance(570.00).withCreationdate(dt);
        accountBeforeJson.withCredit(trans2);

        accountBeforeJson.withBalance(540.00).withCreationdate(dt);
        accountBeforeJson.withDebit(trans3);

        json.toJson(accountBeforeJson);

    }


    @Test(expected=NullPointerException.class)
    // set user with null should throws NullPointerException exception
    public void testFromJsonWithNULL() {
        Account accnt = new Account();
        JsonPersistency json = new JsonPersistency();

        Transaction trans = new Transaction();

        User usr1 = null;

        Account accountAfterJson = json.fromJson(usr1.getUserID());
    }

    @Test
    // set user with valid user should return account object
    public void testFromJson() {
        Account accnt = new Account();
        JsonPersistency json = new JsonPersistency();

        Transaction trans = new Transaction();

        User usr1 = new User()
                .withName("tina")
                .withUserID("tina1");

        Account accountAfterJson = json.fromJson(usr1.getUserID());

        assertTrue("tina1" == accountAfterJson.getOwner().getUserID());
        assertTrue("tina" == accountAfterJson.getOwner().getName());
        assertTrue(540 == accountAfterJson.getBalance());
    }
}
