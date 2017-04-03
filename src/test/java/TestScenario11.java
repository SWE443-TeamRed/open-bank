
/**
 * Created by Savindi on 4/1/2017.
 */
import org.junit.Test;
import org.sdmlib.openbank.Account;
import org.sdmlib.openbank.User;
import org.sdmlib.storyboards.Storyboard;
import org.sdmlib.openbank.Transaction;
import java.util.Date;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestScenario11 {
    Account CheckingAccount;
    Account SavingsAccount;
    Account CheckingSecondUser;
    User tina;
    User nick;
    Date date;
    Storyboard storyboard1;
    Storyboard storyboard2;


    //Creating Checking Account to use in all tests.
    public void creatingCheckingAccount() {
        CheckingAccount = new Account();
        tina = new User();

        //Setting date & time
        date = new Date(2017, 03, 31);
        date.setTime(12);

        //Setting user's information
        tina.withEmail("Tina@email.com")
                .withName("Tina")
                .withPassword("Tina1234")
                .withUserID("Tina");

        //Setting the account information.
        //CheckingAccount.Account(30.00);
        CheckingAccount
                .withBalance(5)
                .withAccountnum(1234)
                .withDebit()
                .withOwner(tina)
                .withCreationdate(date.toString());

    }

    //Creating Savings Account
    public void creatingSavingsAccount() {
        creatingCheckingAccount(); //This test depends on first test.
        SavingsAccount = new Account();

        //Setting the account information.
        //SavingsAccount.Account(5.00);
        SavingsAccount
                .withBalance(5)
                .withAccountnum(123456789)
                .withCreationdate(date.toString())
                .withDebit()
                .withOwner(tina);
    }

    public void creatingSecondUserAccount() {
        CheckingSecondUser = new Account();
        nick = new User();

        //Setting user's information
        nick.withEmail("Nick@email.com")
                .withName("Nick")
                .withPassword("5678")
                .withUserID("Nick");

        //Setting the account information.
        //CheckingSecondUser.Account(5.00);//Could not find a way to actually create a constructor.
        CheckingSecondUser
                .withBalance(5)
                .withAccountnum(981011)
                .withCreationdate(date.toString())
                .withDebit()
                .withOwner(nick);
    }

    /**
     * S9:
     * Title: Tina tries to transfer too much money to Nick
     *(Pre)* Tina wants to transfer 1 million dollars to Nick. She has 30 dollars in her checking account
     * 1) Tina connects to Nick's phone -> See S6
     * 2) Tina selects the exchange funds option
     * 3) Tina enters 1 million dollars to transfer
     * 4) Tina selects the checking account to get the money
     * 5) Tina selects the transfer funds option
     *(Post)* Tina is presented with a message saying she does not have enough funds


    S11
    Title: Tina modifies her previous transaction
    *(Pre)* Tina wants to modify some previous transactions she made.
    *	1) Tina views her transactions -> See S9
    *	2) Tina selects the transaction she made from her checking to savings account at the top of the list
     *	3) Tina selects the edit transaction option
    *	4) Tina changes the amount to transfer from 10 dollars to 20
     *	5) Tina selects the save transaction option
    *(Post)* Tina has edited her most recent transaction
     ** @see <a href='../../../doc/S11ModifyTransaction.html'>S11ModifyTransaction.html</a>
 */

    @Test
    public void S11ModifyTransaction()
    {
        storyboard1 = new Storyboard();
        storyboard2 = new Storyboard();

        creatingCheckingAccount();
        creatingSavingsAccount();
        creatingSecondUserAccount();

        //Tina logs in
        tina.login("Tina", "Tina1234");
        assert (tina.isLoggedIn());

        Transaction transaction = new Transaction();
        transaction.setAmount(10);
        CheckingAccount.withBalance(5);
        SavingsAccount.withBalance(30);

        CheckingSecondUser.withBalance(15).withIsConnected(true);;
        assertFalse(this.CheckingAccount.transferToUser(10, this.CheckingSecondUser));

        storyboard1.add("Precondition 1: Tina has a Saving account and a Checking account."+
                " Saving Account has $30 and Checking Account has $5. Tina tries to transfer $10 from her Checking Account to Nick's Account");
        storyboard1.addObjectDiagram("Tina", tina, "Checking", CheckingAccount,"TinaSavings", SavingsAccount );

        SavingsAccount.transferToUser(10, CheckingSecondUser);
        SavingsAccount.modiftyTransaction(transaction, 20);

        storyboard1.add("Precondition 2: Tina selects the transaction she made from her checking to savings account at the top of the list");
        storyboard1.add("Precondition 3: Tina selects the edit transaction option");
        storyboard1.add( "Precondition 4: Tina changes the amount to transfer from 10 dollars to 20");
        storyboard1.addObjectDiagram("Tina", tina, "TinaChecking", CheckingAccount,"TinaSavings", SavingsAccount );
        storyboard1.dumpHTML();

        assertEquals(true, SavingsAccount.getBalance()== 10.00);
    }
}
