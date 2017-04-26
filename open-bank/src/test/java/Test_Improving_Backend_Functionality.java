import org.junit.Before;
import org.junit.Test;
import org.sdmlib.openbank.*;
import org.sdmlib.storyboards.Storyboard;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by FA on 4/11/2017.
 */
public class Test_Improving_Backend_Functionality {
    Transaction trans;

    @Before
    public void setUp() throws Exception{
        // initiate transaction class
        trans = new org.sdmlib.openbank.Transaction();
    }

    //******** TRANSACTION CLASS TESTS *****************
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
        trans.setTransType(TransactionTypeEnum.WITHDRAWAL);

        assertTrue(org.sdmlib.openbank.TransactionTypeEnum.WITHDRAWAL == trans.getTransType());
    }

    @Test
    // setTrans Type and get the type to make sure you get the correct type
    public void setgetTransTypeDeposit(){
        // set type
        trans.setTransType(TransactionTypeEnum.DEPOSIT);

        assertTrue(org.sdmlib.openbank.TransactionTypeEnum.DEPOSIT == trans.getTransType());
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

    /*************** USER Class test cases *************************
     */
    //Test for successful login
    @Test
    public void testLogin1(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
    }

    //Unsuccessful login due to incorrect username
    @Test
    public void testLogin2(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby", "BillyB$b1");
        assertFalse(bob.isLoggedIn());
    }

    //Unsuccessful login due to incorrect password
    @Test
    public void testLogin3(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b");
        assertFalse(bob.isLoggedIn());
    }

    //Unsuccessful login due to incorrect username and password
    @Test
    public void testLogin4(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby", "BillyB$b");
        assertFalse(bob.isLoggedIn());
    }

    //Null username
    @Test (expected = IllegalArgumentException.class)
    public void testLogin5(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login(null, "BillyB$b1");
    }

    //Null password
    @Test (expected = IllegalArgumentException.class)
    public void testLogin6(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", null);
    }

    //Null username and password
    @Test (expected = IllegalArgumentException.class)
    public void testLogin7(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login(null, null);
    }
/*
    //Successful logout
    @Test
    public void testLogout(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.logout();
        assertFalse(bob.isLoggedIn());
    }

    //Logout without being attempting to log in
    @Test
    public void testLogout2(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        assertFalse(bob.isLoggedIn());
        assertFalse(bob.logout());
    }

    //Logout without successfully logging in
    @Test
    public void testLogout3(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby", "BillyB$b");
        assertFalse(bob.isLoggedIn());
        assertFalse(bob.logout());
    }
    */

    //SetName without logging in
    @Test (expected = Exception.class)
    public void testSetName(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.setName("Bobby");
    }

    //Successful setName
    @Test
    public void testSetName2(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setName("Bobby");
        assertEquals("Bobby", bob.getName());
    }

    //SetName with null
    @Test (expected = IllegalArgumentException.class)
    public void testSetName3(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setName(null);
    }

    //SetName with digits
    @Test (expected = IllegalArgumentException.class)
    public void testSetName4(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setName("b0bby");
    }

    //SetName with digits
    @Test (expected = IllegalArgumentException.class)
    public void testSetName5(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setName("Bobby!");
    }

    //SetUserID
    @Test (expected = Exception.class)
    public void testSetUserID(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.setUserID("BuilderBob");
    }

    //SetUserID null
    @Test (expected = IllegalArgumentException.class)
    public void testSetUserID2(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setUserID(null);
    }

    //SetUserID to the current UserID
    @Test (expected = IllegalArgumentException.class)
    public void testSetUserID3(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setUserID("bobby17");
    }

    //Successfully SetUserID
    @Test
    public void testSetUserID4(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setUserID("BobBuilder");
        assertEquals("BobBuilder", bob.getUserID());
    }

    //SetUserID with a character string less than 4
    @Test (expected = IllegalArgumentException.class)
    public void testSetUserID5(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setUserID("B");
    }

    //SetUserID with a character string greater than 12
    @Test (expected = IllegalArgumentException.class)
    public void testSetUserID6(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setUserID("Bobbbbbbbbbbbbbbbbbbbb17");
    }

    //SetPassword without logging in
    @Test (expected = Exception.class)
    public void testSetPassword(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        assertFalse(bob.isLoggedIn());
        bob.setPassword("Bob@123");
    }

    //Successful setPassword
    @Test
    public void testSetPassword2(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setPassword("Bob@123");
        assertEquals("Bob@123", bob.getPassword());
    }

    //SetPassword to the current password
    @Test (expected = IllegalArgumentException.class)
    public void testSetPassword3(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setPassword("BillyB$b1");
    }

    //SetPassword without special character
    @Test (expected = IllegalArgumentException.class)
    public void testSetPassword4(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setPassword("BillyBob1");
    }

    //SetPassword without digit
    @Test (expected = IllegalArgumentException.class)
    public void testSetPassword5(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setPassword("BillyB$b");
    }

    //SetPassword without uppercase
    @Test (expected = IllegalArgumentException.class)
    public void testSetPassword6(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setPassword("billyb$b");
    }

    //SetPassword without lowercase
    @Test (expected = IllegalArgumentException.class)
    public void testSetPassword7(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setPassword("BILLYB$B");
    }

    //SetPassword with less than 4 characters
    @Test (expected = IllegalArgumentException.class)
    public void testSetPassword8(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setPassword("Bb$");
    }

    //SetPassword with greater than 12 characters
    @Test (expected = IllegalArgumentException.class)
    public void testSetPassword9(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setPassword("BillyB$$bby17");
    }

    //SetEmail when not logged in
    @Test (expected = Exception.class)
    public void testSetEmail(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.setEmail("billybob@live.com");
    }

    //Successful setEmail
    @Test
    public void testSetEmail2(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setEmail("billybob@live.com");
        assertEquals("billybob@live.com", bob.getEmail());
    }

    //SetEmail format broken: userid@address.domain
    @Test (expected = IllegalArgumentException.class)
    public void testSetEmail3(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setEmail("@live.com");
    }

    //SetEmail format broken by address: userid@address.domain
    @Test (expected = IllegalArgumentException.class)
    public void testSetEmail4(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setEmail("billybob@.com");
    }

    //SetEmail format broken by domain: userid@address.domain
    @Test (expected = IllegalArgumentException.class)
    public void testSetEmail5(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setEmail("billybob@live.");
    }

    //SetEmail format broken by excluding @: userid@address.domain
    @Test (expected = IllegalArgumentException.class)
    public void testSetEmail6(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setEmail("billyboblive.com");
    }

    //SetEmail format broken by excluding all special characters: userid@address.domain
    @Test (expected = IllegalArgumentException.class)
    public void testSetEmail7(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setEmail("billyboblivecom");
    }

    //SetEmail null
    @Test (expected = IllegalArgumentException.class)
    public void testSetEmail8(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setEmail(null);
    }

    //SetEmail to current Email
    @Test (expected = IllegalArgumentException.class)
    public void testSetEmail9(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setEmail("steverog@live.com");
    }

    //
    @Test (expected = Exception.class)
    public void testSetPhone(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.setPhone("7037654321");
    }

    //Successful SetPhone
    @Test
    public void testSetPhone2(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setPhone("7037654321");
        assertEquals("7037654321", bob.getPhone());
    }

    //SetPhone with invalid PhoneNo length
    @Test (expected = IllegalArgumentException.class)
    public void testSetPhone3(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setPhone("703");
    }

    //SetPhone with invalid PhoneNo length
    @Test (expected = IllegalArgumentException.class)
    public void testSetPhone4(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setPhone("70312345678");
    }

    //SetPhone with nondigit characters
    @Test (expected = IllegalArgumentException.class)
    public void testSetPhone5(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setPhone("7o312E4567");
    }

    //SetPhone with nondigit characters
    @Test (expected = IllegalArgumentException.class)
    public void testSetPhone6(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setPhone("703!234$67");
    }

    //SetPhone null
    @Test (expected = IllegalArgumentException.class)
    public void testSetPhone7(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setPhone(null);
    }

    //SetPhone to current phone
    @Test (expected = IllegalArgumentException.class)
    public void testSetPhone8(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setPhone("7031234567");
    }


    //******* BANK CLASS TEST CASES ************

    @Test
    // FA test findAccountByID with a valid account number
    public void testfindAccountByID() {
        User usr1 = new User()
                .withName("tina")
                .withUserID("tina1");

        Account checking = new Account()
                .withAccountnum(1)
                .withOwner(usr1)
                .withBalance(100);


        Bank bnk = new Bank();
        bnk.createCustomerAccounts();
        bnk.withCustomerAccounts(checking);

        Account acntGet = bnk.findAccountByID(1);
        //System.out.println("acntGet" + acntGet);
        assertTrue(1==acntGet.getAccountnum());
    }

    @Test
    // FA test findAccountByID with empty account abject, should return null
    public void testfindAccountByIDWithNull() {

        Bank bnk = new Bank();
        bnk.createCustomerAccounts();
        bnk.withCustomerAccounts(null);

        Account acntGet = bnk.findAccountByID(1);
        //System.out.println("acntGet" + acntGet);
        assertTrue(null==acntGet);
    }

    /**
     * 1. Test login validation of all customerAccounts given correct credentials
     * 2. Test login validation of all AdminAccounts given correct credentials
     * 3. Test login validation given incorrect userID
     * 4. Test login validation given incorrect password
     * 5. Test login validation given invalid accountNum
     */
    @Test
    public void testvalidateLogin() {
        User usr1 = new User()
                .withName("karli")
                .withUserID("karli25")
                .withPassword("StudyRight");
        User usr2 = new User()
                .withName("steve")
                .withUserID("steverog1")
                .withPassword("Th3C@pt");
        User usr3 = new User()
                .withName("kevin")
                .withUserID("ksludo")
                .withPassword("Zh1p@n");
        Account checking1 = new Account()
                .withAccountnum(1)
                .withOwner(usr1)
                .withBalance(100);
        Account checking2 = new Account()
                .withAccountnum(2)
                .withOwner(usr2)
                .withBalance(1000);
        Account checking3 = new Account()
                .withAccountnum(3)
                .withOwner(usr3)
                .withBalance(12300000);
        Bank bnk = new Bank();
        bnk.withCustomerAccounts(checking1);
        bnk.withCustomerAccounts(checking2);
        bnk.withAdminAccounts(checking3);
        bnk.withCustomerUser(usr1);
        bnk.withCustomerUser(usr2);
        bnk.withAdminUsers(usr3);

        assertFalse("Validated karli's login with incorrect password", bnk.validateLogin(1, "karli25", "SDML1b"));
        assertTrue("Did not successfully validate karli's login", bnk.validateLogin(1, "karli25", "StudyRight"));
        assertFalse("Validated account 2 with incorrect userID", bnk.validateLogin(2, "steverog2", "Th3C@pt"));
        assertTrue("Did not successfully validate steve's login" ,bnk.validateLogin(2, "steverog1", "Th3C@pt"));
        assertTrue("Did not validate admin login", bnk.validateLogin(3, "ksludo", "Zh1p@n"));
        assertFalse("Validated login of an nonexisting account", bnk.validateLogin(4, "ulnoSDM", "SDML1b"));
    }

    // Should throw an IllegalArgument Exception when trying to validate login of an account with a negative ID
    @Test (expected = IllegalArgumentException.class)
    public void testvalidateLoginWithNegativeAccountID() {
        User usr1 = new User()
                .withName("karli")
                .withUserID("karli25")
                .withPassword("StudyRight");
        Account checking1 = new Account()
                .withAccountnum(1)
                .withOwner(usr1)
                .withBalance(100);
        Bank bnk = new Bank();
        bnk.withCustomerAccounts(checking1);
        bnk.withCustomerUser(usr1);
        bnk.validateLogin(-1, "karli25", "StudyRight");
    }

    // Should throw an IllegalArgument Exception when trying to validate login of null UserID
    @Test (expected = IllegalArgumentException.class)
    public void testvalidateLoginWithNullUserID() {
        User usr1 = new User()
                .withName("karli")
                .withUserID("karli25")
                .withPassword("StudyRight");
        Account checking1 = new Account()
                .withAccountnum(1)
                .withOwner(usr1)
                .withBalance(100);
        Bank bnk = new Bank();
        bnk.withCustomerAccounts(checking1);
        bnk.withCustomerUser(usr1);
        bnk.validateLogin(1, null, "StudyRight");
    }

    // Should throw an IllegalArgument Exception when trying to validate login of null UserID
    @Test (expected = IllegalArgumentException.class)
    public void testvalidateLoginWithNullPassword() {
        User usr1 = new User()
                .withName("karli")
                .withUserID("karli25")
                .withPassword("StudyRight");
        Account checking1 = new Account()
                .withAccountnum(1)
                .withOwner(usr1)
                .withBalance(100);
        Bank bnk = new Bank();
        bnk.withCustomerAccounts(checking1);
        bnk.withCustomerUser(usr1);
        bnk.validateLogin(1, "karli25", null);
    }

    // Tests if findUserByID can find all users associated with the bank
    // Also tests for the case in which the user cannot be found
    @Test
    public void testfindUserByID() {
        User usr1 = new User()
                .withName("tina")
                .withUserID("tina1");
        User usr2 = new User()
                .withName("steve")
                .withUserID("steverog1");
        Bank bnk = new Bank();
        bnk.withCustomerUser(usr1);
        bnk.withCustomerUser(usr2);

        User usr = bnk.findUserByID("steverog1");
        assertTrue(usr.getUserID().equals(usr.getUserID()));
        usr = bnk.findUserByID("tina1");
        assertTrue(usr.getUserID().equals(usr.getUserID()));
        usr = bnk.findUserByID("tina1");
        assertTrue(usr.getUserID().equals(usr.getUserID()));
        usr = bnk.findUserByID("noone");
        assertTrue("User does not exist", usr == null);
    }

    @Test
    public void testfindUserByIDWithNull() {
        Bank bnk = new Bank();
        bnk.createCustomerUser();
        bnk.withCustomerUser(null);

        User usrGet = bnk.findUserByID("steverog1");
        assertTrue(usrGet == null);
    }
}
