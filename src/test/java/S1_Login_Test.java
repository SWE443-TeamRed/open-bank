import org.junit.Test;
import org.sdmlib.openbank.Account;
import org.sdmlib.openbank.User;
import org.sdmlib.storyboards.Storyboard;

/**
 * Created by hlope on 3/29/2017.
 */
public class S1_Login_Test {

/***1***
    Title: Tina logs into her banking account
*(Pre)* PTina has her phone and wants to log into her banking account. The app is installed on her phone but Tina is not logged in
	1) Tina opens her banking app
	2) Tina is at the login screen
	3) Tina enters her username in the username field
	4) Tina enters her password in the password field
	5) Tina selects the login option
*(Post)* Tina is at her homescreen and has logined into her banking account* @see <a href='../../../doc/TinaLogsIntoHerBankingAccount.html'>TinaLogsIntoHerBankingAccount.html</a>
 */



    @Test
    public void TinaLogsIntoHerBankingAccount(){
        System.out.println("[[Tina Logs into her banking account]]");
        Storyboard storyboard = new Storyboard();

        User tina = new User().withName("Tina").withIsAdmin(false);
        Account checking = tina.createAccount().withEmail("henryapez@gmail.com").withPhone(0).withUsername("henryapez").withPassword("1111").withBalance(100);

        storyboard.addObjectDiagram("Tina", tina);
        System.out.println("Inital User: Tina");
        /*
        TODO: Create a User.toString that prints the user's profile info
         */
        storyboard.add("0. (Pre-condition) Tina has her phone and wants to log into her banking account. The app is installed on her phone but Tina is not logged in");
        storyboard.assertEquals("Tina is currently not logged in", false,checking.isIsLoggedIn());
        storyboard.assertEquals("Tina cannot view her balance", 0.0,checking.getBalance());
        /*
            TODO: ASSERT THAT TINA CANNOT CHECK BALANCE, DEPOSIT MONEY, AND WITHDRAW MONEY
         */
        storyboard.add("1) Tina opens her banking app\n" +
                "\t2) Tina is at the login screen\n" +
                "\t3) Tina enters her username in the username field\n" +
                "\t4) Tina enters her password in the password field");
        checking.login("henryapez","1111");
        storyboard.add("5) Tina has loged into her account and can now view her balance");
        storyboard.assertEquals("Tina is now logged in", true, checking.isIsLoggedIn());
        storyboard.assertEquals("Tina can view the 100 dollars in her account", 100.0,checking.getBalance());


//        storyboard.assertTrue("Bob goes first", player1.isMyTurn());
//        storyboard.assertFalse("Joe goes second", player2.isMyTurn());



    }
}
