package net.Red.atm.util;

import org.sdmlib.models.pattern.util.PatternObjectCreator;
import de.uniks.networkparser.IdMap;
import net.Red.atm.User;

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
      return net.Red.atm.util.CreatorCreator.createIdMap(sessionID);
   }
}
