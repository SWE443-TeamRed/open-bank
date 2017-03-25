package org.sdmlib.openbank.util;

import de.uniks.networkparser.IdMap;

class CreatorCreator{

   public static IdMap createIdMap(String sessionID)
   {
      IdMap jsonIdMap = new IdMap().withSessionId(sessionID);
      jsonIdMap.with(new UserCreator());
      jsonIdMap.with(new UserPOCreator());
      return jsonIdMap;
   }
}
