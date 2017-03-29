import de.uniks.networkparser.graph.*;
import org.sdmlib.models.classes.ClassModel;
import org.sdmlib.storyboards.Storyboard;

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


       // create class user
       Clazz user = model.createClazz("User");

       // set attributes
       user.withAttribute("name", DataType.STRING);
       user.withAttribute("userID",DataType.STRING);
       user.withAttribute("isAdmin", DataType.BOOLEAN);


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
       account.withAttribute("isLoggedIn", DataType.DOUBLE);


       //User can open an account (for others if they are an admin)
       user.withMethod("openAccount", DataType.BOOLEAN, new Parameter(DataType.create(user)));

       //User logs into their account
       account.withMethod("login", DataType.BOOLEAN, new Parameter(DataType.STRING).with("username"),new Parameter(DataType.STRING).with("password"));
      //Withdraw funds from account
       account.withMethod("withdraw", DataType.DOUBLE);
       //Deposit funds with account
       account.withMethod("deposit", DataType.DOUBLE);


       // create class Transaction
       Clazz transaction = model.createClazz("Transaction");

       transaction.withAttribute("amount", DataType.DOUBLE);
       transaction.withAttribute("date",DataType.STRING);
       transaction.withAttribute("time", DataType.STRING);
       transaction.withAttribute("note",DataType.STRING);

       //User has MANY accounts, Account has ONE user
       user.withBidirectional(account, "account", Cardinality.MANY, "owner", Cardinality.ONE);

       //transactions toAccount
       account.withBidirectional(transaction, "credit",Cardinality.MANY,"fromAccount",Cardinality.ONE);

       //transactions fromAccount
       account.withBidirectional(transaction, "debit",Cardinality.MANY,"toAccount",Cardinality.ONE);


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


