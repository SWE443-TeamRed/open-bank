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
    public void testSdmLib() {
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

    /**
     * setName() Test Cases
     */
    @Test
    public void testSetName() {
        this.sam = new User()
                .withName("Sam")
                .withUserID("sam2")
                .withEmail("sl2@studyright.com")
                .withPassword("SWE443")
                .withPhone(123123123);
        this.sam.setName("samuelJackson");
        assertTrue("Incorrect Name", this.sam.getName().equals("samuelJackson"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSetName2() {
        this.sam = new User()
                .withName("Sam")
                .withUserID("sam2")
                .withEmail("sl2@studyright.com")
                .withPassword("SWE443")
                .withPhone(123123123);
        this.sam.setName(null);
    }

    /**
     * testSetUID() Test Cases
     */
    @Test
    public void testSetUID() {
        this.sam = new User()
                .withName("Sam")
                .withUserID("sam2")
                .withEmail("sl2@studyright.com")
                .withPassword("SWE443")
                .withPhone(123123123);
        this.sam.setUserID("samuelJackson");
        assertTrue("Incorrect UID", this.sam.getUserID().equals("samuelJackson"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSetUID2() {
        this.sam = new User()
                .withName("Sam")
                .withUserID("sam2")
                .withEmail("sl2@studyright.com")
                .withPassword("SWE443")
                .withPhone(123123123);
        this.sam.setUserID(null);
    }

    /**
     * SetEmail Test Cases (Tests 3 - X involve regular expressions)
     */
    @Test
    public void testSetEmail() {
        this.sam = new User()
                .withName("Sam")
                .withUserID("sam2")
                .withEmail("sl2@studyright.com")
                .withPassword("SWE443")
                .withPhone(123123123);
        this.sam.setEmail("samuelJackson@studyright.com");
        assertTrue("Incorrect Email", this.sam.getEmail().equals("samuelJackson@studyright.com"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSetEmail2() {
        this.sam = new User()
                .withName("Sam")
                .withUserID("sam2")
                .withEmail("sl2@studyright.com")
                .withPassword("SWE443")
                .withPhone(123123123);
        this.sam.setEmail(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSetEmail3() {
        this.sam = new User()
                .withName("Sam")
                .withUserID("sam2")
                .withEmail("sl2@studyright.com")
                .withPassword("SWE443")
                .withPhone(123123123);
        this.sam.setEmail("samuelJackson@.com");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSetEmail4() {
        this.sam = new User()
                .withName("Sam")
                .withUserID("sam2")
                .withEmail("sl2@studyright.com")
                .withPassword("SWE443")
                .withPhone(123123123);
        this.sam.setEmail("@studyright.com");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSetEmail5() {
        this.sam = new User()
                .withName("Sam")
                .withUserID("sam2")
                .withEmail("sl2@studyright.com")
                .withPassword("SWE443")
                .withPhone(123123123);
        this.sam.setEmail("@.com");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSetEmail6() {
        this.sam = new User()
                .withName("Sam")
                .withUserID("sam2")
                .withEmail("sl2@studyright.com")
                .withPassword("SWE443")
                .withPhone(123123123);
        this.sam.setEmail("studyright.com");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSetEmail7() {
        this.sam = new User()
                .withName("Sam")
                .withUserID("sam2")
                .withEmail("sl2@studyright.com")
                .withPassword("SWE443")
                .withPhone(123123123);
        this.sam.setEmail("studyright@");
    }

    /**
     * testSetPassword() Test Cases
     */
    @Test
    public void testSetPassword() {
        this.sam = new User()
                .withName("Sam")
                .withUserID("sam2")
                .withEmail("sl2@studyright.com")
                .withPassword("SWE443")
                .withPhone(123123123);
        this.sam.setPassword("StudyRight");
        assertTrue("Incorrect Password", this.sam.getPassword().equals("StudyRight"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSetPassword2() {
        this.sam = new User()
                .withName("Sam")
                .withUserID("sam2")
                .withEmail("sl2@studyright.com")
                .withPassword("SWE443")
                .withPhone(123123123);
        this.sam.setPassword(null);
    }

    /**
     * Login Test Cases
     */
    @Test
    public void testLogin() {
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
    public void testLogin2() {
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
    public void testLogin3() {
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
    public void testLogin4() {
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
