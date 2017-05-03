/*
   Copyright (c) 2017 FA
   
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
import org.sdmlib.openbank.Bank;
import de.uniks.networkparser.IdMap;
import org.sdmlib.openbank.User;
import org.sdmlib.openbank.Transaction;
import org.sdmlib.openbank.Account;
import org.sdmlib.openbank.FeeValue;

public class BankCreator implements SendableEntityCreator
{
   private final String[] properties = new String[]
   {
      Bank.PROPERTY_FEE,
      Bank.PROPERTY_BANKNAME,
      Bank.PROPERTY_CUSTOMERUSER,
      Bank.PROPERTY_TRANSACTION,
      Bank.PROPERTY_CUSTOMERACCOUNTS,
      Bank.PROPERTY_ADMINUSERS,
      Bank.PROPERTY_ADMINACCOUNTS,
      Bank.PROPERTY_FEEVALUE,
   };
   
   @Override
   public String[] getProperties()
   {
      return properties;
   }
   
   @Override
   public Object getSendableInstance(boolean reference)
   {
      return new Bank();
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

      if (Bank.PROPERTY_FEE.equalsIgnoreCase(attribute))
      {
         return ((Bank) target).getFee();
      }

      if (Bank.PROPERTY_BANKNAME.equalsIgnoreCase(attribute))
      {
         return ((Bank) target).getBankName();
      }

      if (Bank.PROPERTY_CUSTOMERUSER.equalsIgnoreCase(attribute))
      {
         return ((Bank) target).getCustomerUser();
      }

      if (Bank.PROPERTY_TRANSACTION.equalsIgnoreCase(attribute))
      {
         return ((Bank) target).getTransaction();
      }

      if (Bank.PROPERTY_CUSTOMERACCOUNTS.equalsIgnoreCase(attribute))
      {
         return ((Bank) target).getCustomerAccounts();
      }

      if (Bank.PROPERTY_ADMINUSERS.equalsIgnoreCase(attribute))
      {
         return ((Bank) target).getAdminUsers();
      }

      if (Bank.PROPERTY_ADMINACCOUNTS.equalsIgnoreCase(attribute))
      {
         return ((Bank) target).getAdminAccounts();
      }

      if (Bank.PROPERTY_FEEVALUE.equalsIgnoreCase(attribute))
      {
         return ((Bank) target).getFeeValue();
      }
      
      return null;
   }
   
   @Override
   public boolean setValue(Object target, String attrName, Object value, String type)
   {
      if (Bank.PROPERTY_BANKNAME.equalsIgnoreCase(attrName))
      {
         ((Bank) target).setBankName((String) value);
         return true;
      }

      if (Bank.PROPERTY_FEE.equalsIgnoreCase(attrName))
      {
         ((Bank) target).setFee(Double.parseDouble(value.toString()));
         return true;
      }

      if (SendableEntityCreator.REMOVE.equals(type) && value != null)
      {
         attrName = attrName + type;
      }

      if (Bank.PROPERTY_CUSTOMERUSER.equalsIgnoreCase(attrName))
      {
         ((Bank) target).withCustomerUser((User) value);
         return true;
      }
      
      if ((Bank.PROPERTY_CUSTOMERUSER + SendableEntityCreator.REMOVE).equalsIgnoreCase(attrName))
      {
         ((Bank) target).withoutCustomerUser((User) value);
         return true;
      }

      if (Bank.PROPERTY_TRANSACTION.equalsIgnoreCase(attrName))
      {
         ((Bank) target).setTransaction((Transaction) value);
         return true;
      }

      if (Bank.PROPERTY_CUSTOMERACCOUNTS.equalsIgnoreCase(attrName))
      {
         ((Bank) target).withCustomerAccounts((Account) value);
         return true;
      }
      
      if ((Bank.PROPERTY_CUSTOMERACCOUNTS + SendableEntityCreator.REMOVE).equalsIgnoreCase(attrName))
      {
         ((Bank) target).withoutCustomerAccounts((Account) value);
         return true;
      }

      if (Bank.PROPERTY_ADMINUSERS.equalsIgnoreCase(attrName))
      {
         ((Bank) target).withAdminUsers((User) value);
         return true;
      }
      
      if ((Bank.PROPERTY_ADMINUSERS + SendableEntityCreator.REMOVE).equalsIgnoreCase(attrName))
      {
         ((Bank) target).withoutAdminUsers((User) value);
         return true;
      }

      if (Bank.PROPERTY_ADMINACCOUNTS.equalsIgnoreCase(attrName))
      {
         ((Bank) target).withAdminAccounts((Account) value);
         return true;
      }
      
      if ((Bank.PROPERTY_ADMINACCOUNTS + SendableEntityCreator.REMOVE).equalsIgnoreCase(attrName))
      {
         ((Bank) target).withoutAdminAccounts((Account) value);
         return true;
      }

      if (Bank.PROPERTY_FEEVALUE.equalsIgnoreCase(attrName))
      {
         ((Bank) target).withFeeValue((FeeValue) value);
         return true;
      }
      
      if ((Bank.PROPERTY_FEEVALUE + SendableEntityCreator.REMOVE).equalsIgnoreCase(attrName))
      {
         ((Bank) target).withoutFeeValue((FeeValue) value);
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
      ((Bank) entity).removeYou();
   }
}
