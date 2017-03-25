import de.uniks.networkparser.graph.Association;
import de.uniks.networkparser.graph.AssociationTypes;
import de.uniks.networkparser.graph.Cardinality;
import de.uniks.networkparser.graph.Clazz;
import de.uniks.networkparser.graph.DataType;
import org.sdmlib.models.classes.ClassModel;
import org.sdmlib.storyboards.Storyboard;

/**
 * Created by FA on 3/23/2017.
 */
public class Model {

      /**
     * @see <a href='../../../doc/Model.html'>Model.html</a>
 */
   public static void main(String[] args) {
        //create class model
        ClassModel model = new ClassModel("org.sdmlib.openbank");


        // create class user
        Clazz user = model.createClazz("User");

        // set attributes
        user.withAttribute("name", DataType.STRING);
        user.withAttribute("UserID",DataType.STRING);
        //user.withAttribute("DOB",DataType.STRING);

        // create class user
       // Clazz transaction = model.createClazz("Transaction");

        Storyboard storyboard = new Storyboard();
        storyboard.add("This shows the class diagram.");
        storyboard.addClassDiagram(model);

        // add it to the storyboard
        //storyboard.addObjectDiagram(user);

        // show it in html
        storyboard.dumpHTML();

        model.generate();
    }
}

