import org.junit.Assert;
import org.junit.Test;
import org.sdmlib.openbank.Account;
import org.sdmlib.openbank.Transaction;
import org.sdmlib.openbank.User;

import java.util.Date;

/**
 * Created by kimberly_93pc on 3/29/17.
 */


public class TestScenario4 {
    Account CreditAccount;
    Account DebitAccount;
    User user;

    @Test//Creating Credit account
    public void creatingCreditAccount() {
        CreditAccount = new Account();
        user = new User();

        //Setting the account information.
        CreditAccount.Account(12.99);//Could not find a way to actually create a constructor.
        CreditAccount.withName("My Credit Account");
        CreditAccount.withAccountnum(123456789);

        Date date = new Date(2017,03,31);
        date.setTime(12);

        CreditAccount.withCreationdate(date);
        CreditAccount.withCredit();
        CreditAccount.withOwner(user);
        CreditAccount.withPhone(7032222222l);
        CreditAccount.withUsername("Tina");

        //Asserting the account info is set correctly
        assert(CreditAccount.getName().equals("My Credit Account"));
        assert(CreditAccount.getAccountnum()==123456789);
        assert(CreditAccount.getCreationdate()==date);
        assert(CreditAccount.getOwner().equals(user));
        assert(CreditAccount.getPhone()==7032222222l);
        assert(CreditAccount.getUsername().equals("Tina"));
        assert(CreditAccount.getBalance()==12.99);
    }
    @Test//Creating Debit account
    public void creatingDebitAccount() {
        creatingCreditAccount();
        DebitAccount = new Account();

        //Setting the account information.
        DebitAccount.Account(120.99);//Could not find a way to actually create a constructor.
        DebitAccount.withName("My Debit Account");
        DebitAccount.withAccountnum(123456789);

        Date date = new Date(2017,03,31);
        date.setTime(12);

        DebitAccount.withCreationdate(date);
        DebitAccount.withCredit();
        DebitAccount.withEmail("myEmail@mail.com");
        DebitAccount.withOwner(user);
        DebitAccount.withPassword("1234");
        DebitAccount.withPhone(7032222222l);
        DebitAccount.withUsername("Tina");

        //Asserting the account info is set correctly
        assert(DebitAccount.getName().equals("My Debit Account"));
        assert(DebitAccount.getAccountnum()==123456789);
        assert(DebitAccount.getCreationdate()==date);
        assert(DebitAccount.getEmail().equals("myEmail@mail.com"));
        assert(DebitAccount.getOwner().equals(user));
        assert(DebitAccount.getPassword().equals("1234"));
        assert(DebitAccount.getPhone()==7032222222l);
        assert(DebitAccount.getUsername().equals("Tina"));
        assert(DebitAccount.getBalance()==120.99);

        user.withAccount(CreditAccount,DebitAccount);
    }
    @Test//Testing login()
    public void logInToAccountTest() {
        creatingCreditAccount();
        creatingDebitAccount();

        CreditAccount.login("Tina", "1234");
        DebitAccount.login("Tina", "1234");

        //Making sure login works
        assert(CreditAccount.isIsLoggedIn());
        assert(DebitAccount.isIsLoggedIn());
    }

    @Test//Testing myBankTransaction()
    public void myBankTransactionTest(){
        double initialCreditBalance = CreditAccount.getBalance();
        double initialDebitBalance = DebitAccount.getBalance();

        CreditAccount.myBankTransaction(5.00,DebitAccount);

        //Making sure transfer works
        assert(CreditAccount.getBalance()==initialCreditBalance-5.00);
        assert(DebitAccount.getBalance()==initialDebitBalance+5.00);
    }

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
