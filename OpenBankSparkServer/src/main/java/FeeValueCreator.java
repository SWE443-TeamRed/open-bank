/*
   Copyright (c) 2017 delta
   
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

import java.math.BigInteger;

public class FeeValueCreator implements SendableEntityCreator
{
   private final String[] properties = new String[]
           {
                   FeeValue.PROPERTY_TRANSTYPE,
                   FeeValue.PROPERTY_PERCENT,
                   FeeValue.PROPERTY_BANK,
           };

   @Override
   public String[] getProperties()
   {
      return properties;
   }

   @Override
   public Object getSendableInstance(boolean reference)
   {
      return new FeeValue();
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

      if (FeeValue.PROPERTY_TRANSTYPE.equalsIgnoreCase(attribute))
      {
         return ((FeeValue) target).getTransType();
      }

      if (FeeValue.PROPERTY_PERCENT.equalsIgnoreCase(attribute))
      {
         return ((FeeValue) target).getPercent();
      }

      if (FeeValue.PROPERTY_BANK.equalsIgnoreCase(attribute))
      {
         return ((FeeValue) target).getBank();
      }

      return null;
   }

   @Override
   public boolean setValue(Object target, String attrName, Object value, String type)
   {
      if (FeeValue.PROPERTY_PERCENT.equalsIgnoreCase(attrName))
      {
         ((FeeValue) target).setPercent(new BigInteger(value.toString()));
         return true;
      }

      if (FeeValue.PROPERTY_TRANSTYPE.equalsIgnoreCase(attrName))
      {
         ((FeeValue) target).setTransType(TransactionTypeEnum.valueOf((String) value));
         return true;
      }

      if (SendableEntityCreator.REMOVE.equals(type) && value != null)
      {
         attrName = attrName + type;
      }

      if (FeeValue.PROPERTY_BANK.equalsIgnoreCase(attrName))
      {
         ((FeeValue) target).setBank((Bank) value);
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
      ((FeeValue) entity).removeYou();
   }
}
