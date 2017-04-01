import org.junit.Test;
import org.sdmlib.openbank.Account;
import org.sdmlib.openbank.Transaction;
import org.sdmlib.openbank.User;
import org.sdmlib.openbank.util.TransactionSet;

import static org.junit.Assert.*;
/**
 * Created by Savindi on 3/30/2017.
 */
public class TestScenario10{
    private User tina, kim;
    private Account t, k;

    public void precondition() {
        this.tina = new User()
                .withName("Tina")
                .withUserID("tina123")
                .withIsAdmin(false);
        this.kim = new User()
                .withName("Kim")
                .withUserID("kim123")
                .withIsAdmin(false);
        this.t = new Account()
                .withOwner(tina)
                .withAccountnum(234);
        this.k = new Account()
                .withOwner(kim)
                .withAccountnum(567);
    }

/*   Title: Tina views her transactions
*(Pre)* Tina wants to view her transaction to ensure she is spending correctly
	1) Tina logs into her banking account -> See S1
	2) Tina selects the transactions option
    *(Post)* Tina can see all of her transactions that have occured*/

    @Test //Test the transaction
    public void TestSenario10(){
        this.precondition();
        this.t.withBalance(50).setIsConnected(true);
        this.k.withBalance(25).setIsConnected(true);

        TransactionSet toAccount = new TransactionSet();
        TransactionSet fromAccount = new TransactionSet();

        Transaction transaction1 =  new Transaction();
        this.t.transferToUser(10, this.k, fromAccount, transaction1, 3/20/2017, 9.00, "Tina transfer money to Kim.");

        Transaction transaction2 =  new Transaction();
        this.t.deposit(50, transaction2,"3/25/2017", "11.00AM EST", "Tina deposit $20.",toAccount );

        Transaction transaction3 =  new Transaction();
        this.t.withdraw(20,transaction3,"3/30/2017", "10.00AM EST", "Tina withdraw $20.", fromAccount);

        String str1 = "10.0 3/20/2017 9.00AM EST Tina transfer money to Kim.\n20.0 3/30/2017 10.00AM EST Tina withdraw $20.\n";
        String str2 = "50.0 3/25/2017 11.00AM EST Tina deposit $20.\n";

        assertEquals(true, str1.equals(this.t.transactionFromAccount(fromAccount)));
        assertEquals(true, str2.equals(this.t.transactionFromAccount(toAccount)));
    }
}
