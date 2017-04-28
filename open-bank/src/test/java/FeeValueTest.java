/**
 * Created by delta on 4/24/2017.
 */

/**
 * Created by Savindi on 4/1/2017.
 */
import org.junit.Test;
import org.sdmlib.openbank.*;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class FeeValueTest {
    private Bank b;
    private FeeValue f1, f2, f3, f4, f5, f6;

    //can't add too many feevalues (in the case of null feevalues
    @Test
    public void testSDMLibFeeValueCapacity() {
        b = new Bank();
        f1 = new FeeValue()
                .withBank(b);
        f2 = new FeeValue()
                .withBank(b);
        f3 = new FeeValue()
                .withBank(b);
        f4 = new FeeValue()
                .withBank(b);
        f5 = new FeeValue()
                .withBank(b);
        f6 = new FeeValue()
                .withBank(b);
        assertEquals(5, b.getFeeValue().size());
    }

    @Test
    public void testSDMLibFeeValueCapacity2() {
        f1 = new FeeValue();
        f2 = new FeeValue();
        f3 = new FeeValue();
        f4 = new FeeValue();
        f5 = new FeeValue();
        f6 = new FeeValue();
        Bank b = new Bank().withFeeValue(f1, f2, f3, f4, f5, f6);
        assertEquals(5, b.getFeeValue().size());
    }

    @Test
    public void testSDMLibFeeValueDuplicates() {
        b = new Bank();
        f1 = new FeeValue()
                .withTransType(TransactionTypeEnum.DEPOSIT)
                .withBank(b);
        f2 = new FeeValue()
                .withTransType(TransactionTypeEnum.WITHDRAW)
                .withBank(b);
        f3 = new FeeValue()
                .withTransType(TransactionTypeEnum.CLOSE)
                .withBank(b);
        f4 = new FeeValue()
                .withTransType(TransactionTypeEnum.TRANSFER)
                .withBank(b);
        f5 = new FeeValue()
                .withTransType(TransactionTypeEnum.WITHDRAW)
                .withBank(b);
        assertEquals(4, b.getFeeValue().size());
    }

    /**
     * NOTE: Although f5 is a duplicate, this the feeValue size should still be 5 because the transType is set post-creation
     *       Instead, the transType of f5 will be denied
     */
    @Test
    public void testSDMLibFeeValueDuplicates2() {
        b = new Bank();
        f1 = new FeeValue()
                .withBank(b)
                .withTransType(TransactionTypeEnum.DEPOSIT);
        f2 = new FeeValue()
                .withBank(b)
                .withTransType(TransactionTypeEnum.WITHDRAW);
        f3 = new FeeValue()
                .withBank(b)
                .withTransType(TransactionTypeEnum.CLOSE);
        f4 = new FeeValue()
                .withBank(b)
                .withTransType(TransactionTypeEnum.TRANSFER);
        f5 = new FeeValue()
                .withBank(b)
                .withTransType(TransactionTypeEnum.WITHDRAW);
        assertEquals("f5 should be added as the transType is added in post", 5, b.getFeeValue().size());
    }

    @Test
    public void testSDMLibFeeValueDuplicates3() {
        f1 = new FeeValue().withTransType(TransactionTypeEnum.DEPOSIT);
        f2 = new FeeValue().withTransType(TransactionTypeEnum.WITHDRAW);
        f3 = new FeeValue().withTransType(TransactionTypeEnum.CLOSE);
        f4 = new FeeValue().withTransType(TransactionTypeEnum.TRANSFER);
        f5 = new FeeValue().withTransType(TransactionTypeEnum.WITHDRAW);
        Bank b = new Bank().withFeeValue(f1, f2, f3, f4, f5);
        assertEquals(4, b.getFeeValue().size());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testFeeValueSetTransTypeFEE(){
        b = new Bank();
        f1 = new FeeValue()
                .withBank(b)
                .withTransType(TransactionTypeEnum.FEE);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testFeeValueSetTransTypeFEE2(){
        b = new Bank();
        f1 = new FeeValue()
                .withBank(b);
        f1.setTransType(TransactionTypeEnum.FEE);
    }

    /*
    @Test
    public void feeValue() {
        b = new Bank();
        f1 = new FeeValue()
                .withBank(b)
                .withTransType(TransactionTypeEnum.DEPOSIT)
                .withPercent(BigInteger.valueOf(0));
        f2 = new FeeValue()
                .withBank(b)
                .withTransType(TransactionTypeEnum.WITHDRAW)
                .withPercent(BigInteger.valueOf(10000000)); //.01
        f3 = new FeeValue()
                .withBank(b)
                .withTransType(TransactionTypeEnum.TRANSFER)
                .withPercent(BigInteger.valueOf(50000000)); //.05
        User u1 = new User()
                .withName("tina")
                .withBank(b);
        User u2 = new User()
                .withName("nick")
                .withBank(b);
        User u3 = new User()
                .withName("ulno")
                .withEmployingBank(b);
        Account a1 = new Account()
                .withOwner(u1)
                .withAccountnum(1)
                .withBalance(BigInteger.valueOf(1000000000000)) //1000
                .withBank(b);
        Account a2 = new Account()
                .withOwner(u2)
                .withAccountnum(2)
                .withBalance(BigInteger.valueOf(150000000000)) //150
                .withBank(b);

        Account a3 = new Account()
                .withOwner(u3)
                .withAccountnum(3)
                .withBalance(100)
                .withEmployingBank(b);

        a1.withdraw(500);
        a2.deposit(500);
        assertEquals(495, a1.getBalance(), 0);
        assertEquals(650, a2.getBalance(), 0);

        a2.transferToAccount(200, a1, "Here you go");
    }
    */
}
