package com.app.swe443.openbankapp;

import org.sdmlib.models.pattern.PatternObject;
import org.sdmlib.models.pattern.AttributeConstraint;
import org.sdmlib.models.pattern.Pattern;

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
   
   public TransactionPO createDateCondition(String value)
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
   
   public TransactionPO createDateCondition(String lower, String upper)
   {
      new AttributeConstraint()
      .withAttrName(Transaction.PROPERTY_DATE)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public TransactionPO createDateAssignment(String value)
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
   
   public String getDate()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Transaction) getCurrentMatch()).getDate();
      }
      return null;
   }
   
   public TransactionPO withDate(String value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((Transaction) getCurrentMatch()).setDate(value);
      }
      return this;
   }
   
   public TransactionPO createTimeCondition(String value)
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
   
   public TransactionPO createTimeCondition(String lower, String upper)
   {
      new AttributeConstraint()
      .withAttrName(Transaction.PROPERTY_TIME)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public TransactionPO createTimeAssignment(String value)
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
   
   public String getTime()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Transaction) getCurrentMatch()).getTime();
      }
      return null;
   }
   
   public TransactionPO withTime(String value)
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

}