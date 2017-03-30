package org.sdmlib.openbank;

import de.uniks.networkparser.IdMap;
import de.uniks.networkparser.json.JsonArray;
import org.sdmlib.openbank.util.AccountCreator;

/**
 * Created by daniel on 3/29/17.
 */
public class JsonPersistency {

    String jsonText = "";

    public void toJson(){

        User user = new User().withUserID("1").withName("Daniel");
        Account account = new Account().withOwner(user);

        IdMap idMap = AccountCreator.createIdMap("demo");

        JsonArray jsonArray = idMap.toJsonArray(account);
        jsonText = jsonArray.toString(3);

        System.out.println(jsonText); //For testing

        // Write Json to textfile
    }

    public void fromJson(){

        // read jsonText from file
        IdMap readerMap = AccountCreator.createIdMap("demo");

        Object rootObject = readerMap.decode(jsonText);

        Account account = (Account) rootObject;

        System.out.println("Owner: " + account.getOwner().getName()); //For testing
    }
}
