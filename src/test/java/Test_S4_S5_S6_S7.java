import org.junit.Assert;
import org.junit.Test;
import org.sdmlib.openbank.Account;
import org.sdmlib.openbank.Transaction;
import org.sdmlib.openbank.User;
import org.sdmlib.storyboards.Storyboard;

import java.util.Date;

/**
 * Created by kimberly_93pc on 3/29/17.
 * log:
 *  03/31/17
 *  05/01/17
 */


public class Test_S4_S5_S6_S7 {
    Account CheckingAccount;
    Account SavingsAccount;
    Account CheckingSecondUser;
    User tina;
    User nick;
    Date date;
    Storyboard storyboard;

    @Test//Creating Checking Account
    public void creatingCheckingAccount() {

        CheckingAccount = new Account();
        tina = new User();

        //Setting date & time
        date = new Date(2017,03,31);
        date.setTime(12);

        //Setting user's information
        tina.withEmail("myEmail@email.com")
                .withName("Tina")
                .withPassword("1234")
                .withUserID("Tina");

        //Setting the account information.
        CheckingAccount.Account(30.00);//Could not find a way to actually create a constructor.
        CheckingAccount.withName("My Credit Account")
                            .withAccountnum(123456789)
                            .withDebit()
                            .withOwner(tina)
                            .withCreationdate(date);

        //Tina logs in
        tina.login("Tina", "1234");

        //Asserting the account info is set correctly
        assert(CheckingAccount.getName().equals("My Credit Account"));
        assert(CheckingAccount.getAccountnum()==123456789);
        assert(CheckingAccount.getCreationdate().equals(date));
        assert(CheckingAccount.getOwner().equals(tina));

        //Making sure login works
        assert(tina.isLoggedIn());
        assert(CheckingAccount.getBalance()==30.00);
    }

      /**
    * 
    * @see <a href='../../../doc/creatingSavingsAccount.html'>creatingSavingsAccount.html</a>
 */
   @Test//Creating Savings Account
    public void creatingSavingsAccount() {

        creatingCheckingAccount(); //This test depends on first test.
        SavingsAccount = new Account();

        //Setting the account information.
        SavingsAccount.Account(5.00);//Could not find a way to actually create a constructor.
        SavingsAccount.withName("My Debit Account")
                            .withAccountnum(123456789)
                            .withCreationdate(date)
                            .withDebit()
                            .withOwner(tina);

        //Asserting the account info is set correctly
        assert(SavingsAccount.getName().equals("My Debit Account"));
        assert(SavingsAccount.getAccountnum()==123456789);
        assert(SavingsAccount.getCreationdate()==date);
        assert(SavingsAccount.getOwner().equals(tina));

        //Making sure login works
        assert(tina.isLoggedIn());
        assert(SavingsAccount.getBalance()==5.00);
    }

    //Creating an account for a second user
    public void creatingSecondUserAccount() {
        CheckingSecondUser = new Account();
        nick = new User();

        //Setting user's information
        nick.withEmail("secondEmail@email.com")
                .withName("Nick")
                .withPassword("9876")
                .withUserID("Nick");

        //Setting the account information.
        CheckingSecondUser.Account(20.00);//Could not find a way to actually create a constructor.
        CheckingSecondUser.withName("My Debit Account")
                .withAccountnum(1234555555)
                .withCreationdate(date)
                .withDebit()
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
    public void S4_myBankTransactionTest(){
        storyboard = new Storyboard();
        creatingCheckingAccount();
        creatingSavingsAccount();

        double initialCheckingBalance = CheckingAccount.getBalance();
        double initialSavingsBalance = SavingsAccount.getBalance();

        storyboard.add("Precondition: Tina has $30 on her checcking account and $5 in her savings account");
        storyboard.addObjectDiagram("Tina", tina);

        CheckingAccount.myBankTransaction(10.00, SavingsAccount);

        //Making sure transfer works
        storyboard.assertEquals("Checking Account reduced by $10.00: ",
                CheckingAccount.getBalance(),initialCheckingBalance-10.00);
        storyboard.assertEquals("Savings Account has $10.00 extra: ",
                SavingsAccount.getBalance(), initialSavingsBalance+10.00);

        storyboard.addObjectDiagram("Tina", tina);
        storyboard.add("Post-condition: Tina now has  $20 in her Checking account and $15 in her Savings account");
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
        creatingSavingsAccount();
        SavingsAccount.setBalance(50.00);
        assert(tina.isLoggedIn());

        storyboard.add("Precondition: Tina has $50 in her Checking account and needs $30 to buy food.");
        storyboard.addObjectDiagram("Tina", tina);

        SavingsAccount.withdraw(30.00);//Making withdraw
        storyboard.assertEquals("Tina makes transaction: ",
                SavingsAccount.getBalance(),50.00-30.00);//Making sure it worked.
        storyboard.add("Post-condition: Tina gets $30 cash and her checking now has $20");
        storyboard.addObjectDiagram("Tina", tina);
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
    public void S6_testTransferToUser1(){
        storyboard = new Storyboard();
        creatingSavingsAccount();//User Tina
        creatingSecondUserAccount();//User Nick
        tina.login("Tina","1234");
        nick.login("Nick","9876");
        CheckingAccount.setBalance(30.00);
        CheckingSecondUser.setBalance(5.00);

        storyboard.add("Precondition: Tina wants to connect her phone to Nick's phones so that they can make a p2p transaction.");
        storyboard.addObjectDiagram("Tina", tina);

        CheckingAccount.transferToUser(10.00, CheckingSecondUser);
        CheckingSecondUser.receiveFound(10.00,CheckingAccount);
        storyboard.assertTrue("Tina logs in: ",tina.isLoggedIn());//Assert that they are logged in
        storyboard.assertTrue("NIck logs in: ", nick.isLoggedIn());

        //Assert each user is connected to their account.
        storyboard.assertTrue("They are both connected: ",
                CheckingAccount.isIsConnected() && CheckingSecondUser.isIsConnected());

        storyboard.add("Post-condition:: Tina is now connected to Nick's phone & Nick is connected to Tina's phone, a transaction can take place.");
        storyboard.addObjectDiagram("Tina", tina);
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
        creatingSavingsAccount();//User Tina
        creatingSecondUserAccount();//User Nick
        tina.login("Tina","1234");
        nick.login("Nick","9876");
        CheckingAccount.setBalance(30.00);
        CheckingSecondUser.setBalance(5.00);

        storyboard.add("Precondition: Tina wants to give Nick $10 by connecting their accounts through phone." +
                        " Tina has $50 in her Checking and Nick has $15.");
        storyboard.addObjectDiagram("Tina", tina);
        storyboard.addObjectDiagram("Nick", nick);

        //Calling transferToUser()
        CheckingAccount.transferToUser(10.00, CheckingSecondUser);
        CheckingSecondUser.receiveFound(10.00,CheckingAccount);
        storyboard.assertTrue("Tina logs in: ",tina.isLoggedIn());//Assert that they are logged in
        storyboard.assertTrue("NIck logs in: ", nick.isLoggedIn());

        //Assert each user is connected to their account.
        storyboard.assertTrue("They are both connected",
                CheckingAccount.isIsConnected() && CheckingSecondUser.isIsConnected());

        //Assert that the amount did transfer
        storyboard.assertEquals("Tina's balance changed: ",
                CheckingAccount.getBalance(), 30.00-10.00);
        storyboard.assertEquals("Nick's balance changed: ",
                CheckingSecondUser.getBalance(), 5.00+10.00);

        storyboard.add("Post-condition: Tina has transferred $10 to Nick between their accounts");
        storyboard.addObjectDiagram("Tina", tina);
        storyboard.addObjectDiagram("Nick", nick);
        storyboard.dumpHTML();

    }

}

