import org.junit.Test;
import org.sdmlib.openbank.Account;
import org.sdmlib.openbank.User;
import static org.junit.Assert.*;

/**
 * Created by delta on 4/3/2017.
 */
public class UserTests {
    private User steve, sam;
    private Account s;

    /**
     * Tests functionality of sdmLib adding attributes
     */
    @Test
    public void sdmLibTest() {
        this.steve = new User().withName("Steve Rogers")
                .withUserID("Captain")
                .withEmail("captainamerica@studyright.com")
                .withPassword("Avenger")
                .withPhone(123123123);
        assertTrue("UserID doesn't match", this.steve.getUserID().equals("Captain"));
        assertTrue("Email doesn't match", this.steve.getEmail().equals("captainamerica@studyright.com"));
        assertTrue("Password doesn't match", this.steve.getPassword().equals("Avenger"));
        assertEquals("Phone number doesn't match", 123123123, this.steve.getPhone());
    }

    // Test the setName() method
    @Test
    public void setName() {
        this.sam = new User()
                .withName("Sam")
                .withUserID("sam2")
                .withEmail("sl2@studyright.com")
                .withPassword("SWE443")
                .withPhone(123123123);
        this.sam.setName("samuelJackson");
        assertTrue("Incorrect Name", this.sam.getName().equals("samuelJackson"));
    }

    // Test the setUID() method
    @Test
    public void setUID() {
        this.sam = new User()
                .withName("Sam")
                .withUserID("sam2")
                .withEmail("sl2@studyright.com")
                .withPassword("SWE443")
                .withPhone(123123123);
        this.sam.setUserID("samuelJackson");
        assertTrue("Incorrect UID", this.sam.getUserID().equals("samuelJackson"));
    }

    // Test the setEmail() method
    @Test
    public void setEmail() {
        this.sam = new User()
                .withName("Sam")
                .withUserID("sam2")
                .withEmail("sl2@studyright.com")
                .withPassword("SWE443")
                .withPhone(123123123);
        this.sam.setEmail("samuelJackson@studyright.com");
        assertTrue("Incorrect Email", this.sam.getEmail().equals("samuelJackson@studyright.com"));
    }

    // Test the setEmail() method
    @Test
    public void setPassword() {
        this.sam = new User()
                .withName("Sam")
                .withUserID("sam2")
                .withEmail("sl2@studyright.com")
                .withPassword("SWE443")
                .withPhone(123123123);
        this.sam.setPassword("StudyRight");
        assertTrue("Incorrect Password", this.sam.getPassword().equals("StudyRight"));
    }

    /**
     * Login Tests
     */
    @Test
    public void loginTest() {
        this.sam = new User()
                .withName("Sam")
                .withUserID("sam2")
                .withEmail("sl2@studyright.com")
                .withPassword("SWE443")
                .withPhone(123123123);
        this.sam.login("sam2", "SWE443");
        assertTrue(this.sam.isLoggedIn());
    }
    //Testing for an incorrect password entry
    @Test
    public void loginTest2() {
        this.sam = new User()
                .withName("Sam")
                .withUserID("sam2")
                .withEmail("sl2@studyright.com")
                .withPassword("SWE443")
                .withPhone(123123123);
        this.sam.login("Sam", "SWE443");
        assertFalse("Entered Sam instead of sam2", this.sam.isLoggedIn());
    }

    //Testing for an correct login given a change in UserID
    @Test
    public void loginTest3() {
        this.sam = new User()
                .withName("Sam")
                .withUserID("sam2")
                .withEmail("sl2@studyright.com")
                .withPassword("SWE443")
                .withPhone(123123123);
        this.sam.setUserID("samuelJackson");
        this.sam.login("samuelJackson", "SWE443");
        assertTrue("Entered sam2 instead of samuelJackson", this.sam.isLoggedIn());
    }

    //Testing for an correct login given a change in password
    @Test
    public void loginTest4() {
        this.sam = new User()
                .withName("Sam")
                .withUserID("sam2")
                .withEmail("sl2@studyright.com")
                .withPassword("SWE443")
                .withPhone(123123123);
        this.sam.setPassword("StudyRight");
        this.sam.login("sam2", "StudyRight");
        assertTrue(this.sam.isLoggedIn());
    }
}