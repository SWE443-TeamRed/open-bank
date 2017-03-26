package org.sdmlib.openbank.util;

import org.sdmlib.models.pattern.util.PatternObjectCreator;
import de.uniks.networkparser.IdMap;
import org.sdmlib.openbank.Account;

public class AccountPOCreator extends PatternObjectCreator
{
   @Override
   public Object getSendableInstance(boolean reference)
   {
      if(reference) {
          return new AccountPO(new Account[]{});
      } else {
          return new AccountPO();
      }
   }
   
   public static IdMap createIdMap(String sessionID) {
      return org.sdmlib.openbank.util.CreatorCreator.createIdMap(sessionID);
   }
}
