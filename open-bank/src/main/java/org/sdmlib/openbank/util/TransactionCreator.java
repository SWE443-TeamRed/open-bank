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
   
package org.sdmlib.openbank.util;

import de.uniks.networkparser.interfaces.SendableEntityCreator;
import org.sdmlib.openbank.Transaction;
import de.uniks.networkparser.IdMap;
import org.sdmlib.openbank.Account;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import org.sdmlib.openbank.TransactionTypeEnum;
import org.sdmlib.openbank.Bank;

public class TransactionCreator implements SendableEntityCreator
{
   private final String[] properties = new String[]
   {
      Transaction.PROPERTY_AMOUNT,
      Transaction.PROPERTY_DATE,
      Transaction.PROPERTY_TIME,
      Transaction.PROPERTY_NOTE,
      Transaction.PROPERTY_TRANSTYPE,
      Transaction.PROPERTY_CREATIONDATE,
      Transaction.PROPERTY_BANK,
      Transaction.PROPERTY_NEXT,
      Transaction.PROPERTY_PREVIOUS,
      Transaction.PROPERTY_TOACCOUNT,
      Transaction.PROPERTY_FROMACCOUNT,
   };
   
   @Override
   public String[] getProperties()
   {
      return properties;
   }
   
   @Override
   public Object getSendableInstance(boolean reference)
   {
      return new Transaction();
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

      if (Transaction.PROPERTY_AMOUNT.equalsIgnoreCase(attribute))
      {
         return ((Transaction) target).getAmount();
      }

      if (Transaction.PROPERTY_DATE.equalsIgnoreCase(attribute))
      {
         return ((Transaction) target).getDate();
      }

      if (Transaction.PROPERTY_TIME.equalsIgnoreCase(attribute))
      {
         return ((Transaction) target).getTime();
      }

      if (Transaction.PROPERTY_NOTE.equalsIgnoreCase(attribute))
      {
         return ((Transaction) target).getNote();
      }


      if (Transaction.PROPERTY_TRANSTYPE.equalsIgnoreCase(attribute))
      {
         return ((Transaction) target).getTransType();
      }

      if (Transaction.PROPERTY_CREATIONDATE.equalsIgnoreCase(attribute))
      {
         return ((Transaction) target).getCreationdate();
      }

      if (Transaction.PROPERTY_BANK.equalsIgnoreCase(attribute))
      {
         return ((Transaction) target).getBank();
      }

      if (Transaction.PROPERTY_NEXT.equalsIgnoreCase(attribute))
      {
         return ((Transaction) target).getNext();
      }

      if (Transaction.PROPERTY_PREVIOUS.equalsIgnoreCase(attribute))
      {
         return ((Transaction) target).getPrevious();
      }

      if (Transaction.PROPERTY_TOACCOUNT.equalsIgnoreCase(attribute))
      {
         return ((Transaction) target).getToAccount();
      }

      if (Transaction.PROPERTY_FROMACCOUNT.equalsIgnoreCase(attribute))
      {
         return ((Transaction) target).getFromAccount();
      }
      
      return null;
   }
   
   @Override
   public boolean setValue(Object target, String attrName, Object value, String type)
   {
      if (Transaction.PROPERTY_CREATIONDATE.equalsIgnoreCase(attrName))
      {
         ((Transaction) target).setCreationdate((Date) value);
         return true;
      }

      if (Transaction.PROPERTY_TRANSTYPE.equalsIgnoreCase(attrName))
      {
         ((Transaction) target).setTransType(TransactionTypeEnum.valueOf((String) value));
         return true;
      }

      if (Transaction.PROPERTY_NOTE.equalsIgnoreCase(attrName))
      {
         ((Transaction) target).setNote((String) value);
         return true;
      }

      if (Transaction.PROPERTY_TIME.equalsIgnoreCase(attrName))
      {
         ((Transaction) target).setTime((Date) value);
         return true;
      }

      if (Transaction.PROPERTY_DATE.equalsIgnoreCase(attrName))
      {
         ((Transaction) target).setDate((Date) value);
         return true;
      }

      if (Transaction.PROPERTY_AMOUNT.equalsIgnoreCase(attrName))
      {
         ((Transaction) target).setAmount(((BigDecimal) value).toBigInteger());
         return true;
      }

      if (SendableEntityCreator.REMOVE.equals(type) && value != null)
      {
         attrName = attrName + type;
      }

      if (Transaction.PROPERTY_BANK.equalsIgnoreCase(attrName))
      {
         ((Transaction) target).setBank((Bank) value);
         return true;
      }

      if (Transaction.PROPERTY_NEXT.equalsIgnoreCase(attrName))
      {
         ((Transaction) target).setNext((Transaction) value);
         return true;
      }

      if (Transaction.PROPERTY_PREVIOUS.equalsIgnoreCase(attrName))
      {
         ((Transaction) target).setPrevious((Transaction) value);
         return true;
      }

      if (Transaction.PROPERTY_TOACCOUNT.equalsIgnoreCase(attrName))
      {
         ((Transaction) target).setToAccount((Account) value);
         return true;
      }

      if (Transaction.PROPERTY_FROMACCOUNT.equalsIgnoreCase(attrName))
      {
         ((Transaction) target).setFromAccount((Account) value);
         return true;
      }
      
      return false;
   }
   public static IdMap createIdMap(String sessionID)
   {
      return org.sdmlib.openbank.util.CreatorCreator.createIdMap(sessionID);
   }
   
   //==========================================================================
      public void removeObject(Object entity)
   {
      ((Transaction) entity).removeYou();
   }
}
