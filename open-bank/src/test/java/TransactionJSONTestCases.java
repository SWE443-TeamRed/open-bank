import org.junit.Before;
import org.junit.Test;
import org.sdmlib.openbank.*;
import org.sdmlib.storyboards.Storyboard;

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
/*
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
*/
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
/*
        accountBeforeJson.withBalance(570.00).withCreationdate(dt);
        accountBeforeJson.withCredit(trans2);

        accountBeforeJson.withBalance(540.00).withCreationdate(dt);
        accountBeforeJson.withDebit(trans3);
*/
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

        System.out.println("UserID: " + accountAfterJson.getOwner().getUserID().toString());
        System.out.println("Name: " + accountAfterJson.getOwner().getName().toString());
        assertEquals(usr1.getUserID().toString(),accountAfterJson.getOwner().getUserID().toString());
        assertEquals(usr1.getName().toString(),accountAfterJson.getOwner().getName().toString());
        assertTrue(540 == accountAfterJson.getBalance());
    }

    //******** ACCOUNT Test Cases ***********************
    User peter = new User()
            .withName("Peter")
            .withUserID("peter1")
            .withPassword("password")
            .withIsAdmin(false);
    Account account1 = new Account()
            .withAccountnum(1)
            .withIsConnected(true)
            .withOwner(peter)
            .withBalance(100)
            .withCreationdate(new Date())
            .withCredit()
            .withDebit();
    Storyboard storyboard = new Storyboard();

    @Test(expected = IllegalArgumentException.class)
    public void testwithBalance(){
        Account accountWithNegative = new Account().withBalance(-100);
    }
    /**
     *
     * @see <a href='../../../doc/2.html'>2.html</a>
     */
    @Test
    public void testgetBalanceAndSetBalance() {
        System.out.println(account1.getBalance());
        storyboard.assertEquals("Balance should be the same", 100.0, account1.getBalance());
    }

    @Test
    public void testAccountToString(){
        storyboard.assertEquals("Check for is the toString is correct","100.0 1 " + account1.getCreationdate(),account1.toString());
    }

    @Test
    public void testgetAccountnum(){
        storyboard.assertEquals("Check for if getAccountnum works", 1,account1.getAccountnum());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testwithAccountnumAndsetAccountnum(){
        Account accountNumWithNegatve = new Account().withAccountnum(-1);
    }

    /* @Test
     public void testgetCreationDate(){
         Date currentDate = new Date();
         storyboard.assertEquals("Testing if date is working",new Date(),account1.getCreationdate());
     }*/
    @Test
    public void testgetOwner(){
        storyboard.assertEquals("Testing if proper owner is in account",peter,account1.getOwner());
    }
    @Test
    public void testsetOwner(){
        Account accountTestOwner = new Account();
        storyboard.assertEquals("Setting owner with null value should return false",false,accountTestOwner.setOwner(null));
    }
    /* @Test
     public void testCreateOwner(){
         User accountTestCreateOwner = new Account().withOwner(peter).createOwner();
         storyboard.assertEquals("Testing createOwner",peter.getName(),accountTestCreateOwner.getName());
     }*/
    @Test
    public void testgetCredit(){

        Account creditAccount = new Account().withCredit();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransferToAccount(){
        Account receivingAccount = new Account().withBalance(100);
        account1.transferToAccount(-1,receivingAccount,"Testing negative");
    }
    @Test(expected = IllegalArgumentException.class)
    public void testTransferToAccount2(){
        Account receivingAccount = new Account().withBalance(100);
        account1.transferToAccount(100,null,"Testing null account");
    }
    /*@Test
    public void testTransferToAccount3(){
        User victor = new User()
                .withName("Victor")
                .withUserID("peter1")
                .withPassword("password")
                .withIsAdmin(false);
        Account receivingAccount = new Account()
                .withAccountnum(2)
                .withIsConnected(true)
                .withOwner(victor)
                .withBalance(100)
                .withCreationdate(new Date())
                .withCredit()
                .withDebit();
        storyboard.assertEquals("This should return false, since balance in account1 is 100",
                false,account1.transferToAccount(110,receivingAccount,"Testing amount greater than balance in account1"));
    }*/

    @Test
    public void testTransferToAccount4(){
        User victor = new User()
                .withName("Victor")
                .withUserID("peter1")
                .withPassword("password")
                .withIsAdmin(false);
        Account receivingAccount = new Account()
                .withAccountnum(2)
                .withIsConnected(true)
                .withOwner(victor)
                .withBalance(100)
                .withCreationdate(new Date())
                .withCredit()
                .withDebit();
        User tina = new User()
                .withName("Tina")
                .withUserID("peter1")
                .withPassword("password")
                .withIsAdmin(false);
        Account originAccount = new Account()
                .withAccountnum(1)
                .withIsConnected(true)
                .withOwner(victor)
                .withBalance(100)
                .withCreationdate(new Date())
                .withCredit()
                .withDebit();
        originAccount.transferToAccount(50,receivingAccount,"Testing balance after transfer amount account");
        System.out.println(originAccount.getBalance());
        System.out.println(receivingAccount.getBalance());
    }
    @Test
    public void testReceiveFunds(){

        //storyboard.assertEquals("Testing receive funds",true,account1.receiveFunds(100,"Testing receive funds"));

    }
    @Test
    public void testRecordTransaction(){
        Account recordAccount = new Account();
    }

}
