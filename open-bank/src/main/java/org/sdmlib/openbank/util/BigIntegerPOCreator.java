package org.sdmlib.openbank.util;

import org.sdmlib.models.pattern.util.PatternObjectCreator;
import de.uniks.networkparser.IdMap;
import java.math.BigInteger;

public class BigIntegerPOCreator extends PatternObjectCreator
{
   @Override
   public Object getSendableInstance(boolean reference)
   {
      if(reference) {
          return new BigIntegerPO(new BigInteger[]{});
      } else {
          return new BigIntegerPO();
      }
   }
   
   public static IdMap createIdMap(String sessionID) {
      return org.sdmlib.openbank.util.CreatorCreator.createIdMap(sessionID);
   }
}
