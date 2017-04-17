package com.app.swe443.openbankapp.Support;
import org.sdmlib.models.pattern.util.PatternObjectCreator;
import de.uniks.networkparser.IdMap;

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
