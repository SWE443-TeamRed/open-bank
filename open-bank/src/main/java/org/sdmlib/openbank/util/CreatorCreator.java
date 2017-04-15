package org.sdmlib.openbank.util;

import de.uniks.networkparser.IdMap;

class CreatorCreator{

   public static IdMap createIdMap(String sessionID)
   {
      IdMap jsonIdMap = new IdMap().withSessionId(sessionID);
      jsonIdMap.with(new UserCreator());
      jsonIdMap.with(new UserPOCreator());
      jsonIdMap.with(new TransactionCreator());
      jsonIdMap.with(new TransactionPOCreator());
      jsonIdMap.with(new AccountCreator());
      jsonIdMap.with(new AccountPOCreator());
      jsonIdMap.with(new BigIntegerCreator());
      jsonIdMap.with(new BigIntegerPOCreator());
      return jsonIdMap;
   }
}
