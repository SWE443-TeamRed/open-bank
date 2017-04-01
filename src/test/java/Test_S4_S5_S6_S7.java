import org.junit.Assert;
import org.junit.Test;
import org.sdmlib.openbank.Account;
import org.sdmlib.openbank.Transaction;
import org.sdmlib.openbank.User;

import java.util.Date;

/**
 * Created by kimberly_93pc on 3/29/17.
 */


public class Test_S4_S5_S6_S7 {
    Account CheckingAccount;
    Account SavingsAccount;
    Account CheckingSecondUser;
    User tina;
    User nick;
    Date date;

    @Test//Creating Checking Account
    public void creatingCheckingAccount() {
        CheckingAccount = new Account();
        tina = new User();

        //Setting date & time
        date = new Date(2017,03,31);
        date.setTime(12);

        //Setting the account information.
        CheckingAccount.Account(30.00);//Could not find a way to actually create a constructor.
        CheckingAccount.withName("My Credit Account")
                            .withAccountnum(123456789)
                            .withDebit()
                            .withOwner(tina)
                            .withCreationdate(date);

        //Setting user's information
        tina.withEmail("myEmail@email.com")
                            .withName("Tina")
                            .withPassword("1234")
                            .withUserID("Tina");
        //Tina logs in
        tina.login("Tina", "1234");

        //Asserting the account info is set correctly
        assert(CheckingAccount.getName().equals("My Credit Account"));
        assert(CheckingAccount.getAccountnum()==123456789);
        assert(CheckingAccount.getCreationdate()==date);
        assert(CheckingAccount.getOwner().equals(tina));

        //Making sure login works
        assert(tina.isLoggedIn());
        assert(CheckingAccount.getBalance()==30.00);
    }

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

//**4**
//        Title: Tina transfers 10 dollars in her checking account to savings account
//        *(Pre)* Tina wants to put some money into her savings account. She has 30 dollars in her checking and 5 in her savings. She wants to move 10 into her savings
//        1) Tina logs into her banking account -> See S1
//        2) Tina is in her homepage
//        3) Tina selects the transfer option
//        4) Tina is presented with her linked accounts
//        5) Tina selects the checking account to transfer out of
//        6) Tina selects the savings account to transfer to
//        7) Tina enters the amount of money she wants to move
//        8) Tina selects the transfer button to initiate the transfer
//        *(Post)* Tina no

    @Test//Testing myBankTransaction()
    public void myBankTransactionTest(){
        creatingCheckingAccount();
        creatingSavingsAccount();
        double initialCheckingBalance = CheckingAccount.getBalance();
        double initialSavingsBalance = SavingsAccount.getBalance();

        CheckingAccount.myBankTransaction(10.00, SavingsAccount);

        //Making sure transfer works
        assert(CheckingAccount.getBalance()==initialCheckingBalance-10.00);
        assert(SavingsAccount.getBalance()==initialSavingsBalance+10.00);
    }

    //**5**
//        Title: Tina withdraws 30 dollars from checking
//        *(Pre)* Tina has 50 dollars in her checking account and needs 30 dollars to buy food.
//        1) Tine logs into her banking account -> See S1
//        2) Tina is in the homepage of her account.
//        3) Tina selects the withdraw money option
//        4) Tina selects the saving account to withdraw from
//        5) Tina enters 30 dollars to withdraw
//        6) Tina selects the withdraw button
//        *(Post)* Tina gets 30 dollars cash and her checking now has 20 dollars
    //Testing withdraw method from account class.
    @Test
    public void testWithdraw(){
        creatingSavingsAccount();
        SavingsAccount.setBalance(50.00);
        assert(tina.isLoggedIn());

        SavingsAccount.withdraw(30.00);//Making withdraw
        assert(SavingsAccount.getBalance()==50.00-30.00);//Making sure it worked.
    }

    //**6**
//        Title: Tina connects her phone to Nick's phone
//        *(Pre)* Tina wants to connect her phone to Nick so that they can make a transaction
//        1) Tina logs into her banking account -> See S1
//        2) Tina selects the transfer funds option
//        3) Tina selects the to other user option
//        4) Tina waits for Nick to select the other user option as well
//        *(Post)* Tina is now connected to Nick's phone a transaction can take place
    @Test
    public void testTransferToUser1(){
        creatingSavingsAccount();//User Tina
        creatingSecondUserAccount();//User Nick
        tina.login("Tina","1234");
        nick.login("Nick","9876");
        CheckingAccount.setBalance(30.00);
        CheckingSecondUser.setBalance(5.00);

        CheckingAccount.transferToUser(10.00, CheckingSecondUser);
        CheckingSecondUser.receiveFound(10.00,CheckingAccount);
        assert(tina.isLoggedIn());//Assert that they are logged in
        assert(nick.isLoggedIn());

        //Assert each user is connected to their account.
        assert(CheckingAccount.isIsConnected() && CheckingSecondUser.isIsConnected());
    }

    //**7**
//        Title: Tina gives Nick 10 dollars through their accounts
//        *(Pre)* Tina wants to give Nick 10 dollars by connecting their accounts through phone. Tina has 50 dollars in her checking and Nick has 15
//        1) Tina connects to Nick's phone -> See S6
//        2) Tina selects the exchange funds option
//        3) Tina enters the amount she wants to transfer
//        4) Tina enters the account she wants to funds to come out of
//        5) Tina selects the trasfer funds option
//        6) Nick selects the approve option on his phone to recieve the funds
//        *(Post)* Tina has trasnfered 10 dollars to Nick between their accounts
    @Test
    public void testTransferToUser2(){
        creatingSavingsAccount();//User Tina
        creatingSecondUserAccount();//User Nick
        tina.login("Tina","1234");
        nick.login("Nick","9876");
        CheckingAccount.setBalance(30.00);
        CheckingSecondUser.setBalance(5.00);

        //Calling transferToUser()
        CheckingAccount.transferToUser(10.00, CheckingSecondUser);
        CheckingSecondUser.receiveFound(10.00,CheckingAccount);
        assert(tina.isLoggedIn());//Assert that they are logged in
        assert(nick.isLoggedIn());

        //Assert each user is connected to their account.
        assert(CheckingAccount.isIsConnected() && CheckingSecondUser.isIsConnected());

        //Assert that the amount did transfer
        assert(CheckingAccount.getBalance()==30.00-10.00);
        assert(CheckingSecondUser.getBalance()==5.00+10.00);

    }

}

