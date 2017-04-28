package org.sdmlib.openbank.util;

import org.sdmlib.models.pattern.util.PatternObjectCreator;
import de.uniks.networkparser.IdMap;
import org.sdmlib.openbank.FeeValue;

public class FeeValuePOCreator extends PatternObjectCreator
{
   @Override
   public Object getSendableInstance(boolean reference)
   {
      if(reference) {
          return new FeeValuePO(new FeeValue[]{});
      } else {
          return new FeeValuePO();
      }
   }
   
   public static IdMap createIdMap(String sessionID) {
      return org.sdmlib.openbank.util.CreatorCreator.createIdMap(sessionID);
   }
}
