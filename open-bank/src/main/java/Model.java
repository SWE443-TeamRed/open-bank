import de.uniks.networkparser.graph.*;
import org.sdmlib.models.classes.ClassModel;
import org.sdmlib.openbank.Account;
import org.sdmlib.openbank.Transaction;
import org.sdmlib.openbank.User;
import org.sdmlib.storyboards.Storyboard;

import java.math.BigInteger;
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
        Clazz enumeration = model.createClazz("AccountTypeEnum").enableEnumeration();

        enumeration.with(new Literal("SAVINGS"),
                new Literal("CHECKING"));
        enumeration.withMethod("toString", DataType.STRING);

        Clazz transTypeEnum = model.createClazz("TransactionTypeEnum").enableEnumeration();

        transTypeEnum.with(
                new Literal("DEPOSIT"),
                new Literal("WITHDRAW"),
                new Literal("TRANSFER"),
                new Literal("SEED"),
                new Literal("CLOSE"),
                new Literal("FEE"));

        transTypeEnum.withMethod("toString", DataType.STRING);

/////////User///////////////////////////////////////////////////////////////////////////////////////////////////////////

        // create class user
        Clazz user = model.createClazz("User");

        // set attributes
        user.withAttribute("name", DataType.STRING);
        user.withAttribute("userID",DataType.STRING);
        user.withAttribute("isAdmin", DataType.BOOLEAN);
        user.withAttribute("password", DataType.STRING);
        user.withAttribute("email", DataType.STRING);
        user.withAttribute("LoggedIn", DataType.BOOLEAN);
        user.withAttribute("phone", DataType.STRING); // FA 4-12-2017 Changed to String from int, adjustments made to the user related classes
        user.withAttribute("username", DataType.STRING); // FA 4-12-2017 new field

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
        //transaction.withAttribute("amount", DataType.DOUBLE);
        transaction.withAttribute("amount", DataType.create(BigInteger.class));
        transaction.withAttribute("creationdate",DataType.create(Date.class));
        transaction.withAttribute("note",DataType.STRING);
        transaction.withAttribute("transType", DataType.create(transTypeEnum));

        //Transaction Methods

        Clazz feeValue = model.createClazz("FeeValue");
        feeValue.withAttribute("transType", DataType.create(transTypeEnum));
        feeValue.withAttribute("percent", DataType.DOUBLE);

        transaction.withBidirectional(transaction, "fee", Cardinality.ONE, "owner", Cardinality.ONE);

    /////////Account////////////////////////////////////////////////////////////////////////////////////////////////////////
       /*
        Account class:
        accountnum: account number ID
        balance: Balance on the account
        creationdate: when the account was created
        isConnected: if the account is connected to another user
        */

        Clazz account = model.createClazz("Account");
        account.withAttribute("balance", DataType.create(BigInteger.class));
        account.withAttribute("accountnum",DataType.INT);
        account.withAttribute("creationdate", DataType.create(Date.class));
        account.withAttribute("IsConnected", DataType.BOOLEAN);
        account.withAttribute("type", DataType.create(enumeration));

        //Account Methods

        //void Account(double initialAmount), constructor
        account.withMethod("Account", DataType.VOID, new Parameter(DataType.DOUBLE).with("initialAmount"));

        //Transaction takes place between this and a user
        account.withMethod("transferToAccount", DataType.BOOLEAN,
                new Parameter(DataType.DOUBLE).with("amount"),
                new Parameter(account).with("destinationAccount"),
                new Parameter(DataType.STRING).with("note"));

        //transaction from my bank accounts
//        account.withMethod("myBankTransaction", DataType.BOOLEAN, new Parameter(DataType.DOUBLE).with("amount"),
//                new Parameter(account).with("destinationAccount"));

        //this is given money either by deposit or someone trasnfered to them
        account.withMethod("receiveFunds", DataType.BOOLEAN,
                new Parameter(account).with("giver"),
                new Parameter(DataType.DOUBLE).with("amount"),
                new Parameter(DataType.STRING).with("note"));

        //Send information from transaction to Transaction class
        account.withMethod("recordTransaction", DataType.create(Transaction.class),
                new Parameter(DataType.BOOLEAN),
                new Parameter(DataType.DOUBLE),
                new Parameter(DataType.STRING));

        //void withdraw(double amount)
        //Withdraw funds from account
        account.withMethod("withdraw", DataType.BOOLEAN,
                new Parameter(DataType.DOUBLE).with("amount"));

        //void deposit(double amount, Account account)
        //Deposit funds with account
        account.withMethod("deposit", DataType.BOOLEAN,
                new Parameter(DataType.DOUBLE).with("amount"));

        // ************* Bank class ************
        Clazz bank = model.createClazz("Bank");
        bank.withAttribute("fee", DataType.DOUBLE);
        bank.withAttribute("bankName", DataType.STRING);


        // ************* Bank Bidirectionals ****************
        bank.withBidirectional(account, "customerAccounts", Cardinality.MANY, "bank", Cardinality.ONE);
        bank.withBidirectional(user, "customerUser", Cardinality.MANY, "bank", Cardinality.ONE);
        bank.withBidirectional(transaction, "transaction", Cardinality.ONE, "bank", Cardinality.ONE);
        bank.withBidirectional(feeValue, "feeValue", Cardinality.MANY, "bank", Cardinality.ONE);

        bank.withBidirectional(account, "adminAccounts", Cardinality.MANY, "employingBank", Cardinality.ONE);
        bank.withBidirectional(user, "adminUsers", Cardinality.MANY, "employingBank", Cardinality.ONE);
        // ********** Bank Methods **********
        // validateLogin method
        bank.withMethod("validateLogin", DataType.BOOLEAN,
                new Parameter(DataType.INT).with("accountID"),
                new Parameter(DataType.STRING).with("username"),
                new Parameter(DataType.STRING).with("password"));

        // findAccountByID method
        bank.withMethod("findAccountByID", DataType.create(Account.class),
                new Parameter(DataType.INT).with("accountID"));


        // findUserByID method
        bank.withMethod("findUserByID", DataType.create(User.class),
                new Parameter(DataType.STRING).with("userID"));


        // confirmTransaction method
        bank.withMethod("confirmTransaction", DataType.BOOLEAN,
                new Parameter(DataType.INT).with("toAcctID"),
                new Parameter(DataType.INT).with("fromAcctID"),
                new Parameter(DataType.create(Integer.class)).with("dollarValue"),
                new Parameter(DataType.create(Integer.class)).with("decimalValue"));

        /////////Bidirectionals/////////////////////////////////////////////////////////////////////////////////////////////////

        // the account in user
        user.withBidirectional(account, "account", Cardinality.MANY, "owner", Cardinality.ONE);
        // Transactions and Account relation
        account.withBidirectional(transaction, "transactions",Cardinality.MANY,"accounts",Cardinality.MANY);

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


