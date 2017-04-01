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
import org.sdmlib.openbank.Account;
import de.uniks.networkparser.IdMap;
import org.sdmlib.openbank.User;
import org.sdmlib.openbank.Transaction;

import java.util.Date;

public class AccountCreator implements SendableEntityCreator
{

   @Override
   public String[] getProperties() {
      return new String[0];
   }

   @Override
   public Object getValue(Object entity, String attribute) {


     return null;
   }

   @Override
   public boolean setValue(Object entity, String attribute, Object value, String type) {

      return false;
   }

   @Override
   public Object getSendableInstance(boolean prototyp) {
      return null;
   }

   
   //==========================================================================
      public void removeObject(Object entity)
   {
      ((Account) entity).removeYou();
   }
}
