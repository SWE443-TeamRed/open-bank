
import de.uniks.networkparser.graph.*;
import org.sdmlib.models.classes.ClassModel;
import org.sdmlib.openbank.User;
import org.sdmlib.storyboards.Storyboard;

import java.util.Date;

import static jdk.nashorn.internal.runtime.regexp.joni.Syntax.Java;

/**
 * Created by FA on 3/23/2017.
 */
public class Model {
   public static void main(String[] args) {
       //create class model
       ClassModel model = new ClassModel("org.sdmlib.openbank");


/////////User///////////////////////////////////////////////////////////////////////////////////////////////////////////

       // create class user
       Clazz user = model.createClazz("User");

       // set attributes
       user.withAttribute("name", DataType.STRING);
       user.withAttribute("userID",DataType.STRING);
       user.withAttribute("isAdmin", DataType.BOOLEAN);

        //User Methods


       //User can open an account (for others if they are an admin)
       user.withMethod("openAccount", DataType.BOOLEAN, new Parameter(DataType.create(user)));


/////////Transaction////////////////////////////////////////////////////////////////////////////////////////////////////
       // create class Transaction
       Clazz transaction = model.createClazz("Transaction");

       //set attributes
       transaction.withAttribute("amount", DataType.DOUBLE);
       transaction.withAttribute("date",DataType.create(Date.class));
       transaction.withAttribute("time", DataType.create(Date.class)); //DataType.STRING);
       transaction.withAttribute("note",DataType.STRING);

       //Transaction Methods

/////////Account////////////////////////////////////////////////////////////////////////////////////////////////////////
       /*
        Account class:
        username: Account login ID,
        password: Account login password,
        name: Name on the account,
        email: Email on the account,
        phone: Contact number of the account
        balance: Balance on the account
        */

       Clazz account = model.createClazz("Account");
       account.withAttribute("username", DataType.STRING);
       account.withAttribute("password", DataType.STRING);
       account.withAttribute("name", DataType.STRING);
       account.withAttribute("email", DataType.STRING);
       account.withAttribute("phone", DataType.INT);
       account.withAttribute("balance", DataType.DOUBLE);
       account.withAttribute("isLoggedIn", DataType.BOOLEAN);
       account.withAttribute("accountnum",DataType.INT);
       account.withAttribute("creationdate", DataType.STRING);
       account.withAttribute("IsConnected", DataType.BOOLEAN);//Connected to another user.

       //Account Methods

       //void Account(double initialAmount), constructor
       account.withMethod("Account", DataType.VOID, new Parameter(DataType.DOUBLE).with("initialAmount"));
       //boolean transferFounds(double amount, Account destinationAccount)
       account.withMethod("transferToUser", DataType.BOOLEAN, new Parameter(DataType.DOUBLE).with("amount"),
                                                              new Parameter(account).with("destinationAccount"));
       //boolean myBankTransaction(double amount, Account destinationAccount)
       //transaction from my bank accounts
       account.withMethod("myBankTransaction", DataType.BOOLEAN, new Parameter(DataType.DOUBLE).with("amount"),
                                                                 new Parameter(account).with("destinationAccount"));

       //boolean receiveFound(double amount, Account sourceAccount)
       //Receive found from another user
       account.withMethod("receiveFound", DataType.BOOLEAN, new Parameter(DataType.DOUBLE).with("amount"),
                                                            new Parameter(account).with("sourceAccount"));
       //boolean sendTransactionInfo(double amount, Transaction transaction)
       //Send information from transaction to Transaction class
       account.withMethod("sendTransactionInfo", DataType.BOOLEAN, new Parameter(transaction).with("transaction"),
                                                                   new Parameter(DataType.DOUBLE).with("amount"),
                                                                   new Parameter(DataType.STRING).with("date"),
                                                                   new Parameter(DataType.STRING).with("time"),
                                                                   new Parameter(DataType.STRING).with("note"));
       //User logs into their account
       account.withMethod("login", DataType.BOOLEAN, new Parameter(DataType.STRING).with("username"),
                                                     new Parameter(DataType.STRING).with("password"));
       //void withdraw(double amount)
       //Withdraw funds from account
       account.withMethod("withdraw", DataType.VOID, new Parameter(DataType.DOUBLE).with("amount"));

       //void deposit(double amount, Account account)
       //Deposit funds with account
       account.withMethod("deposit", DataType.VOID, new Parameter(account).with("ToAccount"),
                                                    new Parameter(DataType.DOUBLE).with("amount"));


/////////Bidirectionals/////////////////////////////////////////////////////////////////////////////////////////////////

       // the account in user
       user.withBidirectional(account, "account", Cardinality.MANY, "owner", Cardinality.ONE);
       //transactions toAccount
       account.withBidirectional(transaction, "credit",Cardinality.MANY,"fromAccount",Cardinality.ONE);
       //transactions fromAccount
       account.withBidirectional(transaction, "debit",Cardinality.MANY,"toAccount",Cardinality.ONE);

/////////Storyboard/////////////////////////////////////////////////////////////////////////////////////////////////////

       Storyboard storyboard = new Storyboard();
       storyboard.add("This shows the class diagram.");
       storyboard.addClassDiagram(model);


       //user objects
       User bob = new User().withName("Bob").withUserID("1");
       User sam = new User().withName("Sam").withUserID("2");

       // add users to the object diagram
       storyboard.addObjectDiagram(bob,sam);

       // show it in html
       storyboard.dumpHTML();

       model.generate();
    }
}


