import org.junit.Test;
import org.sdmlib.openbank.User;
import java.util.Date;
import static org.junit.Assert.*;

/**
 * Created by delta on 4/9/2017.
 */
public class UserExhaustiveTests {

    //Test for successful login
    @Test
    public void testLogin1(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
    }

    //Unsuccessful login due to incorrect username
    @Test
    public void testLogin2(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby", "BillyB$b1");
        assertFalse(bob.isLoggedIn());
    }

    //Unsuccessful login due to incorrect password
    @Test
    public void testLogin3(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b");
        assertFalse(bob.isLoggedIn());
    }

    //Unsuccessful login due to incorrect username and password
    @Test
    public void testLogin4(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby", "BillyB$b");
        assertFalse(bob.isLoggedIn());
    }

    //Null username
    @Test (expected = IllegalArgumentException.class)
    public void testLogin5(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login(null, "BillyB$b1");
    }

    //Null password
    @Test (expected = IllegalArgumentException.class)
    public void testLogin6(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", null);
    }

    //Null username and password
    @Test (expected = IllegalArgumentException.class)
    public void testLogin7(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login(null, null);
    }

    //Successful logout
    @Test
    public void testLogout(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.logout();
        assertFalse(bob.isLoggedIn());
    }

    //Logout without being attempting to log in
    @Test
    public void testLogout2(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        assertFalse(bob.isLoggedIn());
        assertFalse(bob.logout());
    }

    //Logout without successfully logging in
    @Test
    public void testLogout3(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby", "BillyB$b");
        assertFalse(bob.isLoggedIn());
        assertFalse(bob.logout());
    }

    //SetName without logging in
    @Test (expected = Exception.class)
    public void testSetName(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.setName("Bobby");
    }

    //Successful setName
    @Test
    public void testSetName2(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setName("Bobby");
        assertEquals("Bobby", bob.getName());
    }

    //SetName with null
    @Test (expected = IllegalArgumentException.class)
    public void testSetName3(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setName(null);
    }

    //SetName with digits
    @Test (expected = IllegalArgumentException.class)
    public void testSetName4(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setName("b0bby");
    }

    //SetName with digits
    @Test (expected = IllegalArgumentException.class)
    public void testSetName5(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setName("Bobby!");
    }

    //SetUserID
    @Test (expected = Exception.class)
    public void testSetUserID(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.setUserID("BuilderBob");
    }

    //SetUserID null
    @Test (expected = IllegalArgumentException.class)
    public void testSetUserID2(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setUserID(null);
    }

    //SetUserID to the current UserID
    @Test (expected = IllegalArgumentException.class)
    public void testSetUserID3(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setUserID("bobby17");
    }

    //Successfully SetUserID
    @Test
    public void testSetUserID4(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setUserID("BobBuilder");
        assertEquals("BobBuilder", bob.getUserID());
    }

    //SetUserID with a character string less than 4
    @Test (expected = IllegalArgumentException.class)
    public void testSetUserID5(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setUserID("B");
    }

    //SetUserID with a character string greater than 12
    @Test (expected = IllegalArgumentException.class)
    public void testSetUserID6(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setUserID("Bobbbbbbbbbbbbbbbbbbbb17");
    }

    //SetPassword without logging in
    @Test (expected = Exception.class)
    public void testSetPassword(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        assertFalse(bob.isLoggedIn());
        bob.setPassword("Bob@123");
    }

    //Successful setPassword
    @Test
    public void testSetPassword2(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setPassword("Bob@123");
        assertEquals("Bob@123", bob.getPassword());
    }

    //SetPassword to the current password
    @Test (expected = IllegalArgumentException.class)
    public void testSetPassword3(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setPassword("BillyB$b1");
    }

    //SetPassword without special character
    @Test (expected = IllegalArgumentException.class)
    public void testSetPassword4(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setPassword("BillyBob1");
    }

    //SetPassword without digit
    @Test (expected = IllegalArgumentException.class)
    public void testSetPassword5(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setPassword("BillyB$b");
    }

    //SetPassword without uppercase
    @Test (expected = IllegalArgumentException.class)
    public void testSetPassword6(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setPassword("billyb$b");
    }

    //SetPassword without lowercase
    @Test (expected = IllegalArgumentException.class)
    public void testSetPassword7(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setPassword("BILLYB$B");
    }

    //SetPassword with less than 4 characters
    @Test (expected = IllegalArgumentException.class)
    public void testSetPassword8(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setPassword("Bb$");
    }

    //SetPassword with greater than 12 characters
    @Test (expected = IllegalArgumentException.class)
    public void testSetPassword9(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setPassword("BillyB$$bby17");
    }

    //SetEmail when not logged in
    @Test (expected = Exception.class)
    public void testSetEmail(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.setEmail("billybob@live.com");
    }

    //Successful setEmail
    @Test
    public void testSetEmail2(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setEmail("billybob@live.com");
        assertEquals("billybob@live.com", bob.getEmail());
    }

    //SetEmail format broken: userid@address.domain
    @Test (expected = IllegalArgumentException.class)
    public void testSetEmail3(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setEmail("@live.com");
    }

    //SetEmail format broken by address: userid@address.domain
    @Test (expected = IllegalArgumentException.class)
    public void testSetEmail4(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setEmail("billybob@.com");
    }

    //SetEmail format broken by domain: userid@address.domain
    @Test (expected = IllegalArgumentException.class)
    public void testSetEmail5(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setEmail("billybob@live.");
    }

    //SetEmail format broken by excluding @: userid@address.domain
    @Test (expected = IllegalArgumentException.class)
    public void testSetEmail6(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setEmail("billyboblive.com");
    }

    //SetEmail format broken by excluding all special characters: userid@address.domain
    @Test (expected = IllegalArgumentException.class)
    public void testSetEmail7(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setEmail("billyboblivecom");
    }

    //SetEmail null
    @Test (expected = IllegalArgumentException.class)
    public void testSetEmail8(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setEmail(null);
    }

    //SetEmail to current Email
    @Test (expected = IllegalArgumentException.class)
    public void testSetEmail9(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setEmail("steverog@live.com");
    }

    //
    @Test (expected = Exception.class)
    public void testSetPhone(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.setPhone("7037654321");
    }

    //Successful SetPhone
    @Test
    public void testSetPhone2(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setPhone("7037654321");
        assertEquals("7037654321", bob.getPhone());
    }

    //SetPhone with invalid PhoneNo length
    @Test (expected = IllegalArgumentException.class)
    public void testSetPhone3(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setPhone("703");
    }

    //SetPhone with invalid PhoneNo length
    @Test (expected = IllegalArgumentException.class)
    public void testSetPhone4(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setPhone("70312345678");
    }

    //SetPhone with nondigit characters
    @Test (expected = IllegalArgumentException.class)
    public void testSetPhone5(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setPhone("7o312E4567");
    }

    //SetPhone with nondigit characters
    @Test (expected = IllegalArgumentException.class)
    public void testSetPhone6(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setPhone("703!234$67");
    }

    //SetPhone null
    @Test (expected = IllegalArgumentException.class)
    public void testSetPhone7(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setPhone(null);
    }

    //SetPhone to current phone
    @Test (expected = IllegalArgumentException.class)
    public void testSetPhone8(){
        User bob = new User()
                .withName("Bob")
                .withUserID("bobby17")
                .withPassword("BillyB$b1")
                .withEmail("steverog@live.com")
                .withPhone("7031234567");
        bob.login("bobby17", "BillyB$b1");
        assertTrue(bob.isLoggedIn());
        bob.setPhone("7031234567");
    }
}
