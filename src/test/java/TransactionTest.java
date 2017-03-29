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
        trans.setAmount(-5);
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

    @Test(expected=IllegalArgumentException.class)
    // setDate with null to see if it will throw IllegalArgumentException
    public void setgetDateTestNULL(){
        Date dt = new Date(null);

        // set date with null
        trans.setDate(dt);
    }

    @Test
    // setTime and get the time to make sure you get the correct time
    public void setgetTime(){
        Date dt = new Date("03/19/2017 23:13:26");

        // set time
        trans.setTime(dt);
        assertTrue(dt == trans.getTime());
    }

    @Test(expected=IllegalArgumentException.class)
    // setTime with null to see if it will throw IllegalArgumentException
    public void setgetTimeTestNULL(){
        Date dt = new Date(null);

        // set date with null
        trans.setDate(dt);
    }

    @Test
    // setNote and get the note to make sure you get the correct note
    public void setgetNote(){
        String nt = "Testing Notes..";
        // set note
        trans.setNote(nt);
        assertTrue(nt == trans.getNote());
    }

    @Test
    // setNote and get the note to make sure you get the correct value
    public void setgetNoteNULL(){
        String nt = null;
        // set note
        trans.setNote(nt);
        assertTrue(nt == trans.getNote());
    }

    @Test
    // setNote and get the note to make sure you get the correct value
    public void setgetNoteEmpty(){
        String nt = " ";
        // set note
        trans.setNote(nt);
        assertTrue(nt == trans.getNote());
    }
}
