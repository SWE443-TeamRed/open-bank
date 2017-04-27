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
   
package org.sdmlib.openbank;

import de.uniks.networkparser.interfaces.SendableEntity;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import org.sdmlib.openbank.TransactionTypeEnum;
import org.sdmlib.openbank.Bank;
import org.sdmlib.openbank.util.FeeValueSet;

/**
    * 
    * @see <a href='../../../../../../src/main/java/Model.java'>Model.java</a>
 */
   public  class FeeValue implements SendableEntity
{

   
   //==========================================================================
   
   protected PropertyChangeSupport listeners = null;
   
   public boolean firePropertyChange(String propertyName, Object oldValue, Object newValue)
   {
      if (listeners != null) {
   		listeners.firePropertyChange(propertyName, oldValue, newValue);
   		return true;
   	}
   	return false;
   }
   
   public boolean addPropertyChangeListener(PropertyChangeListener listener) 
   {
   	if (listeners == null) {
   		listeners = new PropertyChangeSupport(this);
   	}
   	listeners.addPropertyChangeListener(listener);
   	return true;
   }
   
   public boolean addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
   	if (listeners == null) {
   		listeners = new PropertyChangeSupport(this);
   	}
   	listeners.addPropertyChangeListener(propertyName, listener);
   	return true;
   }
   
   public boolean removePropertyChangeListener(PropertyChangeListener listener) {
   	if (listeners == null) {
   		listeners.removePropertyChangeListener(listener);
   	}
   	listeners.removePropertyChangeListener(listener);
   	return true;
   }

   public boolean removePropertyChangeListener(String propertyName,PropertyChangeListener listener) {
   	if (listeners != null) {
   		listeners.removePropertyChangeListener(propertyName, listener);
   	}
   	return true;
   }

   
   //==========================================================================
   
   
   public void removeYou()
   {
      setBank(null);
      firePropertyChange("REMOVE_YOU", this, null);
   }

   
   //==========================================================================
   
   public static final String PROPERTY_TRANSTYPE = "transType";
   
   private TransactionTypeEnum transType;

   public TransactionTypeEnum getTransType()
   {
      return this.transType;
   }
   
   public void setTransType(TransactionTypeEnum value) {
      if (value == TransactionTypeEnum.FEE)
         throw new IllegalArgumentException("The TransactionType of a FeeValue cannot be a FEE");
      if (this.transType != value) {
         TransactionTypeEnum oldValue = this.transType;
         this.transType = value;
         this.firePropertyChange(PROPERTY_TRANSTYPE, oldValue, value);
      }
   }
   
   public FeeValue withTransType(TransactionTypeEnum value)
   {
      setTransType(value);
      return this;
   } 

   
   //==========================================================================
   
   public static final String PROPERTY_PERCENT = "percent";
   
   private double percent;

   public double getPercent()
   {
      return this.percent;
   }
   
   public void setPercent(double value) {
      if (value < 0)
         throw new IllegalArgumentException("Value cannot be negative");
      if (this.percent != value) {
         double oldValue = this.percent;
         this.percent = value;
         this.firePropertyChange(PROPERTY_PERCENT, oldValue, value);
      }
   }
   
   public FeeValue withPercent(double value)
   {
      setPercent(value);
      return this;
   } 


   @Override
   public String toString()
   {
      StringBuilder result = new StringBuilder();
      
      result.append(" ").append(this.getPercent());
      return result.substring(1);
   }


   
   /********************************************************************
    * <pre>
    *              many                       one
    * FeeValue ----------------------------------- Bank
    *              feeValue                   bank
    * </pre>
    */
   
   public static final String PROPERTY_BANK = "bank";

   private Bank bank = null;

   public Bank getBank()
   {
      return this.bank;
   }

   public boolean setBank(Bank value) {
      boolean changed = false;

       /*
       Three checks are made here to catch duplicate FeeValues:
        1. Is the FeeValueSet empty?
        2. Is the TransType null? (if it is, then it can be added)
        3. Is there an existing FeeValue with the same TransType?
        */
      FeeValueSet pulledFeeValues = value.getFeeValue();
      for (FeeValue i : pulledFeeValues) {
         if (this.getTransType() == null)
            break;
         if (i != null && i.getTransType() == this.getTransType()) {
            return changed;
         }
      }

      // Minor edit to make it such that there is up to FeeValues (1 for each transaction type with the except of FEE)
      if (this.bank != value && value.getFeeValue().size() < 5) {
         Bank oldValue = this.bank;
         if (this.bank != null) {
            this.bank = null;
            oldValue.withoutFeeValue(this);
         }
         this.bank = value;

         if (value != null) {
            value.withFeeValue(this);
         }
         firePropertyChange(PROPERTY_BANK, oldValue, value);
         changed = true;
      }
      return changed;
   }

   public FeeValue withBank(Bank value)
   {
      setBank(value);
      return this;
   } 

   public Bank createBank()
   {
      Bank value = new Bank();
      withBank(value);
      return value;
   } 
}
