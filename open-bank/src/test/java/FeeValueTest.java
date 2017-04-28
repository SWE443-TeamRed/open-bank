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
    @Test (expected = IllegalArgumentException.class)
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
    }

    @Test
    public void testSDMLibFeeValueDuplicates3() {
        f1 = new FeeValue().withTransType(TransactionTypeEnum.DEPOSIT);
        f2 = new FeeValue().withTransType(TransactionTypeEnum.WITHDRAW);
        f3 = new FeeValue().withTransType(TransactionTypeEnum.CLOSE);
        f4 = new FeeValue().withTransType(TransactionTypeEnum.TRANSFER);
        f5 = new FeeValue().withTransType(TransactionTypeEnum.WITHDRAW);
        Bank b = new Bank().withFeeValue(f1, f2, f3, f4, f5);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testFeeValueTransTypeNull() {
        f1 = new FeeValue().withTransType(TransactionTypeEnum.DEPOSIT);
        f2 = new FeeValue();
        Bank b = new Bank().withFeeValue(f1, f2);
        f2.setTransType(null);
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

    @Test (expected = IllegalArgumentException.class)
    public void testFeeValueDuplicate() {
        f1 = new FeeValue().withTransType(TransactionTypeEnum.DEPOSIT);
        f2 = new FeeValue();
        Bank b = new Bank().withFeeValue(f1, f2);
        f2.setTransType(TransactionTypeEnum.DEPOSIT);
    }

    /**
     * NOTE: Values for BigInteger are offset by 9 decimal places for this test case. The real world values are commented
     * on the side.
     * 1) tests if the fees are accurately being calculated and integrated into the system.
     * 2) tests for successful linkage of transactions via Bank and their fee transactions
     */
    @Test
    public void testfeeValueExhaustive() {
        b = new Bank();
        f1 = new FeeValue()
                .withBank(b)
                .withTransType(TransactionTypeEnum.DEPOSIT)
                .withPercent(new BigInteger("0")); //0
        f2 = new FeeValue()
                .withBank(b)
                .withTransType(TransactionTypeEnum.WITHDRAW)
                .withPercent(new BigInteger("10000000")); //.01
        f3 = new FeeValue()
                .withBank(b)
                .withTransType(TransactionTypeEnum.TRANSFER)
                .withPercent(new BigInteger("50000000")); //.05
        User u1 = new User()
                .withName("tina")
                .withBank(b)
                .withLoggedIn(true);
        User u2 = new User()
                .withName("nick")
                .withBank(b)
                .withLoggedIn(true);
        User u3 = new User()
                .withName("ulno")
                .withEmployingBank(b)
                .withLoggedIn(true);
        Account a1 = new Account()
                .withOwner(u1)
                .withAccountnum(1)
                .withBalance(new BigInteger("1000000000000")) //1000
                .withBank(b);
        Account a2 = new Account()
                .withOwner(u2)
                .withAccountnum(2)
                .withBalance(new BigInteger("150000000000"))  //150
                .withBank(b);

        Account a3 = new Account()
                .withOwner(u3)
                .withAccountnum(3)
                .withBalance(new BigInteger("100000000000"))  //100
                .withEmployingBank(b);

        /* 1 */
        a1.withdraw(new BigInteger("500000000000"));           //500
        a2.deposit(new BigInteger("500000000000"));            //500
        assertEquals(new BigInteger("495000000000"), a1.getBalance());               //495
        assertEquals(new BigInteger("650000000000"), a2.getBalance());               //650
        assertEquals(new BigInteger("105000000000"), a3.getBalance());               //105

        a2.transferToAccount(new BigInteger("200000000000"), a1,"toaccount");   //200
        assertEquals(new BigInteger("695000000000"), a1.getBalance());               //695
        assertEquals(new BigInteger("440000000000"), a2.getBalance());               //440
        assertEquals(new BigInteger("115000000000"), a3.getBalance());               //115

        //Significantly changing percent for a test in terms of calculations
        f2.setPercent(new BigInteger("500000000"));             //.5
        a1.withdraw(new BigInteger("100000000000"));                                 //100
        assertEquals(new BigInteger("545000000000"), a1.getBalance());               //545
        assertEquals(new BigInteger("165000000000"), a3.getBalance());               //165

        /* 2 */
        assertEquals(TransactionTypeEnum.WITHDRAW, b.getTransaction().getTransType());
        assertEquals(new BigInteger("50000000000"), b.getTransaction().getFee().getAmount());
        assertEquals(TransactionTypeEnum.FEE, b.getTransaction().getFee().getTransType());

        assertEquals(TransactionTypeEnum.TRANSFER, b.getTransaction().getPrevious().getTransType());
        assertEquals(new BigInteger("10000000000"), b.getTransaction().getPrevious().getFee().getAmount());
        assertEquals(TransactionTypeEnum.FEE, b.getTransaction().getPrevious().getFee().getTransType());

        assertEquals(TransactionTypeEnum.DEPOSIT, b.getTransaction().getPrevious().getPrevious().getTransType());
        assertEquals(new BigInteger("0"), b.getTransaction().getPrevious().getPrevious().getFee().getAmount());
        assertEquals(TransactionTypeEnum.FEE, b.getTransaction().getPrevious().getPrevious().getFee().getTransType());

        assertEquals(TransactionTypeEnum.WITHDRAW, b.getTransaction().getPrevious().getPrevious().getPrevious().getTransType());
        assertEquals(new BigInteger("5000000000"), b.getTransaction().getPrevious().getPrevious().getPrevious().getFee().getAmount());
        assertEquals(TransactionTypeEnum.FEE, b.getTransaction().getPrevious().getPrevious().getPrevious().getFee().getTransType());
    }
}