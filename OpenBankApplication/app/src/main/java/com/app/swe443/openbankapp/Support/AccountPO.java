package com.app.swe443.openbankapp.Support;

import org.sdmlib.models.pattern.PatternObject;

import org.sdmlib.models.pattern.AttributeConstraint;
import org.sdmlib.models.pattern.Pattern;


import java.util.Date;


public class AccountPO extends PatternObject<AccountPO, Account>
{

    public AccountSet allMatches()
   {
      this.setDoAllMatches(true);
      
      AccountSet matches = new AccountSet();

      while (this.getPattern().getHasMatch())
      {
         matches.add((Account) this.getCurrentMatch());
         
         this.getPattern().findMatch();
      }
      
      return matches;
   }


   public AccountPO(){
      newInstance(null);
   }

   public AccountPO(Account... hostGraphObject) {
      if(hostGraphObject==null || hostGraphObject.length<1){
         return ;
      }
      newInstance(null, hostGraphObject);
   }

   public AccountPO(String modifier)
   {
      this.setModifier(modifier);
   }
   
   //==========================================================================
   
   public void Account(double initialAmount)
   {
      if (this.getPattern().getHasMatch())
      {
          ((Account) getCurrentMatch()).Account(initialAmount);
      }
   }

   
   //==========================================================================

   public boolean transferToUser(double amount, Account destinationAccount, String note)
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Account) getCurrentMatch()).transferToAccount(amount, destinationAccount, note);
      }
      return false;
   }

   
   //==========================================================================
//
//   public boolean myBankTransaction(double amount, Account destinationAccount)
//   {
//      if (this.getPattern().getHasMatch())
//      {
//         return ((Account) getCurrentMatch()).myBankTransaction(amount, destinationAccount);
//      }
//      return false;
//   }
//
//
//   //==========================================================================
//
//   public boolean receiveFound(double amount, Account sourceAccount)
//   {
//      if (this.getPattern().getHasMatch())
//      {
//         return ((Account) getCurrentMatch()).receiveFound(amount, sourceAccount);
//      }
//      return false;
//   }
//
//
//   //==========================================================================
//
//   public boolean sendTransactionInfo(Transaction transaction, double amount, Date p0, Date p1, String note)
//   {
//      if (this.getPattern().getHasMatch())
//      {
//         return ((Account) getCurrentMatch()).sendTransactionInfo(transaction, amount, p0, p1, note);
//      }
//      return false;
//   }

   
   //==========================================================================
   
   public void withdraw(double amount)
   {
      if (this.getPattern().getHasMatch())
      {
          ((Account) getCurrentMatch()).withdraw(amount);
      }
   }

   
   //==========================================================================
   
   public void deposit(double amount)
   {
      if (this.getPattern().getHasMatch())
      {
          ((Account) getCurrentMatch()).deposit(amount);
      }
   }

   public AccountPO createBalanceCondition(double value)
   {
      new AttributeConstraint()
      .withAttrName(Account.PROPERTY_BALANCE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public AccountPO createBalanceCondition(double lower, double upper)
   {
      new AttributeConstraint()
      .withAttrName(Account.PROPERTY_BALANCE)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public AccountPO createBalanceAssignment(double value)
   {
      new AttributeConstraint()
      .withAttrName(Account.PROPERTY_BALANCE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(Pattern.CREATE)
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public double getBalance()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Account) getCurrentMatch()).getBalance();
      }
      return 0;
   }
   
   public AccountPO withBalance(double value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((Account) getCurrentMatch()).setBalance(value);
      }
      return this;
   }
   
   public AccountPO createAccountnumCondition(int value)
   {
      new AttributeConstraint()
      .withAttrName(Account.PROPERTY_ACCOUNTNUM)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public AccountPO createAccountnumCondition(int lower, int upper)
   {
      new AttributeConstraint()
      .withAttrName(Account.PROPERTY_ACCOUNTNUM)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public AccountPO createAccountnumAssignment(int value)
   {
      new AttributeConstraint()
      .withAttrName(Account.PROPERTY_ACCOUNTNUM)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(Pattern.CREATE)
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public int getAccountnum()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Account) getCurrentMatch()).getAccountnum();
      }
      return 0;
   }
   
   public AccountPO withAccountnum(int value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((Account) getCurrentMatch()).setAccountnum(value);
      }
      return this;
   }
   
   public AccountPO createCreationdateCondition(Date value)
   {
      new AttributeConstraint()
      .withAttrName(Account.PROPERTY_CREATIONDATE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public AccountPO createCreationdateAssignment(Date value)
   {
      new AttributeConstraint()
      .withAttrName(Account.PROPERTY_CREATIONDATE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(Pattern.CREATE)
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
//   public Date getCreationdate()
//   {
//      if (this.getPattern().getHasMatch())
//      {
//         return ((Account) getCurrentMatch()).getCreationdate();
//      }
//      return null;
//   }
   
//   public AccountPO withCreationdate(Date value)
//   {
//      if (this.getPattern().getHasMatch())
//      {
//         ((Account) getCurrentMatch()).setCreationdate(value);
//      }
//      return this;
//   }
   
   public AccountPO createIsConnectedCondition(boolean value)
   {
      new AttributeConstraint()
      .withAttrName(Account.PROPERTY_ISCONNECTED)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public AccountPO createIsConnectedAssignment(boolean value)
   {
      new AttributeConstraint()
      .withAttrName(Account.PROPERTY_ISCONNECTED)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(Pattern.CREATE)
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public boolean getIsConnected()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Account) getCurrentMatch()).isIsConnected();
      }
      return false;
   }
   
   public AccountPO withIsConnected(boolean value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((Account) getCurrentMatch()).setIsConnected(value);
      }
      return this;
   }
   
   public UserPO createOwnerPO()
   {
      UserPO result = new UserPO(new User[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Account.PROPERTY_OWNER, result);
      
      return result;
   }

   public UserPO createOwnerPO(String modifier)
   {
      UserPO result = new UserPO(new User[]{});
      
      result.setModifier(modifier);
      super.hasLink(Account.PROPERTY_OWNER, result);
      
      return result;
   }

   public AccountPO createOwnerLink(UserPO tgt)
   {
      return hasLinkConstraint(tgt, Account.PROPERTY_OWNER);
   }

   public AccountPO createOwnerLink(UserPO tgt, String modifier)
   {
      return hasLinkConstraint(tgt, Account.PROPERTY_OWNER, modifier);
   }

   public User getOwner()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Account) this.getCurrentMatch()).getOwner();
      }
      return null;
   }

   public TransactionPO createCreditPO()
   {
      TransactionPO result = new TransactionPO(new Transaction[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Account.PROPERTY_CREDIT, result);
      
      return result;
   }

   public TransactionPO createCreditPO(String modifier)
   {
      TransactionPO result = new TransactionPO(new Transaction[]{});
      
      result.setModifier(modifier);
      super.hasLink(Account.PROPERTY_CREDIT, result);
      
      return result;
   }

   public AccountPO createCreditLink(TransactionPO tgt)
   {
      return hasLinkConstraint(tgt, Account.PROPERTY_CREDIT);
   }

   public AccountPO createCreditLink(TransactionPO tgt, String modifier)
   {
      return hasLinkConstraint(tgt, Account.PROPERTY_CREDIT, modifier);
   }

   public TransactionSet getCredit()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Account) this.getCurrentMatch()).getCredit();
      }
      return null;
   }

   public TransactionPO createDebitPO()
   {
      TransactionPO result = new TransactionPO(new Transaction[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Account.PROPERTY_DEBIT, result);
      
      return result;
   }

   public TransactionPO createDebitPO(String modifier)
   {
      TransactionPO result = new TransactionPO(new Transaction[]{});
      
      result.setModifier(modifier);
      super.hasLink(Account.PROPERTY_DEBIT, result);
      
      return result;
   }

   public AccountPO createDebitLink(TransactionPO tgt)
   {
      return hasLinkConstraint(tgt, Account.PROPERTY_DEBIT);
   }

   public AccountPO createDebitLink(TransactionPO tgt, String modifier)
   {
      return hasLinkConstraint(tgt, Account.PROPERTY_DEBIT, modifier);
   }

   public TransactionSet getDebit()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Account) this.getCurrentMatch()).getDebit();
      }
      return null;
   }

   
   //==========================================================================
   
//   public boolean myBankTransaction(double amount, Account destinationAccount)
//   {
//      if (this.getPattern().getHasMatch())
//      {
//         return ((Account) getCurrentMatch()).myBankTransaction(amount, destinationAccount);
//      }
//      return false;
//   }

   
   //==========================================================================
   
//   public boolean receiveFound(double amount, Account sourceAccount)
//   {
//      if (this.getPattern().getHasMatch())
//      {
//         return ((Account) getCurrentMatch()).receiveFound(amount, sourceAccount);
//      }
//      return false;
//   }

   
   //==========================================================================
   
//   public boolean sendTransactionInfo(Transaction transaction, double amount, Date p0, Date p1, String note)
//   {
//      if (this.getPattern().getHasMatch())
//      {
//         return ((Account) getCurrentMatch()).sendTransactionInfo(transaction, amount, p0, p1, note);
//      }
//      return false;
//   }

   public Date getCreationdate()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Account) getCurrentMatch()).getCreationdate();
      }
      return null;
   }
   
   public AccountPO withCreationdate(Date value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((Account) getCurrentMatch()).setCreationdate(value);
      }
      return this;
   }
   
   
   //==========================================================================
   
   public boolean receiveFunds(Account giver, double amount, String note)
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Account) getCurrentMatch()).receiveFunds(amount, note);
      }
      return false;
   }

   
   //==========================================================================
   
   public Transaction recordTransaction(Account p0, boolean p1, double p2, String p3)
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Account) getCurrentMatch()).recordTransaction(p0, p1, p2, p3);
      }
      return null;
   }

   public AccountPO createCreationdateCondition(String value)
   {
      new AttributeConstraint()
      .withAttrName(Account.PROPERTY_CREATIONDATE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public AccountPO createCreationdateCondition(String lower, String upper)
   {
      new AttributeConstraint()
      .withAttrName(Account.PROPERTY_CREATIONDATE)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public AccountPO createCreationdateAssignment(String value)
   {
      new AttributeConstraint()
      .withAttrName(Account.PROPERTY_CREATIONDATE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(Pattern.CREATE)
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   
   //==========================================================================
   


   public AccountPO createTypeCondition(AccountTypeEnum value)
   {
      new AttributeConstraint()
      .withAttrName(Account.PROPERTY_TYPE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public AccountPO createTypeAssignment(AccountTypeEnum value)
   {
      new AttributeConstraint()
      .withAttrName(Account.PROPERTY_TYPE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(Pattern.CREATE)
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public AccountTypeEnum getType()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Account) getCurrentMatch()).getType();
      }
      return null;
   }
   
   public AccountPO withType(AccountTypeEnum value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((Account) getCurrentMatch()).setType(value);
      }
      return this;
   }
   
   
   //==========================================================================
   
   public boolean transferToAccount(double amount, Account destinationAccount, String note)
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Account) getCurrentMatch()).transferToAccount(amount, destinationAccount, note);
      }
      return false;
   }

   


}
