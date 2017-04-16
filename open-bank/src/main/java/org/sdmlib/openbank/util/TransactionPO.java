package org.sdmlib.openbank.util;

import org.sdmlib.models.pattern.PatternObject;
import org.sdmlib.openbank.Transaction;
import org.sdmlib.models.pattern.AttributeConstraint;
import org.sdmlib.models.pattern.Pattern;
import org.sdmlib.openbank.util.AccountPO;
import org.sdmlib.openbank.Account;
import org.sdmlib.openbank.util.TransactionPO;

import java.util.Date;
import org.sdmlib.openbank.TransactionTypeEnum;
import java.math.BigInteger;

public class TransactionPO extends PatternObject<TransactionPO, Transaction>
{

    public TransactionSet allMatches()
   {
      this.setDoAllMatches(true);
      
      TransactionSet matches = new TransactionSet();

      while (this.getPattern().getHasMatch())
      {
         matches.add((Transaction) this.getCurrentMatch());
         
         this.getPattern().findMatch();
      }
      
      return matches;
   }


   public TransactionPO(){
      newInstance(null);
   }

   public TransactionPO(Transaction... hostGraphObject) {
      if(hostGraphObject==null || hostGraphObject.length<1){
         return ;
      }
      newInstance(null, hostGraphObject);
   }

   public TransactionPO(String modifier)
   {
      this.setModifier(modifier);
   }
   public TransactionPO createAmountCondition(double value)
   {
      new AttributeConstraint()
      .withAttrName(Transaction.PROPERTY_AMOUNT)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public TransactionPO createAmountCondition(double lower, double upper)
   {
      new AttributeConstraint()
      .withAttrName(Transaction.PROPERTY_AMOUNT)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public TransactionPO createAmountAssignment(double value)
   {
      new AttributeConstraint()
      .withAttrName(Transaction.PROPERTY_AMOUNT)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(Pattern.CREATE)
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public double getAmount()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Transaction) getCurrentMatch()).getAmount();
      }
      return 0;
   }
   
   public TransactionPO withAmount(double value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((Transaction) getCurrentMatch()).setAmount(value);
      }
      return this;
   }
   
   public TransactionPO createDateCondition(Date value)
   {
      new AttributeConstraint()
      .withAttrName(Transaction.PROPERTY_DATE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public TransactionPO createDateAssignment(Date value)
   {
      new AttributeConstraint()
      .withAttrName(Transaction.PROPERTY_DATE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(Pattern.CREATE)
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public Date getDate()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Transaction) getCurrentMatch()).getDate();
      }
      return null;
   }
   
   public TransactionPO withDate(Date value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((Transaction) getCurrentMatch()).setDate(value);
      }
      return this;
   }
   
   public TransactionPO createTimeCondition(Date value)
   {
      new AttributeConstraint()
      .withAttrName(Transaction.PROPERTY_TIME)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public TransactionPO createTimeAssignment(Date value)
   {
      new AttributeConstraint()
      .withAttrName(Transaction.PROPERTY_TIME)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(Pattern.CREATE)
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public Date getTime()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Transaction) getCurrentMatch()).getTime();
      }
      return null;
   }
   
   public TransactionPO withTime(Date value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((Transaction) getCurrentMatch()).setTime(value);
      }
      return this;
   }
   
   public TransactionPO createNoteCondition(String value)
   {
      new AttributeConstraint()
      .withAttrName(Transaction.PROPERTY_NOTE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public TransactionPO createNoteCondition(String lower, String upper)
   {
      new AttributeConstraint()
      .withAttrName(Transaction.PROPERTY_NOTE)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public TransactionPO createNoteAssignment(String value)
   {
      new AttributeConstraint()
      .withAttrName(Transaction.PROPERTY_NOTE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(Pattern.CREATE)
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public String getNote()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Transaction) getCurrentMatch()).getNote();
      }
      return null;
   }
   
   public TransactionPO withNote(String value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((Transaction) getCurrentMatch()).setNote(value);
      }
      return this;
   }
   
   public AccountPO createFromAccountPO()
   {
      AccountPO result = new AccountPO(new Account[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Transaction.PROPERTY_FROMACCOUNT, result);
      
      return result;
   }

   public AccountPO createFromAccountPO(String modifier)
   {
      AccountPO result = new AccountPO(new Account[]{});
      
      result.setModifier(modifier);
      super.hasLink(Transaction.PROPERTY_FROMACCOUNT, result);
      
      return result;
   }

   public TransactionPO createFromAccountLink(AccountPO tgt)
   {
      return hasLinkConstraint(tgt, Transaction.PROPERTY_FROMACCOUNT);
   }

   public TransactionPO createFromAccountLink(AccountPO tgt, String modifier)
   {
      return hasLinkConstraint(tgt, Transaction.PROPERTY_FROMACCOUNT, modifier);
   }

   public Account getFromAccount()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Transaction) this.getCurrentMatch()).getFromAccount();
      }
      return null;
   }

   public AccountPO createToAccountPO()
   {
      AccountPO result = new AccountPO(new Account[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Transaction.PROPERTY_TOACCOUNT, result);
      
      return result;
   }

   public AccountPO createToAccountPO(String modifier)
   {
      AccountPO result = new AccountPO(new Account[]{});
      
      result.setModifier(modifier);
      super.hasLink(Transaction.PROPERTY_TOACCOUNT, result);
      
      return result;
   }

   public TransactionPO createToAccountLink(AccountPO tgt)
   {
      return hasLinkConstraint(tgt, Transaction.PROPERTY_TOACCOUNT);
   }

   public TransactionPO createToAccountLink(AccountPO tgt, String modifier)
   {
      return hasLinkConstraint(tgt, Transaction.PROPERTY_TOACCOUNT, modifier);
   }

   public Account getToAccount()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Transaction) this.getCurrentMatch()).getToAccount();
      }
      return null;
   }

   public TransactionPO createTransTypeCondition(TransactionTypeEnum value)
   {
      new AttributeConstraint()
      .withAttrName(Transaction.PROPERTY_TRANSTYPE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public TransactionPO createTransTypeAssignment(TransactionTypeEnum value)
   {
      new AttributeConstraint()
      .withAttrName(Transaction.PROPERTY_TRANSTYPE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(Pattern.CREATE)
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public TransactionTypeEnum getTransType()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Transaction) getCurrentMatch()).getTransType();
      }
      return null;
   }
   
   public TransactionPO withTransType(TransactionTypeEnum value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((Transaction) getCurrentMatch()).setTransType(value);
      }
      return this;
   }
   
   public TransactionPO createCreationdateCondition(Date value)
   {
      new AttributeConstraint()
      .withAttrName(Transaction.PROPERTY_CREATIONDATE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public TransactionPO createCreationdateAssignment(Date value)
   {
      new AttributeConstraint()
      .withAttrName(Transaction.PROPERTY_CREATIONDATE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(Pattern.CREATE)
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public Date getCreationdate()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Transaction) getCurrentMatch()).getCreationdate();
      }
      return null;
   }
   
   public TransactionPO withCreationdate(Date value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((Transaction) getCurrentMatch()).setCreationdate(value);
      }
      return this;
   }
   
   public TransactionPO createAmountDollarCondition(BigInteger value)
   {
      new AttributeConstraint()
      .withAttrName(Transaction.PROPERTY_AMOUNTDOLLAR)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public TransactionPO createAmountDollarAssignment(BigInteger value)
   {
      new AttributeConstraint()
      .withAttrName(Transaction.PROPERTY_AMOUNTDOLLAR)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(Pattern.CREATE)
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public BigInteger getAmountDollar()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Transaction) getCurrentMatch()).getAmountDollar();
      }
      return null;
   }
   
   public TransactionPO withAmountDollar(BigInteger value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((Transaction) getCurrentMatch()).setAmountDollar(value);
      }
      return this;
   }
   
   public TransactionPO createAmountCentCondition(BigInteger value)
   {
      new AttributeConstraint()
      .withAttrName(Transaction.PROPERTY_AMOUNTCENT)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public TransactionPO createAmountCentAssignment(BigInteger value)
   {
      new AttributeConstraint()
      .withAttrName(Transaction.PROPERTY_AMOUNTCENT)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(Pattern.CREATE)
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public BigInteger getAmountCent()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Transaction) getCurrentMatch()).getAmountCent();
      }
      return null;
   }
   
   public TransactionPO withAmountCent(BigInteger value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((Transaction) getCurrentMatch()).setAmountCent(value);
      }
      return this;
   }
   
   public TransactionPO createPrevPO()
   {
      TransactionPO result = new TransactionPO(new Transaction[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Transaction.PROPERTY_PREV, result);
      
      return result;
   }

   public TransactionPO createPrevPO(String modifier)
   {
      TransactionPO result = new TransactionPO(new Transaction[]{});
      
      result.setModifier(modifier);
      super.hasLink(Transaction.PROPERTY_PREV, result);
      
      return result;
   }

   public TransactionPO createPrevLink(TransactionPO tgt)
   {
      return hasLinkConstraint(tgt, Transaction.PROPERTY_PREV);
   }

   public TransactionPO createPrevLink(TransactionPO tgt, String modifier)
   {
      return hasLinkConstraint(tgt, Transaction.PROPERTY_PREV, modifier);
   }

   public Transaction getPrev()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Transaction) this.getCurrentMatch()).getPrev();
      }
      return null;
   }

   public TransactionPO createNextPO()
   {
      TransactionPO result = new TransactionPO(new Transaction[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Transaction.PROPERTY_NEXT, result);
      
      return result;
   }

   public TransactionPO createNextPO(String modifier)
   {
      TransactionPO result = new TransactionPO(new Transaction[]{});
      
      result.setModifier(modifier);
      super.hasLink(Transaction.PROPERTY_NEXT, result);
      
      return result;
   }

   public TransactionPO createNextLink(TransactionPO tgt)
   {
      return hasLinkConstraint(tgt, Transaction.PROPERTY_NEXT);
   }

   public TransactionPO createNextLink(TransactionPO tgt, String modifier)
   {
      return hasLinkConstraint(tgt, Transaction.PROPERTY_NEXT, modifier);
   }

   public Transaction getNext()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Transaction) this.getCurrentMatch()).getNext();
      }
      return null;
   }

}
