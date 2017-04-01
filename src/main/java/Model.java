import de.uniks.networkparser.graph.*;
import org.sdmlib.models.classes.ClassModel;
import org.sdmlib.openbank.User;
import org.sdmlib.storyboards.Storyboard;
import sun.util.calendar.LocalGregorianCalendar;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by FA on 3/23/2017.
 */
public class Model {

    /**
     * @see <a href='../../../doc/Model.html'>Model.html</a>
     * @see <a href='../../../../doc/Model.html'>Model.html</a>
     */
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
        user.withAttribute("password", DataType.STRING);
        user.withAttribute("name", DataType.STRING);
        user.withAttribute("email", DataType.STRING);
        user.withAttribute("LoggedIn", DataType.BOOLEAN);
        user.withAttribute("phone", DataType.INT);

        //User Methods

        //User can open an account for others if they are an admin
        user.withMethod("openAccount", DataType.BOOLEAN, new Parameter(DataType.create(user)));
        //User logs into their account
        user.withMethod("login", DataType.BOOLEAN, new Parameter(DataType.STRING).with("username"),
                new Parameter(DataType.STRING).with("password"));
        //User logs into their account
        user.withMethod("logout", DataType.BOOLEAN);


/////////Transaction////////////////////////////////////////////////////////////////////////////////////////////////////
        // create class Transaction
        Clazz transaction = model.createClazz("Transaction");
        transaction.withAttribute("amount", DataType.DOUBLE);
        transaction.withAttribute("date",DataType.create(Date.class));
        transaction.withAttribute("time", DataType.create(Date.class)); //DataType.STRING);
        transaction.withAttribute("note",DataType.STRING);

        //Transaction Methods

/////////Account////////////////////////////////////////////////////////////////////////////////////////////////////////
       /*
        Account class:
        accountnum: account number ID
        balance: Balance on the account
        creationdate: when the account was created
        isConnected: if the account is connected to another user
        */

        Clazz account = model.createClazz("Account");
        account.withAttribute("name", DataType.STRING);
        account.withAttribute("balance", DataType.DOUBLE);
        account.withAttribute("accountnum",DataType.INT);
        account.withAttribute("creationdate", DataType.create(Date.class));
        account.withAttribute("IsConnected", DataType.BOOLEAN);

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
                new Parameter(DataType.create(Date.class)).with("date"),
                new Parameter(DataType.create(Date.class)).with("time"),
                new Parameter(DataType.STRING).with("note"));

        //void withdraw(double amount)
        //Withdraw funds from account
        account.withMethod("withdraw", DataType.VOID, new Parameter(DataType.DOUBLE).with("amount"));

        //void deposit(double amount, Account account)
        //Deposit funds with account
        account.withMethod("deposit", DataType.VOID,
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
        // add it to the storyboard
        storyboard.addObjectDiagram(user);
        // show it in html
        storyboard.dumpHTML();

        model.generate();


    }
}


