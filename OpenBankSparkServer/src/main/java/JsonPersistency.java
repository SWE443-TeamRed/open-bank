import de.uniks.networkparser.IdMap;
import de.uniks.networkparser.json.JsonArray;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by daniel on 3/29/17.
 */
public class JsonPersistency {



    public void toJson(Account account){
        String jsonText = "";

        if(account == null){
            throw new NullPointerException();
        }
        else {

            IdMap idMap = AccountCreator.createIdMap("demo");

            JsonArray jsonArray = idMap.toJsonArray(account);
            jsonText = jsonArray.toString(3);

            System.out.println(jsonText); //For testing

            // Write Json to textfile
            try {
                FileWriter file = new FileWriter(account.getOwner().getUserID()+".json");
                file.write(jsonText);
                file.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Account fromJson(String userId){

        BufferedReader br = null;
        FileReader fr = null;
        String jsonString = "";

        try {
            String sCurrentLine;

            br = new BufferedReader(new FileReader(userId+".json"));

            while ((sCurrentLine = br.readLine()) != null) {
                jsonString += sCurrentLine + "\n";
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
            } catch (IOException ex) {

                ex.printStackTrace();
            }
        }

        // read jsonText from file
        Account account = null;

        IdMap readerMap = AccountCreator.createIdMap("demo");

        Object rootObject = readerMap.decode(jsonString);

        account = (Account) rootObject;

        return account;
    }



}
