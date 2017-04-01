package org.sdmlib.openbank.util;

import org.sdmlib.models.pattern.util.PatternObjectCreator;
import de.uniks.networkparser.IdMap;
import org.sdmlib.openbank.User;

public class UserPOCreator extends PatternObjectCreator
{

   public static IdMap createIdMap(String sessionID) {
      return org.sdmlib.openbank.util.CreatorCreator.createIdMap(sessionID);
   }
}
