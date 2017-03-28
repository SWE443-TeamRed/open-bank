package org.sdmlib.openbank.util;

import org.sdmlib.models.pattern.PatternObject;
import org.sdmlib.openbank.Database;
import org.sdmlib.openbank.Account;
import org.sdmlib.openbank.Transaction;

public class DatabasePO extends PatternObject<DatabasePO, Database>
{

    public DatabaseSet allMatches()
   {
      this.setDoAllMatches(true);
      
      DatabaseSet matches = new DatabaseSet();

      while (this.getPattern().getHasMatch())
      {
         matches.add((Database) this.getCurrentMatch());
         
         this.getPattern().findMatch();
      }
      
      return matches;
   }


   public DatabasePO(){
      newInstance(null);
   }

   public DatabasePO(Database... hostGraphObject) {
      if(hostGraphObject==null || hostGraphObject.length<1){
         return ;
      }
      newInstance(null, hostGraphObject);
   }

   public DatabasePO(String modifier)
   {
      this.setModifier(modifier);
   }
   
   //==========================================================================
   
   public boolean addAccounts(Account account)
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Database) getCurrentMatch()).addAccounts(account);
      }
      return false;
   }

   
   //==========================================================================
   
   public org.sdmlib.openbank.Account searchForAccount(String userId)
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Database) getCurrentMatch()).searchForAccount(userId);
      }
      return null;
   }

   
   //==========================================================================
   
   public org.sdmlib.openbank.Transaction searchForTransaction(String userId)
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Database) getCurrentMatch()).searchForTransaction(userId);
      }
      return null;
   }

   
   //==========================================================================
   
   public boolean addTransaction(Transaction transaction)
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Database) getCurrentMatch()).addTransaction(transaction);
      }
      return false;
   }

}
