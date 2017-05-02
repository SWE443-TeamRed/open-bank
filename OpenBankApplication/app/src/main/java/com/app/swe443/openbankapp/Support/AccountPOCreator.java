package com.app.swe443.openbankapp.Support;

import org.sdmlib.models.pattern.util.PatternObjectCreator;

import de.uniks.networkparser.IdMap;
import com.app.swe443.openbankapp.Support.Account;

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
      return com.app.swe443.openbankapp.Support.CreatorCreator.createIdMap(sessionID);
   }
}

