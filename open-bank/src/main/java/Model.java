import de.uniks.networkparser.graph.*;
import org.sdmlib.models.classes.ClassModel;
import org.sdmlib.openbank.Account;
import org.sdmlib.openbank.Transaction;
import org.sdmlib.openbank.TransactionTypeEnum;
import org.sdmlib.openbank.User;
import org.sdmlib.openbank.util.TransactionSet;
import org.sdmlib.storyboards.Storyboard;

import java.math.BigInteger;
import java.util.Date;
import java.util.Set;

/**
 * Created by FA on 3/23/2017.
 */
public class Model {

    /**
     * @see <a href='../../../doc/Model.html'>Model.html</a>
     * @see <a href='../../../../doc/Model.html'>Model.html</a>
     */
    public static void main(String[] args) {
        ///////////////////////////////Classes//////////////////////////////////////////////////////////
        ClassModel model = new ClassModel("org.sdmlib.openbank");
        Clazz enumeration = model.createClazz("AccountTypeEnum")
                .enableEnumeration()
                .with(
                        new Literal("SAVINGS"),
                        new Literal("CHECKING"));

        Clazz transTypeEnum = model.createClazz("TransactionTypeEnum")
                .enableEnumeration()
                .with(
                        new Literal("DEPOSIT"),
                        new Literal("WITHDRAW"),
                        new Literal("TRANSFER"),
                        new Literal("SEED"),
                        new Literal("CLOSE"),
                        new Literal("FEE"));

        Clazz user = model.createClazz("User")
                .withAttribute("name", DataType.STRING)
                .withAttribute("userID",DataType.STRING)
                .withAttribute("isAdmin", DataType.BOOLEAN)
                .withAttribute("password", DataType.STRING)
                .withAttribute("email", DataType.STRING)
                .withAttribute("LoggedIn", DataType.BOOLEAN)
                .withAttribute("phone", DataType.STRING) // FA 4-12-2017 Changed to String from int, adjustments made to the user related classes
                .withAttribute("username", DataType.STRING); // FA 4-12-2017 new field

        Clazz transaction = model.createClazz("Transaction")
                .withAttribute("amount", DataType.create(BigInteger.class)) // type changed from double to Biginteger
                .withAttribute("creationdate",DataType.create(Date.class))
                .withAttribute("note",DataType.STRING)
                .withAttribute("transType", DataType.create(transTypeEnum));

        Clazz feeValue = model.createClazz("FeeValue")
                .withAttribute("transType", DataType.create(transTypeEnum))
                .withAttribute("percent", DataType.create(BigInteger.class));

        Clazz account = model.createClazz("Account")
                .withAttribute("balance", DataType.create(BigInteger.class)) // type changed from double to Biginteger
                .withAttribute("accountnum",DataType.INT)
                .withAttribute("creationdate", DataType.create(Date.class))
                .withAttribute("IsConnected", DataType.BOOLEAN)
                .withAttribute("type", DataType.create(enumeration));

        Clazz bank = model.createClazz("Bank")
                .withAttribute("fee", DataType.DOUBLE)
                .withAttribute("bankName", DataType.STRING);

        /////////////////////////////////////////Methods//////////////////////////////////////////////////////////////////////
        enumeration.withMethod("toString", DataType.STRING);

        transTypeEnum.withMethod("toString", DataType.STRING);

        //User can open an account for others if they are an admin
        user.withMethod("openAccount", DataType.BOOLEAN,
                new Parameter(user));
        //User logs into their account
        user.withMethod("login", DataType.BOOLEAN,
                new Parameter(DataType.STRING).with("username"),
                new Parameter(DataType.STRING).with("password"));
        //User logs into their account
        user.withMethod("logout", DataType.BOOLEAN);

        //Transaction takes place between this and a user
        account.withMethod("transferToAccount", DataType.BOOLEAN,
                new Parameter(DataType.create(BigInteger.class)).with("amount"),
                new Parameter(account).with("receiver"),
                new Parameter(DataType.STRING).with("note"));
        //this is given money either by deposit or someone trasnfered to them
        account.withMethod("receiveFunds", DataType.BOOLEAN,
                new Parameter(DataType.create(BigInteger.class)).with("amount"),
                new Parameter(DataType.STRING).with("note"));
        //Send information from transaction to Transaction class
        account.withMethod("recordTransaction", DataType.create(Transaction.class),
                new Parameter(account).with("sender"),
                new Parameter(account).with("receiver"),
                new Parameter(transTypeEnum).with("type"),
                new Parameter(DataType.create(BigInteger.class)).with("amount"),
                new Parameter(DataType.STRING).with("note"));
        //Withdraw funds from account
        account.withMethod("withdraw", DataType.BOOLEAN,
                new Parameter(DataType.create(BigInteger.class)).with("amount"));
        //Deposit funds with account
        account.withMethod("deposit", DataType.BOOLEAN,
                new Parameter(DataType.create(BigInteger.class)).with("amount"));

        // Check if login information is valid
        bank.withMethod("validateLogin", DataType.BOOLEAN,
                new Parameter(DataType.INT).with("accountID"),
                new Parameter(DataType.STRING).with("username"),
                new Parameter(DataType.STRING).with("password"));
        // Search for Account based on accountNum
        bank.withMethod("findAccountByID", DataType.create(Account.class),
                new Parameter(DataType.INT).with("accountID"));
        // Search for User based on userID
        bank.withMethod("findUserByID", DataType.create(User.class),
                new Parameter(DataType.STRING).with("userID"));
        // Confirm Transaction
        bank.withMethod("confirmTransaction", DataType.BOOLEAN,
                new Parameter(DataType.INT).with("toAcctID"),
                new Parameter(DataType.INT).with("fromAcctID"),
                new Parameter(DataType.create(BigInteger.class)).with("dollarValue"),
                new Parameter(DataType.create(BigInteger.class)).with("decimalValue"));

        // Login method, return succesfull if username and password matches
        bank.withMethod("Login", DataType.STRING,
                new Parameter(DataType.STRING).with("username"),
                new Parameter(DataType.STRING).with("password"));

        // withDrawFunds method, withdraws money from given account
        bank.withMethod("withDrawFunds", DataType.create(BigInteger.class),
                new Parameter(DataType.INT).with("accountNum"),
                new Parameter(DataType.create(BigInteger.class)).with("amount"),
                new Parameter(DataType.create(StringBuilder.class)).with("msg"));

        // depositFunds method, deposits money into given account
        bank.withMethod("depositFunds", DataType.create(BigInteger.class),
                new Parameter(DataType.INT).with("accountNum"),
                new Parameter(DataType.create(BigInteger.class)).with("amount"),
                new Parameter(DataType.create(StringBuilder.class)).with("msg"));


        // updateUserInfo method, updates the given field with given value
        bank.withMethod("updateUserInfo", DataType.STRING,
                new Parameter(DataType.STRING).with("userID"),
                new Parameter(DataType.STRING).with("fieldName"),
                new Parameter(DataType.STRING).with("fieldValue"));

        // get 10 digit random ID
        bank.withMethod("getNextID", DataType.INT);

        // getTransactions method, returns all the transactions for given account, date and amoount
        bank.withMethod("getTransactions", DataType.create(Set.class),
                new Parameter(DataType.INT).with("accountNumber"),
                new Parameter(DataType.create(BigInteger.class)).with("amount"),
                new Parameter(DataType.create(Date.class)).with("date"));

        /////////////////////////////////////////Relations//////////////////////////////////////////////////////////////////////
        bank.withBidirectional(account, "customerAccounts", Cardinality.MANY, "bank", Cardinality.ONE);
        bank.withBidirectional(user, "customerUser", Cardinality.MANY, "bank", Cardinality.ONE);
        bank.withBidirectional(transaction, "transaction", Cardinality.ONE, "bank", Cardinality.ONE);
        bank.withBidirectional(account, "adminAccounts", Cardinality.MANY, "employingBank", Cardinality.ONE);
        bank.withBidirectional(user, "adminUsers", Cardinality.MANY, "employingBank", Cardinality.ONE);
        bank.withBidirectional(feeValue, "feeValue", Cardinality.MANY, "bank", Cardinality.ONE);
        user.withBidirectional(account, "account", Cardinality.MANY, "owner", Cardinality.ONE);
        account.withBidirectional(transaction, "ToTransaction",Cardinality.MANY,"ToAccount",Cardinality.ONE);
        account.withBidirectional(transaction, "FromTransaction", Cardinality.MANY, "FromAccount",Cardinality.ONE);
        transaction.withBidirectional(transaction,"previous", Cardinality.ONE,"next",Cardinality.ONE);
        transaction.withBidirectional(transaction, "fee", Cardinality.ONE, "owner", Cardinality.ONE);

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


