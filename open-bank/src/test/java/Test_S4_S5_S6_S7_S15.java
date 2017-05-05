import org.junit.Assert;
import org.junit.Test;
import org.sdmlib.openbank.Account;
import org.sdmlib.openbank.Transaction;
import org.sdmlib.openbank.User;
import org.sdmlib.storyboards.Storyboard;

import java.math.BigInteger;
import java.util.Date;

/**
 * Created by kimberly_93pc on 3/29/17.
 * log:
 *  03/31/17 Kimberly
 *  05/01/17 Kimberly
 */


public class Test_S4_S5_S6_S7_S15 {
    Account tinachecking;
    Account tinasavings;
    Account nickchecking;
    User tina;
    User nick;
    Date date;
    Storyboard storyboard;

    //Creating Checking Account to use in all tests.
    public void createAccounts() {

        tina = new User();

        //Setting user's information
        tina.withName("Tina")
                .withPassword("1234")
                .withUserID("Tina");

        nick = new User();

        //Setting user's information
        nick.withEmail("secondEmail@email.com")
                .withName("Nick")
                .withPassword("9876")
                .withUserID("Nick");
        
        
        //Setting the account information.
        tinachecking = new Account()
                .withAccountnum(123456789)
                .withBalance(BigInteger.valueOf(30))
                .withOwner(tina)
                .withCreationdate(date);
                //.withDebit()


        tinasavings = new Account()
                .withCreationdate(date)
                .withBalance(BigInteger.valueOf(5))
                .withOwner(tina);
    

    
        nickchecking = new Account()
                .withAccountnum(1234555555)
                .withCreationdate(date)
                .withBalance(BigInteger.valueOf(15))
                .withOwner(nick);
    }

//    **4**
//    Title: Tina transfers $10 from her Checking account to her Savings account
//      *(Pre)* Tina wants to put some money into her savings account. She has $30 in her checking and $5 in her savings.
//      She wants to move $10 into her savings account.
//      1) Tina logs into her banking account -> See S1
//      2) Tina is in her homepage
//      3) Tina selects the transfer option
//      4) Tina is presented with her linked accounts
//      5) Tina selects the Checking account to transfer out of
//      6) Tina selects the Savings account to transfer to
//      7) Tina enters the amount of money she wants to move
//      8) Tina selects the transfer button to initiate the transfer
//      *(Post)* Tina now has  $20 in her Checking account and $15 in her Savings account.

    @Test//Testing myBankTransaction()
    public void S4_UserTransersBetweenTheirAccountsTest(){
        storyboard = new Storyboard();
        //Initialzie Tina'as accounts
        createAccounts();

        storyboard.add("Precondition: Tina has $30 on her checking account and $5 in her savings account");
        storyboard.addObjectDiagram("Tina", tina, "Checking", tinachecking,"Savings",tinasavings);

        //Tina Logs in
        tina.login("Tina","1234");
        storyboard.assertTrue("Tina is logged in: ",tina.isLoggedIn());//Sign in before making transaction.

        //Transfer 10 dollars
        storyboard.assertTrue("Transfer was successfull",tinachecking.transferToAccount(BigInteger.valueOf(10),tinasavings, "Tina transfers 10 from checking to savings"));

        //Checking is reduced
        storyboard.assertEquals("Checking Account reduced by $10.00: ",
                20.0,tinachecking.getBalance());
        //Savings increased
        storyboard.assertEquals("Savings Account has $10.00 extra: ",
                15.0,tinasavings.getBalance());

        storyboard.add("Post-condition: Tina now has  $20 in her Checking account and $15 in her Savings account");
        storyboard.addObjectDiagram("Tina", tina,"Checking", tinachecking,"Savings",tinasavings);
        storyboard.dumpHTML();
    }

//    **5**
//    Title: Tina withdraws 30 dollars from Savings account.
//      *(Pre)* Tina has $50 in her Checking account and needs $30 to buy food.
//	    1) Tine logs into her banking account -> See S1.
//      2) Tina is in the homepage of her account.
//      3) Tina selects the withdraw money option.
//      4) Tina selects the Saving account to withdraw from.
//      5) Tina enters $30 to withdraw.
//	    6) Tina selects the withdraw button.
//      *(Post)* Tina gets $30 cash and her checking now has $20.
    @Test
    public void S5_testWithdraw(){
        storyboard = new Storyboard();
        createAccounts();
        tinasavings.withBalance(BigInteger.valueOf(30));
        tina.login("Tina","1234");
        storyboard.assertTrue("Tina is logged in: ",tina.isLoggedIn());

        //Precondition, Tina has 50 in her chekcing account
        storyboard.add("Precondition: Tina has $30 in her savings account and needs $10 to buy food.");
        storyboard.assertEquals("Tina has 30 in her bank account ",30.0,tinasavings.getBalance());
        storyboard.addObjectDiagram("Tina", tina,"Savings",tinasavings);

       tinasavings.withdraw(BigInteger.valueOf(10));//Making withdraw
        storyboard.assertEquals("Tina makes a withdraw of 10 ",
               20.0, tinasavings.getBalance());//Making sure it worked.
        storyboard.add("Post-condition: Tina gets $10 cash and her savings now has $20");
        storyboard.addObjectDiagram("Tina", tina, "Savings",tinasavings);
        storyboard.dumpHTML();

    }

//    **6**
//    Title: Tina connects her phone to Nick's phone.
//      *(Pre)* Tina wants to connect her phone to Nick's phones so that they can make a p2p transaction.
//	    1) Tina logs into her banking account -> See S1.
//      2) Tina selects the transfer funds option.
//      3) From transfer page, Tina selects the transfer-to-other user option.
//	    4) Nick logs into his banking account --> See S1.
//      5) Nick selects the Receive Founds option, and connects to Tina's phone.
//      4) Tina waits for Nick to select the other user option as well and connects to Nick's Phone.
//      *(Post)* Tina is now connected to Nick's phone & Nick is connected to Tina's phone, a transaction can take place.
    @Test
    public void S6_UserConnectsToAnotherUserTest(){
        storyboard = new Storyboard();
        createAccounts();
        tina.login("Tina","1234");
        nick.login("Nick","9876");


        storyboard.add("Precondition: Tina wants to connect her phone to Nick's phones so that they can make a p2p transaction.");
        storyboard.addObjectDiagram("Tina", tina, "CheckingTina", tinachecking, "Savings",tinasavings);
        storyboard.addObjectDiagram("Nick", nick, "CheckingNick", nickchecking);

        storyboard.assertTrue("Tina logs in: ",tina.isLoggedIn());//Assert that they are logged in
        storyboard.assertTrue("NIck logs in: ", nick.isLoggedIn());

        /*
            TODO: This scenario does not test an actual transfer but rather simply that tina's device is connected to Nick's device
                This test should be implemented when Android is set up and we can establish connection between phones
         */

        storyboard.add("Post-condition:: Tina is now connected to Nick's phone & Nick is connected to Tina's phone, a transaction can take place.");
        storyboard.addObjectDiagram("Tina", tina, "CheckingTina", tinachecking, "Savings",tinasavings);
        storyboard.addObjectDiagram("Nick", nick, "CheckingNick", nickchecking);
        storyboard.dumpHTML();
    }

//    **7**
//    Title: Tina gives Nick $10 through a p2p connection.
//      *(Pre)* Tina wants to give Nick $10 by connecting their accounts through phone. Tina has $50 in her Checking and Nick has $15.
//      1) Tina connects to Nick's phone -> See S6.
//      2) Tina selects the exchange funds option.
//      3) Tina enters the amount she wants to transfer.
//      4) Tina selects the account she wants the funds to come out of.
//      5) Tina selects the transfer funds option.
//      6) Nick selects the approve option on his phone to receive the funds.
//      *(Post)* Tina has transferred $10 to Nick between their accounts.
    @Test
    public void S7_testTransferToUser2(){
        storyboard = new Storyboard();
        createAccounts();
        //Two users login
        tina.login("Tina","1234");
        nick.login("Nick","9876");
        storyboard.assertTrue("Tina logs in: ",tina.isLoggedIn());//Assert that they are logged in
        storyboard.assertTrue("NIck logs in: ", nick.isLoggedIn());

        storyboard.add("Precondition: Tina wants to give Nick $10 by connecting their accounts through phone." +
                        " Tina has $30 in her Checking and Nick has $15.");
        storyboard.addObjectDiagram("Tina", tina, "CheckingTina", tinachecking, "Savings",tinasavings);
        storyboard.addObjectDiagram("Nick", nick, "CheckingNick", nickchecking);
        //Assert correct inital balance
        storyboard.assertEquals("Tina has 30 in her account",30.0,tinachecking.getBalance());
        storyboard.assertEquals("Tina has 5 in her account",15.0,nickchecking.getBalance());

        //Calling transferToAccount()
        tinachecking.transferToAccount(BigInteger.valueOf(10), nickchecking, "Transfering 10 from tina's checking to nicks checking");

        //Assert each user is connected to their account.
        /*
            TODO: Add tests here ensuring user phones are connected (Android)
         */
//        storyboard.assertTrue("They are both connected",
//                tinachecking.isIsConnected() && nickchecking.isIsConnected());

        //Assert that the amount did transfer
        storyboard.assertEquals("Tina's balance changed: ",
                20.0,tinachecking.getBalance());
        storyboard.assertEquals("Nick's balance changed: "
               ,25.0, nickchecking.getBalance());

        storyboard.add("Post-condition: Tina has transferred $10 to Nick between their accounts");
        storyboard.addObjectDiagram("Tina", tina, "CheckingTina", tinachecking, "Savings",tinasavings);
        storyboard.addObjectDiagram("Nick", nick, "CheckingNick", nickchecking);
        storyboard.dumpHTML();

    }

//    **15***
//    Title: Tina opens a Checking account.
//      *(Pre)* Tina wants to open a Checking account with Open Bank, she has no accounts.
//      1) Tina goes to her Open Bank app.
//	    2) Tina logs in to her bank account. -> see S1
//	    3) Tina clicks on create a Checking account.
//	    4) Tina enters information of her new Checking account.
//      5) Tina named her Checking account as "My checking".
//      6) Tina submits the information and enters a seeding amount of $30.
//      *(Post)* Tina now has a Checking account.

    @Test//Creating Checking Account
    public void S15_creatingtinachecking() {
        storyboard = new Storyboard();
        storyboard.add("[[Tina wants to open a Checking account with Open Bank. She has no accounts]]");

        tina = new User().withLoggedIn(true)
                .withEmail("myEmail@email.com")
                .withName("Tina")
                .withPassword("1234")
                .withUserID("Tina");

        //Tina logs in
        tina.login("Tina", "1234");

        //Making sure login works
        storyboard.assertTrue("Tina is logged in: ",tina.isLoggedIn());


        storyboard.add("Precondition) Tina wants to open a Checking account with Open Bank, she has 0 accounts.");
        storyboard.assertEquals("Has 0 accounts in this bank",0,tina.getAccount().size());
        storyboard.add("2) Tina opnes a checking account with balance of 100");
        //Setting the account information
        tinachecking = new Account()//Could not find a way to actually create a constructor.
                .withBalance(BigInteger.valueOf(100))
                .withOwner(tina)
                .withCreationdate(new Date());

        //Asserting the account info is set correctly
        storyboard.assertEquals("Owner is Tina: ",
                tina,tinachecking.getOwner());

        storyboard.assertEquals("Tina has one account open",1,tina.getAccount().size());
        storyboard.assertEquals("Tina's checking account has a balance of 100",100.0,tina.getAccount().get(0).getBalance());

        storyboard.addObjectDiagram("Tina", tina);

        storyboard.add("Post) Tina now has a Checking account.");
        storyboard.addObjectDiagram("Checking", tinachecking, "Tina", tina);
        storyboard.dumpHTML();
    }

}

