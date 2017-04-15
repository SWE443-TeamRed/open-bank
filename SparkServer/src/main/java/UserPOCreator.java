package main.java;

import de.uniks.networkparser.IdMap;
import org.sdmlib.models.pattern.util.PatternObjectCreator;

public class UserPOCreator extends PatternObjectCreator
{
   @Override
   public Object getSendableInstance(boolean reference)
   {
      if(reference) {
          return new UserPO(new User[]{});
      } else {
          return new UserPO();
      }
   }
   
   public static IdMap createIdMap(String sessionID) {
      return CreatorCreator.createIdMap(sessionID);
   }
}
