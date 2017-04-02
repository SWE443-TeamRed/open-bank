
/**
 * Created by Savindi on 4/1/2017.
 */
import org.junit.Test;
import org.sdmlib.openbank.Account;
import org.sdmlib.openbank.User;
import org.sdmlib.storyboards.Storyboard;

import java.util.Date;
    public class TestScenario12 {
        Account tinaAccount;
        Account nickAccount;
        User tina;
        Date date;
        Storyboard storyboard;

        //Creating Checking Account to use in all tests.
        public void creatingTinaAccount() {
            tinaAccount = new Account();
            tina = new User();

            //Setting date & time
            date = new Date(2017, 03, 31);
            date.setTime(12);

            //Setting user's information
            tina.withEmail("Tina@email.com")
                    .withName("Tina")
                    .withPassword("1234")
                    .withUserID("Tina");
            tinaAccount.Account(50.00);
            tinaAccount.withOwner(tina)
                      .withCreationdate(date.toString());
            //Tina logs in
            tina.login("Tina", "1234");
        }

        //**12**
// Title: Tina deposits 100 dollars into her checking account
//*(Pre)* Tina has 100 dollars in cash that she wants to deposit into her checking account. Tina has 40 dollars currently in
//        her checking account.
//	1) Tina logs into her banking account -> See S1
//	2) Tina selects the deposit option
//	3) Tina enters 100 into the amount field
//	4) Tina selects checking in the account field
//	5) Tina selects the complete transaction option
//*(Post)* Tina has deposited 100 dollars into her checking account. She now has 140 dollars in her
        @Test
        public void testDeposit() {
            creatingTinaAccount();
            storyboard = new Storyboard();
            tinaAccount.setBalance(50.00);
            assert (tina.isLoggedIn());

            storyboard.add("Precondition: Tina has $50 in her account and she deposit $30 to her account.");
            storyboard.addObjectDiagram("Tina", tina, "Savings", tinaAccount);

            tinaAccount.deposit(30.00);//Making withdraw
            storyboard.assertEquals("Tina makes transaction: ",
                    tinaAccount.getBalance(), 50.00 + 30.00);//Making sure it worked.
            storyboard.add("Post-condition: Tina has $80 in her account");
            storyboard.addObjectDiagram("Tina", tina, "Savings", tinaAccount);
            storyboard.dumpHTML();
        }
    }

