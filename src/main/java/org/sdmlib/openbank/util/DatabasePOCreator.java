package org.sdmlib.openbank.util;

import org.sdmlib.models.pattern.util.PatternObjectCreator;
import de.uniks.networkparser.IdMap;
import org.sdmlib.openbank.Database;

public class DatabasePOCreator extends PatternObjectCreator
{
   @Override
   public Object getSendableInstance(boolean reference)
   {
      if(reference) {
          return new DatabasePO(new Database[]{});
      } else {
          return new DatabasePO();
      }
   }
   
   public static IdMap createIdMap(String sessionID) {
      return org.sdmlib.openbank.util.CreatorCreator.createIdMap(sessionID);
   }
}
