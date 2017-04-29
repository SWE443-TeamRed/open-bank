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

import de.uniks.networkparser.list.SimpleSet;
import java.math.BigInteger;
import de.uniks.networkparser.interfaces.Condition;
import java.util.Collection;

public class BigIntegerSet extends SimpleSet<BigInteger>
{
	protected Class<?> getTypClass() {
		return BigInteger.class;
	}

   public BigIntegerSet()
   {
      // empty
   }

   public BigIntegerSet(BigInteger... objects)
   {
      for (BigInteger obj : objects)
      {
         this.add(obj);
      }
   }

   public BigIntegerSet(Collection<BigInteger> objects)
   {
      this.addAll(objects);
   }

   public static final BigIntegerSet EMPTY_SET = new BigIntegerSet().withFlag(BigIntegerSet.READONLY);


   public BigIntegerPO createBigIntegerPO()
   {
      return new BigIntegerPO(this.toArray(new BigInteger[this.size()]));
   }


   public String getEntryType()
   {
      return "java.math.BigInteger";
   }


   @Override
   public BigIntegerSet getNewList(boolean keyValue)
   {
      return new BigIntegerSet();
   }


   public BigIntegerSet filter(Condition<BigInteger> condition) {
      BigIntegerSet filterList = new BigIntegerSet();
      filterItems(filterList, condition);
      return filterList;
   }

   @SuppressWarnings("unchecked")
   public BigIntegerSet with(Object value)
   {
      if (value == null)
      {
         return this;
      }
      else if (value instanceof java.util.Collection)
      {
         this.addAll((Collection<BigInteger>)value);
      }
      else if (value != null)
      {
         this.add((BigInteger) value);
      }
      
      return this;
   }
   
   public BigIntegerSet without(BigInteger value)
   {
      this.remove(value);
      return this;
   }

}
