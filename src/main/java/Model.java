import de.uniks.networkparser.DateTimeEntity;
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
     /**
    * 
    * @see <a href='../../../doc/Model.html'>Model.html</a>
 */
   public static void main(String[] args) {
       //create class model
       ClassModel model = new ClassModel("org.sdmlib.openbank");
       Clazz enumeration = model.createClazz("TransactionTypeEnum").enableEnumeration();

       enumeration.with(new Literal("Debit"),
               new Literal("Credit"));

       enumeration.withMethod("toString", DataType.STRING);


       // create class user
       Clazz user = model.createClazz("User");

       // set attributes
       user.withAttribute("name", DataType.STRING);
       user.withAttribute("UserID",DataType.STRING);
       //user.withAttribute("DOB",DataType.STRING);

       // create class Account
       Clazz account = model.createClazz("Account");

       //set attributes
       account.withAttribute("balance", DataType.DOUBLE);
       account.withAttribute("accountnum",DataType.INT);
       account.withAttribute("creationdate", DataType.create(Date.class)); //DataType.STRING);

       // create class Transaction
       Clazz transaction = model.createClazz("Transaction");

       //set attributes
       transaction.withAttribute("amount", DataType.DOUBLE);
       transaction.withAttribute("date",DataType.create(Date.class));
       transaction.withAttribute("time", DataType.create(Date.class)); //DataType.STRING);
       transaction.withAttribute("note",DataType.STRING);
       transaction.withAttribute("transType", DataType.create(enumeration));
       //transaction.withAttribute("transType",DataType.);

       // the account in user
       user.withBidirectional(account, "account", Cardinality.MANY, "owner", Cardinality.ONE);

       //transactions toAccount
       account.withBidirectional(transaction, "credit",Cardinality.MANY,"fromAccount",Cardinality.ONE);

       //transactions fromAccount
       account.withBidirectional(transaction, "debit",Cardinality.MANY,"toAccount",Cardinality.ONE);


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


