/*
   Copyright (c) 2017 hlope
   
   Permission is hereby granted, free of charge, to any person obtaining a copy of this software 
   and associated documentation files (the "Software"), to deal in the Software without restriction, 
   including without limitation the rights to use, copy, modify, merge, publish, distribute, 
   sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is 
   furnished to do so, subject to the following conditions: 
   
   The above copyright notice and this permission notice shall be included in all copies or 
   substantial portions of the Software. 
   
   The Software shall be used for Good, not Evil. 
   
   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING 
   BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND 
   NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, 
   DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */

import de.uniks.networkparser.IdMap;
import de.uniks.networkparser.interfaces.SendableEntityCreator;

import java.math.BigDecimal;
import java.util.Date;

public class AccountCreator implements SendableEntityCreator
{
   private final String[] properties = new String[]
   {
      Account.PROPERTY_BALANCE,
      Account.PROPERTY_ACCOUNTNUM,
      Account.PROPERTY_CREATIONDATE,
      Account.PROPERTY_ISCONNECTED,
      Account.PROPERTY_OWNER,
      Account.PROPERTY_TYPE,
      Account.PROPERTY_BANK,
      Account.PROPERTY_EMPLOYINGBANK,
      Account.PROPERTY_TOTRANSACTION,
      Account.PROPERTY_FROMTRANSACTION,
   };
   
   @Override
   public String[] getProperties()
   {
      return properties;
   }
   
   @Override
   public Object getSendableInstance(boolean reference)
   {
      return new Account();
   }
   
   @Override
   public Object getValue(Object target, String attrName)
   {
      int pos = attrName.indexOf('.');
      String attribute = attrName;
      
      if (pos > 0)
      {
         attribute = attrName.substring(0, pos);
      }

      if (Account.PROPERTY_BALANCE.equalsIgnoreCase(attribute))
      {
         return ((Account) target).getBalance();
      }

      if (Account.PROPERTY_ACCOUNTNUM.equalsIgnoreCase(attribute))
      {
         return ((Account) target).getAccountnum();
      }

      if (Account.PROPERTY_CREATIONDATE.equalsIgnoreCase(attribute))
      {
         return ((Account) target).getCreationdate();
      }

      if (Account.PROPERTY_ISCONNECTED.equalsIgnoreCase(attribute))
      {
         return ((Account) target).isIsConnected();
      }

      if (Account.PROPERTY_OWNER.equalsIgnoreCase(attribute))
      {
         return ((Account) target).getOwner();
      }



      if (Account.PROPERTY_TYPE.equalsIgnoreCase(attribute))
      {
         return ((Account) target).getType();
      }

      if (Account.PROPERTY_BANK.equalsIgnoreCase(attribute))
      {
         return ((Account) target).getBank();
      }

      if (Account.PROPERTY_EMPLOYINGBANK.equalsIgnoreCase(attribute))
      {
         return ((Account) target).getEmployingBank();
      }


      if (Account.PROPERTY_TOTRANSACTION.equalsIgnoreCase(attribute))
      {
         return ((Account) target).getToTransaction();
      }

      if (Account.PROPERTY_FROMTRANSACTION.equalsIgnoreCase(attribute))
      {
         return ((Account) target).getFromTransaction();
      }
      
      return null;
   }
   
   @Override
   public boolean setValue(Object target, String attrName, Object value, String type)
   {
      if (Account.PROPERTY_TYPE.equalsIgnoreCase(attrName))
      {
         ((Account) target).setType(AccountTypeEnum.valueOf((String) value));
         return true;
      }

      if (Account.PROPERTY_ISCONNECTED.equalsIgnoreCase(attrName))
      {
         ((Account) target).setIsConnected((Boolean) value);
         return true;
      }

      if (Account.PROPERTY_CREATIONDATE.equalsIgnoreCase(attrName))
      {
         ((Account) target).setCreationdate((Date) value);
         return true;
      }

      if (Account.PROPERTY_ACCOUNTNUM.equalsIgnoreCase(attrName))
      {
         ((Account) target).setAccountnum(Integer.parseInt(value.toString()));
         return true;
      }

      if (Account.PROPERTY_BALANCE.equalsIgnoreCase(attrName))
      {
         //((Account) target).setBalance(Double.parseDouble(value.toString()));
         ((Account) target).setBalance(((BigDecimal) value).toBigInteger());
         return true;
      }

      if (SendableEntityCreator.REMOVE.equals(type) && value != null)
      {
         attrName = attrName + type;
      }

      if (Account.PROPERTY_OWNER.equalsIgnoreCase(attrName))
      {
         ((Account) target).setOwner((User) value);
         return true;
      }

      if (Account.PROPERTY_BANK.equalsIgnoreCase(attrName))
      {
         ((Account) target).setBank((Bank) value);
         return true;
      }

      if (Account.PROPERTY_EMPLOYINGBANK.equalsIgnoreCase(attrName))
      {
         ((Account) target).setEmployingBank((Bank) value);
         return true;
      }
      if (Account.PROPERTY_TOTRANSACTION.equalsIgnoreCase(attrName))
      {
         ((Account) target).withToTransaction((Transaction) value);
         return true;
      }
      
      if ((Account.PROPERTY_TOTRANSACTION + SendableEntityCreator.REMOVE).equalsIgnoreCase(attrName))
      {
         ((Account) target).withoutToTransaction((Transaction) value);
         return true;
      }

      if (Account.PROPERTY_FROMTRANSACTION.equalsIgnoreCase(attrName))
      {
         ((Account) target).withFromTransaction((Transaction) value);
         return true;
      }
      
      if ((Account.PROPERTY_FROMTRANSACTION + SendableEntityCreator.REMOVE).equalsIgnoreCase(attrName))
      {
         ((Account) target).withoutFromTransaction((Transaction) value);
         return true;
      }
      
      return false;
   }
   public static IdMap createIdMap(String sessionID)
   {
      return CreatorCreator.createIdMap(sessionID);
   }
   
   //==========================================================================
      public void removeObject(Object entity)
   {
      ((Account) entity).removeYou();
   }
}
