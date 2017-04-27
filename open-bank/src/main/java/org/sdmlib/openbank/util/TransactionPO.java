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
import org.sdmlib.openbank.util.BankPO;
import org.sdmlib.openbank.Bank;
import org.sdmlib.openbank.util.AccountSet;
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
   
   public BigInteger getAmount()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Transaction) getCurrentMatch()).getAmount();
      }
      return BigInteger.valueOf(0);
   }
   
   public TransactionPO withAmount(BigInteger value)
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
   
   public BankPO createBankPO()
   {
      BankPO result = new BankPO(new Bank[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Transaction.PROPERTY_BANK, result);
      
      return result;
   }

   public BankPO createBankPO(String modifier)
   {
      BankPO result = new BankPO(new Bank[]{});
      
      result.setModifier(modifier);
      super.hasLink(Transaction.PROPERTY_BANK, result);
      
      return result;
   }

   public TransactionPO createBankLink(BankPO tgt)
   {
      return hasLinkConstraint(tgt, Transaction.PROPERTY_BANK);
   }

   public TransactionPO createBankLink(BankPO tgt, String modifier)
   {
      return hasLinkConstraint(tgt, Transaction.PROPERTY_BANK, modifier);
   }

   public Bank getBank()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Transaction) this.getCurrentMatch()).getBank();
      }
      return null;
   }

   public AccountPO createAccountsPO()
   {
      AccountPO result = new AccountPO(new Account[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Transaction.PROPERTY_ACCOUNTS, result);
      
      return result;
   }

   public AccountPO createAccountsPO(String modifier)
   {
      AccountPO result = new AccountPO(new Account[]{});
      
      result.setModifier(modifier);
      super.hasLink(Transaction.PROPERTY_ACCOUNTS, result);
      
      return result;
   }

   public TransactionPO createAccountsLink(AccountPO tgt)
   {
      return hasLinkConstraint(tgt, Transaction.PROPERTY_ACCOUNTS);
   }

   public TransactionPO createAccountsLink(AccountPO tgt, String modifier)
   {
      return hasLinkConstraint(tgt, Transaction.PROPERTY_ACCOUNTS, modifier);
   }

   public AccountSet getAccounts()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Transaction) this.getCurrentMatch()).getAccounts();
      }
      return null;
   }

   public TransactionPO createAmountCondition(BigInteger value)
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
   
   public TransactionPO createAmountAssignment(BigInteger value)
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

   public TransactionPO createPreviousPO()
   {
      TransactionPO result = new TransactionPO(new Transaction[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Transaction.PROPERTY_PREVIOUS, result);
      
      return result;
   }

   public TransactionPO createPreviousPO(String modifier)
   {
      TransactionPO result = new TransactionPO(new Transaction[]{});
      
      result.setModifier(modifier);
      super.hasLink(Transaction.PROPERTY_PREVIOUS, result);
      
      return result;
   }

   public TransactionPO createPreviousLink(TransactionPO tgt)
   {
      return hasLinkConstraint(tgt, Transaction.PROPERTY_PREVIOUS);
   }

   public TransactionPO createPreviousLink(TransactionPO tgt, String modifier)
   {
      return hasLinkConstraint(tgt, Transaction.PROPERTY_PREVIOUS, modifier);
   }

   public Transaction getPrevious()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Transaction) this.getCurrentMatch()).getPrevious();
      }
      return null;
   }

}
