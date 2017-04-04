package org.sdmlib.openbank;

import org.junit.Test;
import org.sdmlib.storyboards.Storyboard;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by daniel on 3/29/17.
 */
public class JsonPersistencyTest {
    Transaction trans = new Transaction();

    /*
        Persistancy test
        User's account is persisted as is, without any tran
     * @see <a href='../../../../../../doc/UserActivityPersistance.html'>UserActivityPersistance.html</a>
 * @see <a href='../../../../../../doc/WithdrawDepositActivityPersistance.html'>WithdrawDepositActivityPersistance.html</a>
 */
    @Test
    public void testWithdrawDepositActivityPersistance() throws Exception {
        System.out.println("*******************************[[Tina makes withdraw and deposit transacitons in her acount]]*******************************");
        Storyboard storyboard = new Storyboard();

        JsonPersistency jsonPersistency = new JsonPersistency();

        //Initial scenario
        User tina = new User()
                .withName("Tina")
                .withUserID("tina1")
                .withPassword("tinapass")
                .withIsAdmin(false);
        Account checking = new Account()
                .withAccountnum(1)
                .withOwner(tina)
                .withBalance(100)
                .withCreationdate(new Date())
                .withCredit()
                .withDebit();

        storyboard.add("Precondition: Tina has a checking account, it has 100 dollars in it. She wants to make some transactions");
        storyboard.addObjectDiagram(tina);
        storyboard.addObjectDiagram(checking);

        storyboard.add("Tina logs in");
       // tina.login("tina1","tinapass");
        System.out.println("Tina logs in");

        storyboard.add("Tina withdraws 10 dollars from her checking, balance is now "+checking.getBalance());
        if(checking.withdraw(10.0))
            System.out.println("Transaction: Withdrew 10 dollars, balance is now "+checking.getBalance());
        storyboard.assertEquals("Withdraw 10",90.0,checking.getBalance());


        storyboard.add("Tina withdraws 20 dollars from her checking, balance is now "+checking.getBalance());
        if(checking.withdraw(20.0))
            System.out.println("Transaction: Withdrew 20 dollars, balance is now "+checking.getBalance());
        storyboard.assertEquals("Withdraw 20",70.0,checking.getBalance());


        storyboard.add("Tina withdraws 30 dollars from her checking, balance is now "+checking.getBalance());
        if(checking.withdraw(30.0))
            System.out.println("Transaction: Withdrew 30 dollars, balance is now "+checking.getBalance());
        storyboard.assertEquals("Withdraw 30",40.0,checking.getBalance());


        storyboard.add("Tina deposits 10 dollars from her checking, balance is now "+checking.getBalance());
        if(checking.deposit(10.0))
            System.out.println("Transaciton: Deposited 10 dollars, balance is now "+checking.getBalance());
        storyboard.assertEquals("Deposit 10",50.0,checking.getBalance());


        storyboard.add("Tina deposits 300 dollars from her checking, balance is now "+checking.getBalance());
        if(checking.deposit(300.0))
            System.out.println("Transaction: Deposited 300 dollars, balance is now "+checking.getBalance());
        storyboard.assertEquals("Deposit 300",350.0,checking.getBalance());


        //Logout and persist Tina's information
        storyboard.add("Loging out with balance of "+checking.getBalance());
        storyboard.addObjectDiagram(checking);
        storyboard.addObjectDiagram(tina);
        System.out.println("Loging out with balance of "+checking.getBalance()+" \n Credits: "+checking.getCredit().size() + " \n Debits: "+ checking.getDebit().size());
        tina.logout();

        storyboard.add("Tina logs back in");
        tina.login("tina1","tinapass");
        Account newchecking = tina.getAccount().get(0);

        storyboard.assertEquals("Tina has 2 credits from past transactions",2,newchecking.getCredit().size());
        storyboard.assertEquals("Tina has 3 debits from past transactions",3,newchecking.getDebit().size());
        storyboard.assertEquals("Tina has 350 dollars in the bank",350.0,checking.getBalance());

        System.out.println("Login - Retrieved data for Tina");
        System.out.println(newchecking.toString());
        System.out.println("UserID: " + newchecking.getOwner().getUserID());
        System.out.println("Name: " + newchecking.getOwner().getName());
        System.out.println("Credit: " + newchecking.getCredit().toString());
        System.out.println("Debits: " + newchecking.getDebit().toString());
        System.out.println("Balance: " + newchecking.getBalance());
    }



      /**
    * 
    * @see <a href='../../../../../../doc/TransferActivityPersistance.html'>TransferActivityPersistance.html</a>
 */
   @Test
    public void testTransferActivityPersistance() throws Exception {
        System.out.println("\n\n[[*******************************[[Tina transfers 10 dollars to Nick]]*******************************");
        Storyboard storyboard = new Storyboard();

        JsonPersistency jsonPersistency = new JsonPersistency();

        //Initial scenario

       //Create tina instance with userId and password, rest of her account and person data retrieved on login
        User tina = new User()
                .withUserID("tina1")
                .withName("Tina")
                .withPassword("tinapass");

        //New User Nick
        User nick = new User()
                .withName("Nick")
                .withUserID("nick1")
                .withName("Nick")
                .withPassword("nickpass")
                .withIsAdmin(false);
        Account nickchecking = new Account()
                .withAccountnum(2)
                .withOwner(nick)
                .withBalance(100)
                .withCreationdate(new Date())
                .withCredit()
                .withDebit();

        storyboard.add("Tina logs in");
        System.out.println("Tina logs in");
        tina.login("tina1","tinapass");
        nick.login("nick1","nickpass");
        Account checking = tina.getAccount().get(0);


       storyboard.add("Precondition: Tina wants to transfer 10 dollars from her checking to nick's checking, Tina currently has 350, Nick has 100");
       storyboard.addObjectDiagram(tina);
       storyboard.addObjectDiagram(checking);
       storyboard.addObjectDiagram(nick);
       storyboard.addObjectDiagram(nickchecking);
       storyboard.assertEquals("Tina has 350",350.0,checking.getBalance());
       storyboard.assertEquals("Nick has 100",100.0,nickchecking.getBalance());


       storyboard.add("Tina transfers 10 from her checking to Nick's checking");
        if(checking.transferToAccount(10.0,nickchecking,"Tina transfers 10 from her checking to Nick's checking"))
            System.out.println("Transaction: 10 from Tina's checking to Nick's checking ");
        storyboard.assertEquals("Tina lost 10",340.0,checking.getBalance());
       storyboard.assertEquals("Nick gained 10",110.0,nickchecking.getBalance());


       storyboard.add("Tina transfers 100 from her checking to Nick's checking");
       if(checking.transferToAccount(100.0,nickchecking,"Tina transfers 100 from her checking to Nick's checking"))
           System.out.println("Transaction: 100 from Tina's checking to Nick's checking ");
       storyboard.assertEquals("Tina lost 100",240.0,checking.getBalance());
       storyboard.assertEquals("Nick gained 100",210.0,nickchecking.getBalance());


       storyboard.add("Nick transfers 150 from his checking to Tina's checking");
       if(nickchecking.transferToAccount(150.0,checking,"Nick transfers 150 from his checking to Tina's checking"))
           System.out.println("Transaction: 150 from Nick's checking to Tina's checking ");
       storyboard.assertEquals("Nick lost 150",60.0,nickchecking.getBalance());
       storyboard.assertEquals("Tina gained 150",390.0,checking.getBalance());


        //Logout and persist Tina's information
        storyboard.add("Nick and Tina log out");
        storyboard.addObjectDiagram(checking);
        storyboard.addObjectDiagram(tina);
       storyboard.addObjectDiagram(nickchecking);
       storyboard.addObjectDiagram(nick);
        System.out.println("Loging out \n Tina has a balance of "+checking.getBalance()+" \n Credits: "+checking.getCredit().size() + " \n Debits: "+ checking.getDebit().size());
       System.out.println("Nick has a balance of "+nickchecking.getBalance()+" \n Credits: "+nickchecking.getCredit().size() + " \n Debits: "+ nickchecking.getDebit().size());

       tina.logout();
       nick.logout();


        //Ensure persistancy upon login
        storyboard.add("Tina and Nick log back in");

        //New users for Tina and Nick with saved data
       User newTina = new User().withUserID("tina1").withPassword("tinapass").withName("Tina");
       User newNick = new User().withUserID("nick1").withPassword("nickpass").withName("Nick");
       //Login and get their information
       newTina.login("tina1","tinapass");
       newNick.login("nick1","nickpass");

       Account newchecking = newTina.getAccount().get(0);
       Account newNickchecking = newNick.getAccount().get(0);
       System.out.println("Tina has "+newTina.getAccount().size() + " accounts \nNick has "+newNick.getAccount().size()+" accounts");
       storyboard.assertEquals("Tina has 1 account",1,newTina.getAccount().size());
       storyboard.assertEquals("Nick has 1 account",1,newNick.getAccount().size());

        storyboard.assertEquals("Tina has 3 credits from past transactions",3,newchecking.getCredit().size());
        storyboard.assertEquals("Tina has 5 debits from past transactions",5,newchecking.getDebit().size());
        storyboard.assertEquals("Tina has 340 dollars in the bank",390.0,newchecking.getBalance());

       storyboard.assertEquals("Nick has 2 credits from past transactions",2,newNickchecking.getCredit().size());
       storyboard.assertEquals("Nick has 1 debits from past transactions",1,newNickchecking.getDebit().size());
       storyboard.assertEquals("Nick has 210 dollars in the bank",60.0,newNickchecking.getBalance());


       System.out.println("Login - Retrieved data for Tina");
        System.out.println(newchecking.toString());
        System.out.println("UserID: " + newchecking.getOwner().getUserID());
        System.out.println("Name: " + newchecking.getOwner().getName());
        System.out.println("Credit: " + newchecking.getCredit().toString());
        System.out.println("Debits: " + newchecking.getDebit().toString());
        System.out.println("Balance: " + newchecking.getBalance());

       System.out.println("Login - Retrieved data for Nick*");
       System.out.println(newNickchecking.toString());
       System.out.println("\n\n\nUserID: " + newNickchecking.getOwner().getUserID());
       System.out.println("Name: " + newNickchecking.getOwner().getName());
       System.out.println("Credit: " + newNickchecking.getCredit().toString());
       System.out.println("Debits: " + newNickchecking.getDebit().toString());
       System.out.println("Balance: " + newNickchecking.getBalance());
    }



}
