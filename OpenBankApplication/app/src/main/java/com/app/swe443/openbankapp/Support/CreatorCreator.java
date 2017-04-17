package com.app.swe443.openbankapp.Support;

import de.uniks.networkparser.IdMap;
import com.app.swe443.openbankapp.Support.AccountPOCreator;

class CreatorCreator{

   public static IdMap createIdMap(String sessionID)
   {
      IdMap jsonIdMap = new IdMap().withSessionId(sessionID);
      String str = System.getProperty("java.classpath");
      jsonIdMap.with(new AccountCreator());
      jsonIdMap.with(new AccountPOCreator());
      jsonIdMap.with(new TransactionCreator());
      jsonIdMap.with(new TransactionPOCreator());
      jsonIdMap.with(new UserCreator());
      jsonIdMap.with(new UserPOCreator());

      return jsonIdMap;
   }
}
