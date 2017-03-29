import org.junit.Before;
import org.junit.Test;
import org.sdmlib.openbank.Transaction;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;

/**
 * Created by FA on 3/28/2017.
 */
public class TransactionTest {
    Transaction trans;

    @Before
    public void setUp() throws Exception{
        trans = new Transaction();
    }

    // this will test for negative value in setAmount
    // it will throw an IllegalArgumentException if the value is negative

    @Test(expected=IllegalArgumentException.class)
    public void testNegative()throws Exception{

        try {
            trans.setAmount(-5);
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("Amount is not valid!"));

        }
    }

    @Test
    // setAmount and get the amount to make sure you get the correct amount
    public void setgetAmount(){
        trans.setAmount(50.55);
        assertTrue(50.55 == trans.getAmount());
    }

    @Test
    // setDate and get the date to make sure you get the correct date
    public void setgetDate(){
        Date dt = new Date("03/19/2017");

        // set date
        trans.setDate(dt);
        assertTrue(dt == trans.getDate());
    }

    @Test
    // setTime and get the time to make sure you get the correct time
    public void setgetTime(){
        Date dt = new Date("03/19/2017 23:13:26");

        // set time
        trans.setTime(dt);
        assertTrue(dt == trans.getTime());
    }

    @Test
    // setNote and get the note to make sure you get the correct note
    public void setgetNote(){
        String nt = "Testing Notes..";
        // set note
        trans.setNote(nt);
        assertTrue(nt == trans.getNote());
    }

}
