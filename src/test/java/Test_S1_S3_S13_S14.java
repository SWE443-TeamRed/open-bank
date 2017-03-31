import org.junit.Test;
import org.sdmlib.openbank.Account;
import org.sdmlib.openbank.User;
import org.sdmlib.storyboards.Storyboard;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * Created by hlope on 3/29/2017.
 */
public class Test_S1_S3_S13_S14 {

        /*
           Title: Tina logs into her banking account
    *(Pre)* PTina has her phone and wants to log into her banking account. The app is installed on her phone but is not opens
        1) Tina opens her banking app
        2) Tina is at the login screen
        3) Tina enters her username in the username field
        4) Tina enters her password in the password field
        5) Tina selects the login option
    *(Post)* Tina is at her homescreen and has logined into her banking account
     * @see <a href='../../../doc/loginTest.html'>loginTest.html</a>
 * @see <a href='../../../doc/S1_loginTest.html'>S1_loginTest.html</a>
 */
    @Test
    public void S1_loginTest(){
        System.out.println("[[Tina Logs into her banking account]]");
        Storyboard storyboard = new Storyboard();

        //Initial scenario
        User tina = new User()
                .withName("Tina")
                .withUserID("tina1")
                .withPassword("tinapass")
                .withIsAdmin(false);
        Account checking = new Account()
                .withAccountnum(1)
                .withOwner(tina)
                .withBalance(100);


        storyboard.addObjectDiagram("Tina", tina);
        System.out.println("Inital User: Tina");

        //Precondition
        storyboard.add("0. (Pre-condition) Tina has her phone and wants to log into her banking account. The app is installed on her phone but Tina is not logged in");
                //Assert inital scenario
                storyboard.assertEquals("Tina is currently not logged in to her checking account", false,tina.isLoggedIn());
                storyboard.assertEquals("Tina cannot view her balance", 0.0,checking.getBalance());
        //Actions
                //Tina correctly logs in
                storyboard.add("1) Tina opens her banking app\n" +
                        "\t2) Tina is at the login screen\n" +
                        "\t3) Tina enters her username in the username field\n" +
                        "\t4) Tina enters her password in the password field");
                tina.login("tina1","tinapass");
        //Post condition
                storyboard.add("5) (Post-Condition)  Tina has loged into her account and can now view her balance");
                storyboard.assertEquals("Tina is now logged in", true, tina.isLoggedIn());
                storyboard.assertEquals("Tina can view the 100 dollars in her account", 100.0,checking.getBalance());


        System.out.println("Completed: loginTest \n\n ");

    }


    /*
        User interfaces should prevent new user from being created is userID, password, and isAdmin is not specified
     */
    @Test
    public void CreateAUserTest(){
        System.out.println("[[Tina is created]]");
        User tina=null;
        //Initial scenario
        System.out.println("Inital Scenario: Tina does not exists");
        assertEquals("Tina does not exists", null,tina);
        tina = new User()
                .withName("Tina")
                .withIsAdmin(false)
                .withUserID("tina1");
        System.out.println("Created Tina with credentials "+tina.toString());
        assertNotNull("Tina is created with name: Tina userID: tina1,isAdmin:false",tina);
        assertEquals("Tina is created with name: Tina","Tina",tina.getName());
        assertEquals("Tina is created with userID: tina1","tina1",tina.getUserID());
        assertEquals("Tina is created with isAdmin:false",false,tina.isIsAdmin());

        System.out.println("Completed: CreateAUserTest \n\n ");
    }

    /*
           **3**
    Title: Tina views her balance
    *(Pre)* Tina wants to view her balance to see if she can make rent
        1) Tine logs into her banking account -> See S1
        2) Tina is now in the main menu of her account
        3) Tina selects view balance
    *(Post)* Tina is presented with her balance and is happy with it
     */
    @Test
    public void S3_viewBalanceTest(){
        System.out.println("[[Tina views her balance]]");

        //Initial scenario
            User tina = new User()
                    .withName("Tina")
                    .withIsAdmin(false)
                    .withUserID("tina1")
                    .withPassword("tinapass");
            Account checking = new Account()
                    .withAccountnum(1)
                    .withOwner(tina)
                    .withBalance(100);
            System.out.println("Inital Scenario: Tina has a checking account and credentials" + "\n"+ tina.toString());
            assertNotNull("Tina exists",tina);
            assertEquals("Tina's name: Tina","Tina",tina.getName());
            assertEquals("Tina's userID: tina1","tina1",tina.getUserID());
            assertEquals("Tina's isAdmin:false",false,tina.isIsAdmin());

        //Actions
            //Not logged in, cannot view
            System.out.println("1) Tina tries to view balance when she is not logged in, gets balance of " + checking.getBalance());
            assertEquals("Tina has not logged in, cannot view balance",0.0,checking.getBalance());

            //Login can view
            System.out.println("2) Tina logins in, gets balance of " + checking.getBalance());
                tina.login("tina1","tinapass");

        //Post condition
            assertEquals("(Post-condition) Tina has logged in, can view balance",100.0,checking.getBalance());
            System.out.println("(Post-condition) Tina has logged in, can view balance" + checking.getBalance());

        System.out.println("Completed: viewBalanceTest \n\n ");

    }


    /*
        See Scenarios S13 and S14


        **13**
        Title: Steve Rogers unsuccessfully logs into his bank account
        *(Pre)* Steve Rogers has an existing bank account with a UID, "CaptainAmerica", and password, "TheAvengers". Steve wants
                to login to his account through the mobile app on his phone.
                1) Steve opens his banking application
                2) Steve is at the login screen ready to input his credentials
                3) Steve enters "CaptAmerica" in the username field
                4) Steve enters "TheAvengers" in the password field
                5) Steve submits his inputs to be verified by selecting "Login"
                6) A warning prompt comes up stating that either the Username or Password was incorrectly entered
        *(Post)* Steve Rogers is at the login screen ready to reenter his credentials

         **14**
        Title: Steve Rogers unsuccessfully logs into his bank account
        *(Pre)* Steve Rogers has an existing bank account with a UID, "CaptainAmerica", and password, "TheAvengers". Steve wants
                to login to his account through the mobile app on his phone.
                1) Steve opens his banking application
                2) Steve is at the login screen ready to input his credentials
                3) Steve enters "CaptainAmerica" in the username field
                4) Steve enters "Avengers" in the password field
                5) Steve submits his inputs to be verified by selecting "Login"
                6) A warning prompt comes up stating that either the Username or Password was incorrectly entered
        *(Post)* Steve Rogers is at the login screen ready to reenter his credentials

 * @see <a href='../../../doc/incorrectLoginAttemptsTest.html'>incorrectLoginAttemptsTest.html</a>
 * @see <a href='../../../doc/S13_S14_incorrectLoginAttemptsTest.html'>S13_S14_incorrectLoginAttemptsTest.html</a>
 */
    @Test
    public void S13_S14_incorrectLoginAttemptsTest(){
        System.out.println("[[Tina attempts to login with wrong credentials]]");

        Storyboard storyboard = new Storyboard();
        //Initial scenario
        User tina = new User()
                .withName("Tina")
                .withUserID("tina1")
                .withPassword("1111")
                .withIsAdmin(false);
        Account checking = new Account()
                .withAccountnum(1)
                .withOwner(tina)
                .withBalance(100);
        storyboard.addObjectDiagram("Tina", tina);
        System.out.println("Inital User: " + tina.toString() +"\n----");



        //Incorrect username
        tina.login("wrongusername","1111");
        System.out.println("Testing: incorrect username");
        storyboard.assertEquals("Tina tries to login with wrong username", false,tina.isLoggedIn());
        storyboard.assertEquals("Tina cannot view her balance", 0.0,checking.getBalance());


        //Incorrect password
        tina.login("tina1","wrongpassword");
        System.out.println("Testing incorrect password");
        storyboard.assertEquals("Tina tries to login with wrong password", false,tina.isLoggedIn());
        storyboard.assertEquals("Tina cannot view her balance", 0.0,checking.getBalance());

        //Incorrect username and password
        tina.login("wrongusername","wrongpassword");
        System.out.println("Testing: incorrect username and password");
        storyboard.assertEquals("Tina tries to login with wron username and password", false,tina.isLoggedIn());
        storyboard.assertEquals("Tina cannot view her balance", 0.0,checking.getBalance());

        System.out.println("Completed: incorrectLoginAttemptsTest \n\n ");
    }


}
