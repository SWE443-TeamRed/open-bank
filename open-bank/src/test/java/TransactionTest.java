import org.junit.Before;
import org.junit.Test;
import org.sdmlib.openbank.Transaction;
import org.sdmlib.openbank.TransactionTypeEnum;


import java.util.Date;
import static org.junit.Assert.*;

/**
 * Created by FA on 3/28/2017.
 */
public class TransactionTest {
    Transaction trans;

    @Before
    public void setUp() throws Exception{
        // initiate transaction class
        trans = new Transaction();
    }

    @Test(expected=IllegalArgumentException.class)
    // this will test for negative value in setAmount
    // it will throw an IllegalArgumentException if the value is negative
    public void testSetAmountNegative()throws Exception{
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
        trans.setCreationdate(dt);
        assertTrue(dt == trans.getCreationdate());
    }

    @Test(expected=IllegalArgumentException.class)
    // setDate with null to see if it will throw IllegalArgumentException
    public void setgetDateTestNULL(){
        Date dt = new Date(null);

        // set date with null
        trans.setCreationdate(dt);
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

    @Test(expected=IllegalArgumentException.class)
    // setTrans Type with null should throws IllegalArgumentException exception
    public void setNullTransType(){
        // set type
        trans.setTransType(null);

    }

    @Test
    // setTrans Type and get the type to make sure you get the correct type
    public void setgetTransTypeWithdraw(){
        // set type
        trans.setTransType(TransactionTypeEnum.Withdraw);

        assertTrue(TransactionTypeEnum.Withdraw == trans.getTransType());
    }

    @Test
    // setTrans Type and get the type to make sure you get the correct type
    public void setgetTransTypeDeposit(){
        // set type
        trans.setTransType(TransactionTypeEnum.Deposit);

        assertTrue(TransactionTypeEnum.Deposit == trans.getTransType());
    }
}
