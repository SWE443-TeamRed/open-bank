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
   
package org.sdmlib.openbank.util;

import de.uniks.networkparser.list.SimpleSet;
import org.sdmlib.openbank.FeeValue;
import de.uniks.networkparser.interfaces.Condition;
import java.util.Collection;
import org.sdmlib.openbank.TransactionTypeEnum;
import java.math.BigInteger;
import de.uniks.networkparser.list.ObjectSet;
import org.sdmlib.openbank.util.BankSet;
import org.sdmlib.openbank.Bank;

public class FeeValueSet extends SimpleSet<FeeValue>
{
	protected Class<?> getTypClass() {
		return FeeValue.class;
	}

   public FeeValueSet()
   {
      // empty
   }

   public FeeValueSet(FeeValue... objects)
   {
      for (FeeValue obj : objects)
      {
         this.add(obj);
      }
   }

   public FeeValueSet(Collection<FeeValue> objects)
   {
      this.addAll(objects);
   }

   public static final FeeValueSet EMPTY_SET = new FeeValueSet().withFlag(FeeValueSet.READONLY);


   public FeeValuePO createFeeValuePO()
   {
      return new FeeValuePO(this.toArray(new FeeValue[this.size()]));
   }


   public String getEntryType()
   {
      return "org.sdmlib.openbank.FeeValue";
   }


   @Override
   public FeeValueSet getNewList(boolean keyValue)
   {
      return new FeeValueSet();
   }


   public FeeValueSet filter(Condition<FeeValue> condition) {
      FeeValueSet filterList = new FeeValueSet();
      filterItems(filterList, condition);
      return filterList;
   }

   @SuppressWarnings("unchecked")
   public FeeValueSet with(Object value)
   {
      if (value == null)
      {
         return this;
      }
      else if (value instanceof java.util.Collection)
      {
         this.addAll((Collection<FeeValue>)value);
      }
      else if (value != null)
      {
         this.add((FeeValue) value);
      }
      
      return this;
   }
   
   public FeeValueSet without(FeeValue value)
   {
      this.remove(value);
      return this;
   }


   /**
    * Loop through the current set of FeeValue objects and collect a list of the transType attribute values. 
    * 
    * @return List of org.sdmlib.openbank.TransactionTypeEnum objects reachable via transType attribute
    */
   public TransactionTypeEnumSet getTransType()
   {
      TransactionTypeEnumSet result = new TransactionTypeEnumSet();
      
      for (FeeValue obj : this)
      {
         result.add(obj.getTransType());
      }
      
      return result;
   }


   /**
    * Loop through the current set of FeeValue objects and collect those FeeValue objects where the transType attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of FeeValue objects that match the parameter
    */
   public FeeValueSet filterTransType(TransactionTypeEnum value)
   {
      FeeValueSet result = new FeeValueSet();
      
      for (FeeValue obj : this)
      {
         if (value == obj.getTransType())
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of FeeValue objects and assign value to the transType attribute of each of it. 
    * 
    * @param value New attribute value
    * 
    * @return Current set of FeeValue objects now with new attribute values.
    */
   public FeeValueSet withTransType(TransactionTypeEnum value)
   {
      for (FeeValue obj : this)
      {
         obj.setTransType(value);
      }
      
      return this;
   }


   /**
    * Loop through the current set of FeeValue objects and collect a list of the percent attribute values. 
    * 
    * @return List of java.math.BigInteger objects reachable via percent attribute
    */
   public BigIntegerSet getPercent()
   {
      BigIntegerSet result = new BigIntegerSet();
      
      for (FeeValue obj : this)
      {
         result.add(obj.getPercent());
      }
      
      return result;
   }


   /**
    * Loop through the current set of FeeValue objects and collect those FeeValue objects where the percent attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of FeeValue objects that match the parameter
    */
   public FeeValueSet filterPercent(BigInteger value)
   {
      FeeValueSet result = new FeeValueSet();
      
      for (FeeValue obj : this)
      {
         if (value == obj.getPercent())
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of FeeValue objects and assign value to the percent attribute of each of it. 
    * 
    * @param value New attribute value
    * 
    * @return Current set of FeeValue objects now with new attribute values.
    */
   public FeeValueSet withPercent(BigInteger value)
   {
      for (FeeValue obj : this)
      {
         obj.setPercent(value);
      }
      
      return this;
   }

   /**
    * Loop through the current set of FeeValue objects and collect a set of the Bank objects reached via bank. 
    * 
    * @return Set of Bank objects reachable via bank
    */
   public BankSet getBank()
   {
      BankSet result = new BankSet();
      
      for (FeeValue obj : this)
      {
         result.with(obj.getBank());
      }
      
      return result;
   }

   /**
    * Loop through the current set of FeeValue objects and collect all contained objects with reference bank pointing to the object passed as parameter. 
    * 
    * @param value The object required as bank neighbor of the collected results. 
    * 
    * @return Set of Bank objects referring to value via bank
    */
   public FeeValueSet filterBank(Object value)
   {
      ObjectSet neighbors = new ObjectSet();

      if (value instanceof Collection)
      {
         neighbors.addAll((Collection<?>) value);
      }
      else
      {
         neighbors.add(value);
      }
      
      FeeValueSet answer = new FeeValueSet();
      
      for (FeeValue obj : this)
      {
         if (neighbors.contains(obj.getBank()) || (neighbors.isEmpty() && obj.getBank() == null))
         {
            answer.add(obj);
         }
      }
      
      return answer;
   }

   /**
    * Loop through current set of ModelType objects and attach the FeeValue object passed as parameter to the Bank attribute of each of it. 
    * 
    * @return The original set of ModelType objects now with the new neighbor attached to their Bank attributes.
    */
   public FeeValueSet withBank(Bank value)
   {
      for (FeeValue obj : this)
      {
         obj.withBank(value);
      }
      
      return this;
   }

}
